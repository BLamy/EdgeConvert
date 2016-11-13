package group1;

import group1.model.Field;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;

/**
 * Created by brett on 9/30/16.
 */
public class EdgeConvertFileParserTest extends TestCase {
    EdgeConvertFileParser parser;
    public void setUp() {
        parser = new EdgeConvertFileParser(new File("Courses.edg"));
    }

    @Test
    public void testShouldGetTables() {
        assertEquals("Should be 3 tables", parser.getEdgeTables().length, 3);
    }

    @Test
    public void testStudentsTable() {
        String actual = parser.getEdgeTables()[0].toString();
        String expected = "Table:1{TableName:STUDENTNativeFields:7|8RelatedTables:RelatedFields:0|0}";
        assertEquals("First Table Should be named student", actual.replaceAll("\\s+", ""), expected);
    }

    @Test
    public void testFacultyTable() {
        String actual = parser.getEdgeTables()[1].toString();
        String expected = "Table:2{TableName:FACULTYNativeFields:11|6RelatedTables:13RelatedFields:0|0}";
        assertEquals("First Table Should be named student", actual.replaceAll("\\s+", ""), expected);
    }
    @Test
    public void testCoursesTable() {
        String actual = parser.getEdgeTables()[2].toString();
        String expected = "Table:13{TableName:COURSESNativeFields:3|5RelatedTables:2RelatedFields:0|0}";
        assertEquals("First Table Should be named student", actual.replaceAll("\\s+", ""), expected);
    }
    @Test
    public void testFieldNames() throws Exception {
        Field[] fields = parser.getEdgeFields();
        assertEquals("Should be 7 fields", fields.length, 7);
        assertEquals("First Field", fields[0].toString(), "3|Grade|13|0|0|0|1|false|false|");
        assertEquals("Second Field", fields[1].toString(), "4|CourseName|0|0|0|0|1|false|false|");
        assertEquals("Third Field", fields[2].toString(), "5|Number|13|0|0|0|1|false|false|");
        assertEquals("Fourth Field", fields[3].toString(), "6|FacSSN|2|0|0|0|1|false|false|");
        assertEquals("Fifth Field", fields[4].toString(), "7|StudentSSN|1|0|0|0|1|false|false|");
        assertEquals("Sixth Field", fields[5].toString(), "8|StudentName|1|0|0|0|1|false|false|");
        assertEquals("Seventh Field", fields[6].toString(), "11|FacultyName|2|0|0|0|1|false|false|");
    }


//    @Test
//    public void testParseSaveFile() throws Exception { // TODO saveFile is completely broken.
////        EdgeConvertFileParser parser = new EdgeConvertFileParser(new File("Courses.edg"));
////        parser.parseSaveFile();
////        assertEquals("Should properly open Courses file", parser.getEdgeFields().length, 7);
//    }
}
