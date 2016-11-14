package group1.Inputs;

import group1.Util.Constants;
import group1.model.Connector;
import group1.model.Database;
import group1.model.Field;
import group1.model.Table;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.jar.Attributes;

/**
 * Created by brett on 11/12/16.
 */
public class SaveFileStrategy {
    /**
     * Will Create a table object from a hashmap of attributes
     */
    private static Table getTable(HashMap<String, String> attributes) throws IOException {
        int numFigure = Integer.parseInt(attributes.get("Table:"));
        String tableName = attributes.get("TableName:");
        Table table = new Table(numFigure + Constants.DELIM + tableName);

        String[] nativeFields = attributes.get("NativeFields:").split("\\" + Constants.DELIM);
        for (String nativeField : nativeFields) {
            table.addNativeField(Integer.parseInt(nativeField));
        }

        String[] relatedTables = attributes.get("RelatedTables:").split("\\" + Constants.DELIM);
        for (String relatedTable : relatedTables) {
            table.addRelatedTable(Integer.parseInt(relatedTable));
        }

        String[] relatedFields = attributes.get("RelatedFields:").split("\\" + Constants.DELIM);
        for (int i = 0; i < relatedFields.length; i++) {
            table.setRelatedField(i, Integer.parseInt(relatedFields[i]));
        }
        return table;
    }

    /**
     * Given a line such as `1|ID|0|0|0|2|1|true|true|` will return a field
     */
    private static Field getField(String strField) {
        String[] components = strField.split("\\" + Constants.DELIM);
        String numFigure = components[0];
        String fieldName = components[1];
        Field field = new Field(numFigure + Constants.DELIM + fieldName);
        field.setTableID(Integer.parseInt(components[2]));
        field.setTableBound(Integer.parseInt(components[3]));
        field.setTableBound(Integer.parseInt(components[4]));
        field.setDataType(Integer.parseInt(components[5]));
        field.setVarcharValue(Integer.parseInt(components[6]));
        field.setIsPrimaryKey(Boolean.valueOf(components[7]));
        field.setDisallowNull(Boolean.valueOf(components[8]));
        if (components.length > 9) {
            field.setDefaultValue(components[9]);
        }
        return field;
    }

    /**
     * All inputs must expose this method
     */
    public static Database open(BufferedReader br, String currentLine) throws IOException {
        br.readLine(); // DatabaseName
        currentLine = br.readLine(); //this should be "Table: "

        ArrayList<Table> tables = new ArrayList<>();
        do {
            tables.add(getTable(FileParser.parseAttributes(br, currentLine)));
        } while ((currentLine = br.readLine()).startsWith("Table: "));

        ArrayList<Field> fields = new ArrayList<>();
        do {
            fields.add(getField(currentLine));
        } while ((currentLine = br.readLine()) != null);

        return new Database(tables.toArray(new Table[tables.size()]), fields.toArray(new Field[fields.size()]), new Connector[]{});
    }
}