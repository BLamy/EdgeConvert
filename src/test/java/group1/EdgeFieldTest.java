package group1;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by brett on 9/30/16.
 */
public class EdgeFieldTest extends TestCase {

    @Test
    public void testConstructor() throws Exception {
        EdgeField edgeField = new EdgeField("1|brett");
        assertEquals("Should parse number figure", edgeField.getNumFigure(), 1);
        assertEquals("Should Parse name", edgeField.getName(), "brett");
    }

    @Test
    public void testSetVarcharValue() throws Exception {
        EdgeField edgeField = new EdgeField("1|brett");
        assertEquals("Test Default", edgeField.getVarcharValue(), EdgeField.VARCHAR_DEFAULT_LENGTH);
        edgeField.setVarcharValue(-1);
        assertEquals(edgeField.getVarcharValue(), EdgeField.VARCHAR_DEFAULT_LENGTH);
        edgeField.setVarcharValue(4);
        assertEquals(edgeField.getVarcharValue(), 4);
    }

    @Test
    public void testSetDataType() throws Exception {
        EdgeField edgeField = new EdgeField("1|brett");
        assertEquals(edgeField.getDataType(), 0);
        edgeField.setDataType(1);
        assertEquals(edgeField.getDataType(), 1);
        edgeField.setDataType(100);
        assertEquals(edgeField.getDataType(), 1);
    }

    @Test
    public void testToString() throws Exception {
        EdgeField edgeField = new EdgeField("1|brett");
        edgeField.setTableID(8);
        edgeField.setTableBound(8);
        edgeField.setFieldBound(8);
        edgeField.setDisallowNull(true);
        edgeField.setIsPrimaryKey(true);
        edgeField.setDefaultValue("foobar");
        edgeField.setVarcharValue(4);
        edgeField.setDataType(1);
        assertEquals(edgeField.toString(),
                edgeField.getNumFigure() + EdgeConvertFileParser.DELIM +
                edgeField.getName() + EdgeConvertFileParser.DELIM +
                edgeField.getTableID() + EdgeConvertFileParser.DELIM +
                edgeField.getTableBound() + EdgeConvertFileParser.DELIM +
                edgeField.getFieldBound() + EdgeConvertFileParser.DELIM +
                edgeField.getDataType() + EdgeConvertFileParser.DELIM +
                edgeField.getVarcharValue() + EdgeConvertFileParser.DELIM +
                edgeField.getIsPrimaryKey() + EdgeConvertFileParser.DELIM +
                edgeField.getDisallowNull() + EdgeConvertFileParser.DELIM +
                edgeField.getDefaultValue()
        );

    }
}
