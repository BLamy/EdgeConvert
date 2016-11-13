
package group1;

import group1.Util.ErrorMessages;
import group1.model.Field;
import group1.model.Table;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class EdgeConvertFileParser {
    private File parseFile;
    private BufferedReader br;
    private ArrayList<Table> alTables;
    private ArrayList<Field> alFields;
    private ArrayList<EdgeConnector> alConnectors;
    public static final String EDGE_ID = "EDGE Diagram File"; //first line of .edg files should be this
    public static final String SAVE_ID = "EdgeConvert Save File"; //first line of save files should be this
    public static final String DELIM = "|";

    public EdgeConvertFileParser(File constructorFile) {
        alTables = new ArrayList<>();
        alFields = new ArrayList<>();
        alConnectors = new ArrayList<>();
        parseFile = constructorFile;
        this.openFile(parseFile);
    }

    private void parseFigure(HashMap<String, String> attributes) throws IOException {
        String style = attributes.get("Style");
        if (style == null) return; // this is to weed out other Figures, like Labels

        // Table name
        String tableName = attributes.getOrDefault("Text", "");
        if (tableName != null && tableName.indexOf("\\") > 0) { //Edge denotes a line break as "\line", disregard anything after a backslash
            tableName = tableName.substring(0, tableName.indexOf("\\"));
        }

        //
        int numFig = Integer.parseInt(attributes.getOrDefault("Figure", "0"));
        boolean isEntity = style.startsWith("Entity");
        boolean isAttribute = style.startsWith("Attribute");
        boolean isRelationship = style.startsWith("Relation");
        boolean isDuplicate = isEntity && isTableDup(tableName);

        // Handle Errors
        String err = "";
        if (isRelationship) { //presence of Relations implies lack of normalization
            err += ErrorMessages.containsRelationships(tableName);
        }
        if (isEntity && tableName != null && tableName.equals("")) {
            err += ErrorMessages.entityOrAttributeNameBlank();
        }
        if (isDuplicate) {
            err += ErrorMessages.duplicateTableName(tableName);
        }
        if (!err.equals("")) {
            JOptionPane.showMessageDialog(null, err);
            EdgeConvertGUI.setReadSuccess(false);
            return;
        }

        //create a new Table object and add it to the alTables ArrayList
        if (isEntity) {
            alTables.add(new Table(numFig + DELIM + tableName));
        }

        //create a new Field object and add it to the alFields ArrayList
        if (isAttribute) {
            alFields.add(new Field(numFig + DELIM + tableName));
        }

    }

    private void parseConnector(HashMap<String, String> attributes) throws IOException {
        int numConnector = Integer.parseInt(attributes.get("Connector")); //get the Connector number
        int endPoint1 = Integer.parseInt(attributes.get("Figure1"));
        int endPoint2 = Integer.parseInt(attributes.get("Figure2"));
        alConnectors.add(new EdgeConnector(numConnector + DELIM + endPoint1 + DELIM + endPoint2 + DELIM + null + DELIM + null));
    }

    public void parseEdgeFile() throws IOException {
        String currentLine;
        while ((currentLine = br.readLine()) != null) {
            currentLine = currentLine.trim();
            boolean isFigure = currentLine.startsWith("Figure ");
            boolean isConnector = currentLine.startsWith("Connector ");
            if (isFigure || isConnector) { //this is the start of a Figure entry
                HashMap<String, String> attributes = new HashMap<>();
                do {
                    String[] components = currentLine.split(" ");
                    if (components.length == 2) {
                        attributes.put(components[0], components[1].replaceAll("\"", ""));
                    }
                    currentLine = br.readLine().trim();
                } while (!currentLine.equals("}"));
                if (isFigure) {
                    parseFigure(attributes);
                } else {
                    parseConnector(attributes);
                }
            }
        }
    }

    private void resolveConnectors() { //Identify nature of Connector endpoints
        Field[] fields = getEdgeFields();
        Table[] tables = getEdgeTables();
        EdgeConnector[] connectors = getEdgeConnectors();
        int fieldIndex, table1Index = 0, table2Index = 0;
        for (EdgeConnector connector : connectors) {
            int endPoint1 = connector.getEndPoint1();
            int endPoint2 = connector.getEndPoint2();
            fieldIndex = -1;
            for (int fIndex = 0; fIndex < fields.length; fIndex++) { //search fields array for endpoints
                if (endPoint1 == fields[fIndex].getNumFigure()) { //found endPoint1 in fields array
                    connector.setIsEP1Field(true); //set appropriate flag
                    fieldIndex = fIndex; //identify which element of the fields array that endPoint1 was found in
                }
                if (endPoint2 == fields[fIndex].getNumFigure()) { //found endPoint2 in fields array
                    connector.setIsEP2Field(true); //set appropriate flag
                    fieldIndex = fIndex; //identify which element of the fields array that endPoint2 was found in
                }
            }
            for (int tIndex = 0; tIndex < tables.length; tIndex++) { //search tables array for endpoints
                if (endPoint1 == tables[tIndex].getNumFigure()) { //found endPoint1 in tables array
                    connector.setIsEP1Table(true); //set appropriate flag
                    table1Index = tIndex; //identify which element of the tables array that endPoint1 was found in
                }
                if (endPoint2 == tables[tIndex].getNumFigure()) { //found endPoint1 in tables array
                    connector.setIsEP2Table(true); //set appropriate flag
                    table2Index = tIndex; //identify which element of the tables array that endPoint2 was found in
                }
            }

            if (connector.getIsEP1Field() && connector.getIsEP2Field()) { //both endpoints are fields, implies lack of normalization
                JOptionPane.showMessageDialog(null, ErrorMessages.containsCompositeAttributes(parseFile.toString()));
                EdgeConvertGUI.setReadSuccess(false); //this tells GUI not to populate JList components
                break; //stop processing list of Connectors
            }

            if (connector.getIsEP1Table() && connector.getIsEP2Table()) { //both endpoints are tables
                //the connector represents a many-many relationship, implies lack of normalization
                if (connector.getEndStyle1().indexOf("many") >= 0 && connector.getEndStyle2().indexOf("many") >= 0) {
                    JOptionPane.showMessageDialog(null, ErrorMessages.manyToMany(tables[table1Index].getName(), tables[table2Index].getName()));
                    EdgeConvertGUI.setReadSuccess(false); //this tells GUI not to populate JList components
                    break; //stop processing list of Connectors
                }

                //add Figure number to each table's list of related tables
                tables[table1Index].addRelatedTable(tables[table2Index].getNumFigure());
                tables[table2Index].addRelatedTable(tables[table1Index].getNumFigure());
            }

            if (fieldIndex >= 0 && fields[fieldIndex].getTableID() == 0) { //field has not been assigned to a table yet
                if (connector.getIsEP1Table()) { //endpoint1 is the table
                    tables[table1Index].addNativeField(fields[fieldIndex].getNumFigure()); //add to the appropriate table's field list
                    fields[fieldIndex].setTableID(tables[table1Index].getNumFigure()); //tell the field what table it belongs to
                } else { //endpoint2 is the table
                    tables[table2Index].addNativeField(fields[fieldIndex].getNumFigure()); //add to the appropriate table's field list
                    fields[fieldIndex].setTableID(tables[table2Index].getNumFigure()); //tell the field what table it belongs to
                }
            } else if (fieldIndex >= 0) { //field has already been assigned to a table
                JOptionPane.showMessageDialog(null, ErrorMessages.attributeConnectedToMultipleTables(fields[fieldIndex].getName()));
                EdgeConvertGUI.setReadSuccess(false); //this tells GUI not to populate JList components
                break; //stop processing list of Connectors
            }
        }
    }

    public void parseSaveFile() throws IOException { //this method is fucked
//      StringTokenizer stTables, stNatFields, stRelFields, stNatRelFields, stField;
//      Table tempTable;
//      Field tempField;
//      currentLine = br.readLine();
//      currentLine = br.readLine(); //this should be "Table: "
//      while (currentLine.startsWith("Table: ")) {
//         numFigure = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); //get the Table number
//         currentLine = br.readLine(); //this should be "{"
//         currentLine = br.readLine(); //this should be "TableName"
//         tableName = currentLine.substring(currentLine.indexOf(" ") + 1);
//         tempTable = new Table(numFigure + DELIM + tableName);
//
//         currentLine = br.readLine(); //this should be the NativeFields list
//         stNatFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
//         numFields = stNatFields.countTokens();
//         for (int i = 0; i < numFields; i++) {
//            tempTable.addNativeField(Integer.parseInt(stNatFields.nextToken()));
//         }
//
//         currentLine = br.readLine(); //this should be the RelatedTables list
//         stTables = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
//         numTables = stTables.countTokens();
//         for (int i = 0; i < numTables; i++) {
//            tempTable.addRelatedTable(Integer.parseInt(stTables.nextToken()));
//         }
//         tempTable.makeArrays();
//
//         currentLine = br.readLine(); //this should be the RelatedFields list
//         stRelFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
//         numFields = stRelFields.countTokens();
//
//         for (int i = 0; i < numFields; i++) {
//            tempTable.setRelatedField(i, Integer.parseInt(stRelFields.nextToken()));
//         }
//
//         alTables.add(tempTable);
//         currentLine = br.readLine(); //this should be "}"
//         currentLine = br.readLine(); //this should be "\n"
//         currentLine = br.readLine(); //this should be either the next "Table: ", #Fields#
//      }
//      while ((currentLine = br.readLine()) != null) {
//         stField = new StringTokenizer(currentLine, DELIM);
//         numFigure = Integer.parseInt(stField.nextToken());
//         fieldName = stField.nextToken();
//         tempField = new Field(numFigure + DELIM + fieldName);
//         tempField.setTableID(Integer.parseInt(stField.nextToken()));
//         tempField.setTableBound(Integer.parseInt(stField.nextToken()));
//         tempField.setFieldBound(Integer.parseInt(stField.nextToken()));
//         tempField.setDataType(Integer.parseInt(stField.nextToken()));
//         tempField.setVarcharValue(Integer.parseInt(stField.nextToken()));
//         tempField.setIsPrimaryKey(Boolean.valueOf(stField.nextToken()).booleanValue());
//         tempField.setDisallowNull(Boolean.valueOf(stField.nextToken()).booleanValue());
//         if (stField.hasMoreTokens()) { //Default Value may not be defined
//            tempField.setDefaultValue(stField.nextToken());
//         }
//         alFields.add(tempField);
//      }
    }

    @Deprecated
    private void makeArrays() {
    }

    private boolean isTableDup(String testTableName) {
        for (Table table : alTables) {
            if (table.getName().equals(testTableName)) {
                return true;
            }
        }
        return false;
    }

    public Table[] getEdgeTables() {
        return alTables.toArray(new Table[alTables.size()]);
    }

    public EdgeConnector[] getEdgeConnectors() {
        return alConnectors.toArray(new EdgeConnector[alConnectors.size()]);
    }

    public Field[] getEdgeFields() {
        return alFields.toArray(new Field[alFields.size()]);
    }

    public void openFile(File inputFile) {
        try {
            FileReader fr = new FileReader(inputFile);

            br = new BufferedReader(fr);
            //test for what kind of file we have
            String currentLine = br.readLine().trim();
            if (currentLine.startsWith(EDGE_ID)) { //the file chosen is an Edge Diagrammer file
                this.parseEdgeFile(); //parse the file
                br.close();
                this.makeArrays(); //convert ArrayList objects into arrays of the appropriate Class type
                this.resolveConnectors(); //Identify nature of Connector endpoints
            } else {
                if (currentLine.startsWith(SAVE_ID)) { //the file chosen is a Save file created by this application
                    this.parseSaveFile(); //parse the file
                    br.close();
                    this.makeArrays(); //convert ArrayList objects into arrays of the appropriate Class type
                } else { //the file chosen is something else
                    JOptionPane.showMessageDialog(null, ErrorMessages.unknownFile());
                }
            }
        } // try
        catch (FileNotFoundException fnfe) {
            System.out.println("Cannot find \"" + inputFile.getName() + "\".");
            System.exit(0);
        } // catch FileNotFoundException
        catch (IOException ioe) {
            System.out.println(ioe);
            System.exit(0);
        } // catch IOException
    } // openFile()
} // EdgeConvertFileHandler
