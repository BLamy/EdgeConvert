package group1.Outputs;

import group1.model.Field;
import group1.model.Table;
import group1.Util.EdgeCollections;

/**
 * Created by brett on 11/12/16.
 */
public class MySQL {
    static String[] strDataType = {"VARCHAR", "BOOL", "INT", "DOUBLE"};

    /**
     * MySQL uses '1' and '0' for boolean types
     */
    private static int convertStrBooleanToInt(String input) { //
        return input.equals("true") ? 1 : 0;
    }

    private static String createDatabase(String dbName) {
        return "CREATE DATABASE " + dbName + ";\r\n";
    }

    private static String useDatabase(String dbName) {
        return "USE " + dbName + ";\r\n";
    }

    private static String createTable(Table table, Field[] fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + table.getName() + " (\r\n");
        for (int nativeField : table.getNativeFieldsArray()) { //print out the fields
            Field currentField = EdgeCollections.findFieldByNumFig(fields, nativeField);
            sb.append("\t" + currentField.getName() + " " + strDataType[currentField.getDataType()]);
            if (currentField.getDataType() == 0) { //varchar
                sb.append("(" + currentField.getVarcharValue() + ")"); //append varchar length in () if data type is varchar
            }
            if (currentField.getDisallowNull()) {
                sb.append(" NOT NULL");
            }
            if (!currentField.getDefaultValue().equals("")) {
                if (currentField.getDataType() == 1) { //boolean data type
                    sb.append(" DEFAULT " + convertStrBooleanToInt(currentField.getDefaultValue()));
                } else { //any other data type
                    sb.append(" DEFAULT " + currentField.getDefaultValue());
                }
            }
            sb.append(",\r\n"); //end of field
        }
        return sb.toString();
    }

    private static String addPrimaryKeys(Table table, Field[] fields) {
        int numPrimaryKey = EdgeCollections.getNumPrimaryKey(table, fields);
        int numForeignKey = EdgeCollections.getNumForeignKey(table, fields);
        boolean[] primaryKey = EdgeCollections.getPrimaryKeys(table, fields);

        if (numPrimaryKey <= 0) return "";

        StringBuilder sb = new StringBuilder();
        int[] nativeFields = table.getNativeFieldsArray();

        sb.append("CONSTRAINT " + table.getName() + "_PK PRIMARY KEY (");
        for (int i = 0; i < primaryKey.length; i++) {
            if (!primaryKey[i]) continue;
            sb.append(EdgeCollections.findFieldNameByNumFig(fields, nativeFields[i]));
            if (i < numPrimaryKey - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        if (numForeignKey > 0) {
            sb.append(",");
        }
        sb.append("\r\n");
        return sb.toString();
    }

    private static String addForeignKeys(Table table, Field[] fields, Table[] tables) {
        int numForeignKey = EdgeCollections.getNumForeignKey(table, fields);
        if (numForeignKey <= 0) return "";

        int currentFK = 1;
        StringBuilder sb = new StringBuilder();
        int[] relatedFields = table.getRelatedFieldsArray();
        int[] nativeFields = table.getNativeFieldsArray();
        for (int i = 0; i < relatedFields.length; i++) {
            Field nativeField = EdgeCollections.findFieldByNumFig(fields, nativeFields[i]);
            if (relatedFields[i] == 0 || nativeField == null) continue;
            String fkName = nativeField.getName();
            String boundTableName = EdgeCollections.findTableNameByNumFig(tables, nativeField.getTableBound());
            String boundFieldName = EdgeCollections.findFieldNameByNumFig(fields, relatedFields[i]);
            sb.append("CONSTRAINT " + table.getName() +
                    "_FK" + currentFK + " FOREIGN KEY(" + fkName + ")" +
                    " REFERENCES " + boundTableName + "(" + boundFieldName + ")"
            );
            if (i < numForeignKey - 1) {
                sb.append(",\r\n");
            }
            currentFK++;
        }
        sb.append("\r\n");
        return sb.toString();
    }

    public static String convert(String databaseName, Table[] tables, Field[] fields) {
        StringBuffer sb = new StringBuffer();

        sb.append(createDatabase(databaseName));
        sb.append(useDatabase(databaseName));
        for (Table table : EdgeCollections.sortTables(tables)) {
            sb.append(createTable(table, fields));
            sb.append(addPrimaryKeys(table, fields));
            sb.append(addForeignKeys(table, fields, tables));
            sb.append(");\r\n\r\n"); //end of table
        }
        return sb.toString();
    }
}

