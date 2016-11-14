package group1.Util;

/**
 * Created by brett on 11/12/16.
 */

import group1.model.Field;
import group1.model.Table;

import java.util.ArrayList;

/**
 * A bunch of methods for doing things with collections.
 * Pretty much every function in here is probably better written using some kind of Higher Order Function but this is java.
 */
public class Collections {
    public static int calcFieldIndex(int endPoint1, int endPoint2, Field[] fields) {
        int fieldIndex = -1;
        for (int fIndex = 0; fIndex < fields.length; fIndex++) { //search fields array for endpoints
            if (endPoint1 == fields[fIndex].getNumFigure() || endPoint2 == fields[fIndex].getNumFigure()) { //found endPoint1 in fields array
                fieldIndex = fIndex; //identify which element of the fields array that endPoint1 was found in
            }
        }
        return fieldIndex;
    }

    public static int calcTableIndex(int table1Index, int endPoint, Table[] tables) {
        for (int tIndex = 0; tIndex < tables.length; tIndex++) { //search tables array for endpoints
            if (endPoint == tables[tIndex].getNumFigure()) { //found endPoint1 in tables array
                table1Index = tIndex; //identify which element of the tables array that endPoint1 was found in
            }
        }
        return table1Index;
    }

    public static boolean isTableDup(String testTableName, ArrayList<Table> otherTables) {
        for (Table table : otherTables) {
            if (table.getName().equals(testTableName)) {
                return true;
            }
        }
        return false;
    }

    public static Table findTableByNumFig(Table[] tables, int numFigure) {
        for (Table table : tables) {
            if (numFigure == table.getNumFigure()) {
                return table;
            }
        }
        return null;
    }

    public static String findTableNameByNumFig(Table[] tables, int numFigure) {
        Table table = findTableByNumFig(tables, numFigure);
        return (table == null) ? "" : table.getName();
    }

    public static Field findFieldByNumFig(Field[] fields, int numFigure) {
        for (Field field : fields) {
            if (numFigure == field.getNumFigure()) {
                return field;
            }
        }
        return null;
    }

    public static String findFieldNameByNumFig(Field[] fields, int numFigure) {
        Field field = findFieldByNumFig(fields, numFigure);
        return (field == null) ? "" : field.getName();
    }

    public static int getNumPrimaryKey(Table table, Field[] fields) {
        int numPrimaryKey = 0;
        for (int nativeField : table.getNativeFieldsArray()) {
            Field currentField = findFieldByNumFig(fields, nativeField);
            if (currentField != null && currentField.getIsPrimaryKey()) {
                numPrimaryKey++;
            }
        }
        return numPrimaryKey;
    }

    public static int getNumForeignKey(Table table, Field[] fields) {
        int numForeignKey = 0;
        for (int nativeField : table.getNativeFieldsArray()) {
            Field currentField = findFieldByNumFig(fields, nativeField);
            if (currentField != null && currentField.getFieldBound() != 0) {
                numForeignKey++;
            }
        }
        return numForeignKey;
    }

    public static boolean[] getPrimaryKeys(Table table, Field[] fields) {
        int[] nativeFields = table.getNativeFieldsArray();
        boolean[] primaryKey = new boolean[nativeFields.length];
        for (int i = 0; i < nativeFields.length; i++) {
            Field currentField = findFieldByNumFig(fields, nativeFields[i]);
            primaryKey[i] = currentField != null && currentField.getIsPrimaryKey();
        }
        return primaryKey;
    }

    public static Table[] sortTables(Table[] tables) {
        int[] numBoundTables = new int[tables.length];
        int maxBound = 0;
        for (int i = 0; i < tables.length; i++) { //step through list of tables
            int numBound = 0; //initialize counter for number of bound tables
            int[] relatedFields = tables[i].getRelatedFieldsArray();
            for (int relatedField : relatedFields) { //step through related fields list
                if (relatedField != 0) {
                    numBound++; //count the number of non-zero related fields
                }
            }
            numBoundTables[i] = numBound;
            if (numBound > maxBound) {
                maxBound = numBound;
            }
        }

        ArrayList<Table> orderedTables = new ArrayList<Table>();
        for (int boundCount = 0; boundCount <= maxBound; boundCount++) { //process tables in order from least dependent (least number of bound tables) to most dependent
            for (int tableCount = 0; tableCount < numBoundTables.length; tableCount++) { //step through list of tables
                if (numBoundTables[tableCount] == boundCount) { //
                    orderedTables.add(tables[tableCount]);
                }
            }
        }
        return orderedTables.toArray(new Table[orderedTables.size()]);
    }
}
