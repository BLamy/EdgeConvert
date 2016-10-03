import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by brett on 9/30/16.
 */
public class EdgeTableTest extends TestCase {

    @Test
    public void testConstructor() throws Exception {
        EdgeTable edgeTable = new EdgeTable("1|brett");
        assertEquals("Should parse number figure", edgeTable.getNumFigure(), 1);
        assertEquals("Should Parse name", edgeTable.getName(), "brett");
    }

    @Test
    public void testGetName() throws Exception {

    }

    @Test
    public void testAddRelatedTable() throws Exception {

    }

    @Test
    public void testGetRelatedTablesArray() throws Exception {

    }

    @Test
    public void testGetRelatedFieldsArray() throws Exception {

    }

    @Test
    public void testSetRelatedField() throws Exception {

    }

    @Test
    public void testGetNativeFieldsArray() throws Exception {

    }

    @Test
    public void testAddNativeField() throws Exception {

    }

    @Test
    public void testMoveFieldUp() throws Exception {

    }

    @Test
    public void testMoveFieldDown() throws Exception {

    }

    @Test
    public void testMakeArrays() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }
}