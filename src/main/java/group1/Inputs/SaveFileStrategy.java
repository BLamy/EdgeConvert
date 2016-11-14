package group1.Inputs;

import group1.model.Connector;
import group1.model.Database;
import group1.model.Field;
import group1.model.Table;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by brett on 11/12/16.
 */
public class SaveFileStrategy {


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

    public static Database open(BufferedReader br, String currentLine) throws IOException {
        return new Database(new Table[]{}, new Field[]{}, new Connector[]{});
    }
}