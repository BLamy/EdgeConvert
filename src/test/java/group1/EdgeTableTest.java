package group1;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by brett on 9/30/16.
 */
public class EdgeTableTest extends TestCase {

    @Test
    public void testConstructor() throws Exception {
        EdgeTable edgeTable = new EdgeTable("1|brett");
        assertEquals("Should parse number figure", 1, edgeTable.getNumFigure());
        assertEquals("Should Parse name", "brett", edgeTable.getName());
    }

    @Test
    public void testRelatedTable() throws Exception {
        EdgeTable edgeTable = new EdgeTable("1|brett");
        edgeTable.addRelatedTable(0);
        edgeTable.addRelatedTable(1);
        edgeTable.addRelatedTable(2);
        edgeTable.makeArrays();
        assertEquals("Should properly set testRelatedTable[1]", 0, edgeTable.getRelatedTablesArray()[0]);
        assertEquals("Should properly set testRelatedTable[1]", 1, edgeTable.getRelatedTablesArray()[1]);
        assertEquals("Should properly set testRelatedTable[1]", 2, edgeTable.getRelatedTablesArray()[2]);
    }

    @Test
    public void testNativeFieldsArray() throws Exception {
        EdgeTable edgeTable = new EdgeTable("1|brett");
        edgeTable.addNativeField(0);
        edgeTable.addNativeField(1);
        edgeTable.addNativeField(2);
        edgeTable.makeArrays();
        assertEquals("Should properly set setGetRelatedFields[1]", 0, edgeTable.getNativeFieldsArray()[0]);
        assertEquals("Should properly set setGetRelatedFields[1]", 1, edgeTable.getNativeFieldsArray()[1]);
        assertEquals("Should properly set setGetRelatedFields[1]", 2, edgeTable.getNativeFieldsArray()[2]);
    }

    @Test
    public void testMoveFieldUp() throws Exception {
        EdgeTable edgeTable = new EdgeTable("1|brett");
        edgeTable.addNativeField(0);
        edgeTable.addNativeField(1);
        edgeTable.addNativeField(2);
        edgeTable.makeArrays();
        edgeTable.setRelatedField(1, 1);
        edgeTable.setRelatedField(2, 2);
        edgeTable.moveFieldUp(2);
        assertEquals("Should properly set setGetRelatedFields[1]", 0, edgeTable.getRelatedFieldsArray()[0]);
        assertEquals("Should properly set setGetRelatedFields[1]", 2, edgeTable.getRelatedFieldsArray()[1]);
        assertEquals("Should properly set setGetRelatedFields[1]", 1, edgeTable.getRelatedFieldsArray()[2]);
    }

    @Test
    public void testMoveFieldDown() throws Exception {
        EdgeTable edgeTable = new EdgeTable("1|brett");
        edgeTable.addNativeField(0);
        edgeTable.addNativeField(1);
        edgeTable.addNativeField(2);
        edgeTable.makeArrays();
        edgeTable.setRelatedField(1, 1);
        edgeTable.setRelatedField(2, 2);
        edgeTable.moveFieldDown(1);
        assertEquals("Should properly set setGetRelatedFields[1]", 0, edgeTable.getRelatedFieldsArray()[0]);
        assertEquals("Should properly set setGetRelatedFields[1]", 2, edgeTable.getRelatedFieldsArray()[1]);
        assertEquals("Should properly set setGetRelatedFields[1]", 1, edgeTable.getRelatedFieldsArray()[2]);
    }

    @Test
    public void testToString() throws Exception {
        EdgeTable edgeTable = new EdgeTable("1|brett");
        edgeTable.addRelatedTable(1);
        edgeTable.addRelatedTable(2);
        edgeTable.addNativeField(0);
        edgeTable.addNativeField(1);
        edgeTable.addNativeField(2);
        edgeTable.makeArrays();
        String expected = (
            "Table:1" +
                "{" +
                    "TableName: brett" +
                    "NativeFields: 0|1|2" +
                    "RelatedTables: 1|2" +
                    "RelatedFields: 0|0|0" +
                "}"
        ).replaceAll("\\s","");

        String actual = edgeTable.toString().replaceAll("\\s","");
        assertEquals("", actual, expected);
    }
}
