package group1.model;

import group1.Util.Constants;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by brett on 9/30/16.
 */
public class FieldTest extends TestCase {

    @Test
    public void testConstructor() throws Exception {
        Field field = new Field("1|brett");
        assertEquals("Should parse number figure", field.getNumFigure(), 1);
        assertEquals("Should Parse name", field.getName(), "brett");
    }

    @Test
    public void testSetVarcharValue() throws Exception {
        Field field = new Field("1|brett");
        assertEquals("Test Default", field.getVarcharValue(), Field.VARCHAR_DEFAULT_LENGTH);
        field.setVarcharValue(-1);
        assertEquals(field.getVarcharValue(), Field.VARCHAR_DEFAULT_LENGTH);
        field.setVarcharValue(4);
        assertEquals(field.getVarcharValue(), 4);
    }

    @Test
    public void testSetDataType() throws Exception {
        Field field = new Field("1|brett");
        assertEquals(field.getDataType(), 0);
        field.setDataType(1);
        assertEquals(field.getDataType(), 1);
        field.setDataType(100);
        assertEquals(field.getDataType(), 1);
    }

    @Test
    public void testToString() throws Exception {
        Field field = new Field("1|brett");
        field.setTableID(8);
        field.setTableBound(8);
        field.setFieldBound(8);
        field.setDataType(1);
        field.setVarcharValue(4);
        field.setIsPrimaryKey(true);
        field.setDisallowNull(true);
        field.setDefaultValue("foobar");
        assertEquals(field.toString(), String.join(Constants.DELIM, "1", "brett", "8", "8", "8", "1", "4", "true", "true", "foobar"));
    }
}
