package group1.model;

import group1.Util.Constants;

import java.util.*;

public class Table {
    private int numFigure;
    private String name;
    private ArrayList<Integer> relatedTables, nativeFields;
    private HashMap<Integer, Integer> relatedFields;

    public Table(String inputString) {
        StringTokenizer st = new StringTokenizer(inputString, Constants.DELIM);
        numFigure = Integer.parseInt(st.nextToken());
        name = st.nextToken();
        relatedTables = new ArrayList<Integer>();
        nativeFields = new ArrayList<Integer>();
        relatedFields = new HashMap<Integer, Integer>();
    }

    public int getNumFigure() {
        return numFigure;
    }

    public String getName() {
        return name;
    }

    public void addRelatedTable(int relatedTable) {
        relatedTables.add(relatedTable);
    }

    public int[] getRelatedTablesArray() {
        int[] relatedTables = new int[this.relatedTables.size()];
        for (int i = 0; i < this.relatedTables.size(); i++) {
            relatedTables[i] = this.relatedTables.get(i);
        }
        return relatedTables;
    }

    public int[] getRelatedFieldsArray() {
        int[] relatedFields = new int[nativeFields.size()];
        for (int i = 0; i < relatedFields.length; i++) {
            relatedFields[i] = this.relatedFields.getOrDefault(i, 0);
        }
        return relatedFields;
    }

    public void setRelatedField(int index, int relatedValue) {
        relatedFields.put(index, relatedValue);
    }

    public int[] getNativeFieldsArray() {
        int[] nativeFields = new int[this.nativeFields.size()];
        for (int i = 0; i < this.nativeFields.size(); i++) {
            nativeFields[i] = this.nativeFields.get(i);
        }
        return nativeFields;
    }

    public void addNativeField(int value) {
        nativeFields.add(value);
    }

    public void moveFieldUp(int index) {
        if (index == 0) return;
        int tempNative = nativeFields.get(index - 1);
        nativeFields.set(index - 1, nativeFields.get(index));
        nativeFields.set(index, tempNative);
        int tempRelated = relatedFields.get(index - 1);
        relatedFields.put(index - 1, relatedFields.get(index));
        relatedFields.put(index, tempRelated);
    }

    public void moveFieldDown(int index) {
        if (index == (nativeFields.size() - 1)) return;
        int tempNative = nativeFields.get(index + 1);
        nativeFields.set(index + 1, nativeFields.get(index));
        nativeFields.set(index, tempNative);
        int tempRelated = relatedFields.get(index + 1);
        relatedFields.put(index + 1, relatedFields.get(index));
        relatedFields.put(index, tempRelated);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Table: " + numFigure + "\r\n");
        sb.append("{\r\n");
        sb.append("TableName: " + name + "\r\n");
        sb.append("NativeFields: ");
        int[] nativeFields = getNativeFieldsArray();
        for (int i = 0; i < nativeFields.length; i++) {
            sb.append(nativeFields[i]);
            if (i < (nativeFields.length - 1)) {
                sb.append(Constants.DELIM);
            }
        }
        sb.append("\r\nRelatedTables: ");
        int[] relatedTables = getRelatedTablesArray();
        for (int i = 0; i < relatedTables.length; i++) {
            sb.append(relatedTables[i]);
            if (i < (relatedTables.length - 1)) {
                sb.append(Constants.DELIM);
            }
        }
        sb.append("\r\nRelatedFields: ");
        int[] relatedFields = getRelatedFieldsArray();
        for (int i = 0; i < relatedFields.length; i++) {
            sb.append(relatedFields[i]);
            if (i < (relatedFields.length - 1)) {
                sb.append(Constants.DELIM);
            }
        }
        sb.append("\r\n}");

        return sb.toString();
    }
}
