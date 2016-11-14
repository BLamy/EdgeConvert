package group1.model;

import group1.Util.Constants;

import java.util.*;

public class Table {
   private int numFigure;
   private String name;
   private ArrayList<Integer> alRelatedTables, alNativeFields;
    private HashMap<Integer, Integer> alRelatedFields;

   public Table(String inputString) {
      StringTokenizer st = new StringTokenizer(inputString, Constants.DELIM);
      numFigure = Integer.parseInt(st.nextToken());
      name = st.nextToken();
      alRelatedTables = new ArrayList<Integer>();
      alNativeFields = new ArrayList<Integer>();
       alRelatedFields = new HashMap<Integer, Integer>();
   }

   public int getNumFigure() {
       return numFigure;
   }

   public String getName() {
      return name;
   }

   public void addRelatedTable(int relatedTable) {
      alRelatedTables.add(relatedTable);
   }

   public int[] getRelatedTablesArray() {
       Integer[] temp = alRelatedTables.toArray(new Integer[alRelatedTables.size()]);
       int[] relatedTables = new int[temp.length];
       for (int i = 0; i < temp.length; i++) {
           relatedTables[i] = temp[i];
       }
      return relatedTables;
   }

   public int[] getRelatedFieldsArray() {
       int[] relatedFields = new int[alNativeFields.size()];
       for (int i = 0; i < relatedFields.length; i++) {
           relatedFields[i] = alRelatedFields.getOrDefault(i, 0);
       }
      return relatedFields;
   }

   public void setRelatedField(int index, int relatedValue) {
       alRelatedFields.put(index, relatedValue);
   }

   public int[] getNativeFieldsArray() {
       Integer[] temp = alNativeFields.toArray(new Integer[alNativeFields.size()]);
       int[] nativeFields = new int[temp.length];
       for (int i = 0; i < temp.length; i++) {
           nativeFields[i] = temp[i];
       }
      return nativeFields;
   }

   public void addNativeField(int value) {
      alNativeFields.add(value);
   }

   public void moveFieldUp(int index) {
       if (index == 0) return;
       int tempNative = alNativeFields.get(index - 1);
       alNativeFields.set(index - 1, alNativeFields.get(index));
       alNativeFields.set(index, tempNative);
       int tempRelated = alRelatedFields.get(index - 1);
       alRelatedFields.put(index - 1, alRelatedFields.get(index));
       alRelatedFields.put(index, tempRelated);
    }

   public void moveFieldDown(int index) {
       if (index == (alNativeFields.size() - 1)) return;
       int tempNative = alNativeFields.get(index + 1);
       alNativeFields.set(index + 1, alNativeFields.get(index));
       alNativeFields.set(index, tempNative);
       int tempRelated = alRelatedFields.get(index + 1);
       alRelatedFields.put(index + 1, alRelatedFields.get(index));
       alRelatedFields.put(index, tempRelated);
   }


    @Deprecated
   public void makeArrays() {}

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("Table: " + numFigure + "\r\n");
      sb.append("{\r\n");
      sb.append("TableName: " + name + "\r\n");
      sb.append("NativeFields: ");
       int[] nativeFields = getNativeFieldsArray();
      for (int i = 0; i < nativeFields.length; i++) {
         sb.append(nativeFields[i]);
         if (i < (nativeFields.length - 1)){
            sb.append(Constants.DELIM);
         }
      }
      sb.append("\r\nRelatedTables: ");
      int[] relatedTables = getRelatedTablesArray();
      for (int i = 0; i < relatedTables.length; i++) {
         sb.append(relatedTables[i]);
         if (i < (relatedTables.length - 1)){
            sb.append(Constants.DELIM);
         }
      }
      sb.append("\r\nRelatedFields: ");
       int[] relatedFields = getRelatedFieldsArray();
       for (int i = 0; i < relatedFields.length; i++) {
         sb.append(relatedFields[i]);
         if (i < (relatedFields.length - 1)){
            sb.append(Constants.DELIM);
         }
      }
      sb.append("\r\n}\r\n");

      return sb.toString();
   }
}
