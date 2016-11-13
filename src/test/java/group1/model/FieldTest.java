package group1.model;

import group1.EdgeConvertFileParser;
import group1.model.Field;
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
        field.setDisallowNull(true);
        field.setIsPrimaryKey(true);
        field.setDefaultValue("foobar");
        field.setVarcharValue(4);
        field.setDataType(1);
        assertEquals(field.toString(),
                field.getNumFigure() + EdgeConvertFileParser.DELIM +
                field.getName() + EdgeConvertFileParser.DELIM +
                field.getTableID() + EdgeConvertFileParser.DELIM +
                field.getTableBound() + EdgeConvertFileParser.DELIM +
                field.getFieldBound() + EdgeConvertFileParser.DELIM +
                field.getDataType() + EdgeConvertFileParser.DELIM +
                field.getVarcharValue() + EdgeConvertFileParser.DELIM +
                field.getIsPrimaryKey() + EdgeConvertFileParser.DELIM +
                field.getDisallowNull() + EdgeConvertFileParser.DELIM +
                field.getDefaultValue()
        );

    }
}
