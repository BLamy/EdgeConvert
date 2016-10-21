package group1;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;

/**
 * Created by brett on 9/30/16.
 */
public class EdgeConvertFileParserTest extends TestCase {

    @Test
    public void testParseEdgeFile() throws Exception {
        EdgeConvertFileParser parser = new EdgeConvertFileParser(new File("Courses.edg"));
        assertEquals("Should properly open Courses file", parser.getEdgeFields().length, 7);
    }

//    @Test
//    public void testParseSaveFile() throws Exception { // TODO saveFile is completely broken.
////        EdgeConvertFileParser parser = new EdgeConvertFileParser(new File("Courses.edg"));
////        parser.parseSaveFile();
////        assertEquals("Should properly open Courses file", parser.getEdgeFields().length, 7);
//    }
}
