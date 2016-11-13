package group1.model;

import group1.model.Table;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by brett on 9/30/16.
 */
public class TableTest extends TestCase {

    @Test
    public void testConstructor() throws Exception {
        Table table = new Table("1|brett");
        assertEquals("Should parse number figure", 1, table.getNumFigure());
        assertEquals("Should Parse name", "brett", table.getName());
    }

    @Test
    public void testRelatedTable() throws Exception {
        Table table = new Table("1|brett");
        table.addRelatedTable(0);
        table.addRelatedTable(1);
        table.addRelatedTable(2);
        assertEquals("Should properly set testRelatedTable[0]", 0, table.getRelatedTablesArray()[0]);
        assertEquals("Should properly set testRelatedTable[1]", 1, table.getRelatedTablesArray()[1]);
        assertEquals("Should properly set testRelatedTable[2]", 2, table.getRelatedTablesArray()[2]);
    }

    @Test
    public void testNativeFieldsArray() throws Exception {
        Table table = new Table("1|brett");
        table.addNativeField(0);
        table.addNativeField(1);
        table.addNativeField(2);
        assertEquals("Should properly set setGetRelatedFields[0]", 0, table.getNativeFieldsArray()[0]);
        assertEquals("Should properly set setGetRelatedFields[1]", 1, table.getNativeFieldsArray()[1]);
        assertEquals("Should properly set setGetRelatedFields[2]", 2, table.getNativeFieldsArray()[2]);
    }

    @Test
    public void testMoveFieldUp() throws Exception {
        Table table = new Table("1|brett");
        table.addNativeField(0);
        table.addNativeField(1);
        table.addNativeField(2);
        table.setRelatedField(1, 1);
        table.setRelatedField(2, 2);
        table.moveFieldUp(2);
        assertEquals("Should properly set setGetRelatedFields[0]", 0, table.getRelatedFieldsArray()[0]);
        assertEquals("Should properly set setGetRelatedFields[1]", 2, table.getRelatedFieldsArray()[1]);
        assertEquals("Should properly set setGetRelatedFields[2]", 1, table.getRelatedFieldsArray()[2]);
    }

    @Test
    public void testMoveFieldDown() throws Exception {
        Table table = new Table("1|brett");
        table.addNativeField(0);
        table.addNativeField(1);
        table.addNativeField(2);
        table.setRelatedField(1, 1);
        table.setRelatedField(2, 2);
        table.moveFieldDown(1);
        assertEquals("Should properly set setGetRelatedFields[0]", 0, table.getRelatedFieldsArray()[0]);
        assertEquals("Should properly set setGetRelatedFields[1]", 2, table.getRelatedFieldsArray()[1]);
        assertEquals("Should properly set setGetRelatedFields[2]", 1, table.getRelatedFieldsArray()[2]);
    }

    @Test
    public void testToString() throws Exception {
        Table table = new Table("1|brett");
        table.addRelatedTable(1);
        table.addRelatedTable(2);
        table.addNativeField(0);
        table.addNativeField(1);
        table.addNativeField(2);
        String expected = (
            "Table:1" +
                "{" +
                    "TableName: brett" +
                    "NativeFields: 0|1|2" +
                    "RelatedTables: 1|2" +
                    "RelatedFields: 0|0|0" +
                "}"
        ).replaceAll("\\s","");

        String actual = table.toString().replaceAll("\\s","");
        assertEquals("", actual, expected);
    }
}
