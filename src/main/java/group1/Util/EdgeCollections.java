package group1.Util;

/**
 * Created by brett on 11/12/16.
 */

import group1.EdgeField;
import group1.EdgeTable;

import java.util.ArrayList;

public class EdgeCollections {
    public static EdgeTable findTableByNumFig(EdgeTable[] tables, int numFigure) {
        // TODO replace with find Higher order function?
        for (EdgeTable table : tables) {
            if (numFigure == table.getNumFigure()) {
                return table;
            }
        }
        return null;
    }

    public static String findTableNameByNumFig(EdgeTable[] tables, int numFigure) {
        EdgeTable table = findTableByNumFig(tables, numFigure);
        return (table == null) ? "" : table.getName();
    }

    public static EdgeField findFieldByNumFig(EdgeField[] fields, int numFigure) {
        // TODO replace with find Higher order function?
        for (EdgeField field : fields) {
            if (numFigure == field.getNumFigure()) {
                return field;
            }
        }
        return null;
    }

    public static String findFieldNameByNumFig(EdgeField[] fields, int numFigure) {
        EdgeField field = findFieldByNumFig(fields, numFigure);
        return (field == null) ? "" : field.getName();
    }

    public static int getNumPrimaryKey(EdgeTable table, EdgeField[] fields) {
        int numPrimaryKey = 0;
        for (int nativeField : table.getNativeFieldsArray()) {
            EdgeField currentField = findFieldByNumFig(fields, nativeField);
            if (currentField != null && currentField.getIsPrimaryKey()) {
                numPrimaryKey++;
            }
        }
        return numPrimaryKey;
    }

    public static int getNumForeignKey(EdgeTable table, EdgeField[] fields) {
        int numForeignKey = 0;
        for (int nativeField : table.getNativeFieldsArray()) {
            EdgeField currentField = findFieldByNumFig(fields, nativeField);
            if (currentField != null && currentField.getFieldBound() != 0) {
                numForeignKey++;
            }
        }
        return numForeignKey;
    }

    public static boolean[] getPrimaryKeys(EdgeTable table, EdgeField[] fields) {
        int[] nativeFields = table.getNativeFieldsArray();
        boolean[] primaryKey = new boolean[nativeFields.length];
        for (int i = 0; i < nativeFields.length; i++) {
            EdgeField currentField = findFieldByNumFig(fields, nativeFields[i]);
            primaryKey[i] = currentField != null && currentField.getIsPrimaryKey();
        }
        return primaryKey;
    }

    public static EdgeTable[] sortTables(EdgeTable[] tables) {
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

        ArrayList<EdgeTable> orderedTables = new ArrayList<EdgeTable>();
        for (int boundCount = 0; boundCount <= maxBound; boundCount++) { //process tables in order from least dependent (least number of bound tables) to most dependent
            for (int tableCount = 0; tableCount < numBoundTables.length; tableCount++) { //step through list of tables
                if (numBoundTables[tableCount] == boundCount) { //
                    orderedTables.add(tables[tableCount]);
                }
            }
        }
        return orderedTables.toArray(new EdgeTable[orderedTables.size()]);
    }
}
