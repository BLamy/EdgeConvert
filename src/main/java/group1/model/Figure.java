package group1.model;

import group1.EdgeConvertGUI;
import group1.Util.Constants;
import group1.Util.Collections;
import group1.Util.ErrorMessages;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by brett on 11/13/16.
 */


public class Figure {
    String tableName;
    boolean isEntity;
    boolean isAttribute;
    boolean isRelationship;
    boolean isDuplicate;
    String err;
    Table table;
    Field field;

    public Figure(HashMap<String, String> attributes, ArrayList<Table> otherTables) {
        String style = attributes.get("Style");
        if (style == null) return; // this is to weed out other Figures, like Labels

        // Table name
        this.tableName = attributes.getOrDefault("Text", "");
        if (this.tableName.indexOf("\\") > 0) { //EdgeStrategy denotes a line break as "\line", disregard anything after a backslash
            this.tableName = this.tableName.substring(0, this.tableName.indexOf("\\"));
        }

        // Flags
        this.isEntity = style.startsWith("Entity");
        this.isAttribute = style.startsWith("Attribute");
        this.isRelationship = style.startsWith("Relation");
        this.isDuplicate = this.isEntity && Collections.isTableDup(this.tableName, otherTables);

        //------------------------------
        // Handle Errors
        //------------------------------
        this.err = "";
        if (isRelationship) { //presence of Relations implies lack of normalization
            this.err += ErrorMessages.containsRelationships(tableName);
        }
        if (isEntity && tableName.equals("")) {
            this.err += ErrorMessages.entityOrAttributeNameBlank();
        }
        if (isDuplicate) {
            this.err += ErrorMessages.duplicateTableName(tableName);
        }
        if (!this.err.equals("")) {
            JOptionPane.showMessageDialog(null, this.err);
            EdgeConvertGUI.setReadSuccess(false);
            return;
        }


        //------------------------------
        // Add tables / fields to their respective arrays
        //------------------------------
        int numFig = Integer.parseInt(attributes.getOrDefault("Figure", "0"));
        if (isEntity) {
            this.table = new Table(numFig + Constants.DELIM + tableName);
        }
        if (isAttribute) {
            this.field = new Field(numFig + Constants.DELIM + tableName);
        }
    }

    public boolean hasTable() {
        return this.table != null;
    }

    public Table getTable() {
        return this.table;
    }

    public boolean hasField() {
        return this.field != null;
    }

    public Field getField() {
        return this.field;
    }
}