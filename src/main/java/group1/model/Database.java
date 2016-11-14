package group1.model;

import group1.EdgeConvertGUI;
import group1.Util.Collections;
import group1.Util.ErrorMessages;

import javax.swing.*;

/**
 * Created by brett on 11/13/16.
 */
public class Database {
    Table[] tables;
    Field[] fields;
    Connector[] connectors;

    public Database(Table[] tables, Field[] fields, Connector[] connectors) {
        int table1Index = 0, table2Index = 0;
        for (Connector connector : connectors) {
            //------------------------------
            // Search fields array for endpoints
            //------------------------------
            int endPoint1 = connector.getEndPoint1();
            int endPoint2 = connector.getEndPoint2();
            int fieldIndex = Collections.calcFieldIndex(endPoint1, endPoint2, fields);
            for (Field field: fields) {
                if (endPoint1 == field.getNumFigure()) connector.setIsEP1Field(true);
                if (endPoint2 == field.getNumFigure()) connector.setIsEP2Field(true);
            }

            //------------------------------
            // Search tables array for endpoints
            //------------------------------
            table1Index = Collections.calcTableIndex(table1Index, endPoint1, tables);
            table2Index = Collections.calcTableIndex(table2Index, endPoint2, tables);
            for (Table table: tables) {
                if (endPoint1 == table.getNumFigure()) connector.setIsEP1Table(true);
                if (endPoint2 == table.getNumFigure()) connector.setIsEP2Table(true);
            }

            //------------------------------
            // Both endpoints are fields, implies lack of normalization
            //------------------------------
            if (connector.getIsEP1Field() && connector.getIsEP2Field()) {
                JOptionPane.showMessageDialog(null, ErrorMessages.containsCompositeAttributes(""));
                EdgeConvertGUI.setReadSuccess(false);
                break;
            }

            //------------------------------
            // The connector represents a many-many relationship, implies lack of normalization
            //------------------------------
            if (connector.getIsEP1Table() && connector.getIsEP2Table()
                    && connector.getEndStyle1().indexOf("many") >= 0 && connector.getEndStyle2().indexOf("many") >= 0) {
                JOptionPane.showMessageDialog(null, ErrorMessages.manyToMany(tables[table1Index].getName(), tables[table2Index].getName()));
                EdgeConvertGUI.setReadSuccess(false); //this tells GUI not to populate JList components
                break; //stop processing list of Connectors
            }

            //------------------------------
            // Add Figure number to each table's list of related tables
            //------------------------------
            Table table1 = tables[table1Index];
            Table table2 = tables[table2Index];
            if (connector.getIsEP1Table() && connector.getIsEP2Table()) {
                table1.addRelatedTable(table2.getNumFigure());
                table2.addRelatedTable(table1.getNumFigure());
            }

            //------------------------------
            // search tables array for endpoints
            //------------------------------
            if (fieldIndex < 0) continue;
            Field field = fields[fieldIndex];

            if (field.getTableID() != 0) {
                JOptionPane.showMessageDialog(null, ErrorMessages.attributeConnectedToMultipleTables(fields[fieldIndex].getName()));
                EdgeConvertGUI.setReadSuccess(false); //this tells GUI not to populate JList components
                break; //stop processing list of Connectors
            }

            //------------------------------
            // search tables array for endpoints
            //------------------------------
            if (connector.getIsEP1Table()) {
                table1.addNativeField(field.getNumFigure());
                field.setTableID(table1.getNumFigure());
            } else {
                table2.addNativeField(field.getNumFigure());
                field.setTableID(table2.getNumFigure());
            }
        }
        this.tables = tables;
        this.fields = fields;
        this.connectors = connectors;
    }


    public Table[] getTables() {
        return tables;
    }

    public Field[] getFields() {
        return fields;
    }

    public Connector[] getConnectors() {
        return connectors;
    }
}
