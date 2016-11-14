package group1.Inputs;

import group1.model.Database;
import group1.model.Field;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;

/**
 * Created by brett on 11/13/16.
 */
public class SaveFileStrategyTest extends TestCase {
    Database database;
    public void setUp() {
        database = FileParser.openFile(new File("Courses.dat"));
    }

    @Test
    public void testShouldGetTables() {
        assertEquals("Should be 2 tables", database.getTables().length, 2);
    }

    @Test
    public void testStudentsTable() {
        String actual = database.getTables()[0].toString();
        String expected = "Table:0{TableName:usersNativeFields:0|1|2RelatedTables:1RelatedFields:0|0|0}";
        assertEquals("First Table Should be named users", actual.replaceAll("\\s+", ""), expected);
    }

    @Test
    public void testFacultyTable() {
        String actual = database.getTables()[1].toString();
        String expected = "Table:1{TableName:messagesNativeFields:3|4|5|6RelatedTables:0RelatedFields:0|1|1|0}";
        assertEquals("second Table Should be named messages", actual.replaceAll("\\s+", ""), expected);
    }

    @Test
    public void testFieldNames() throws Exception {
        Field[] fields = database.getFields();
        assertEquals("Should be 7 fields", fields.length, 9);
        assertEquals("First Field",  "1|ID|0|0|0|2|1|true|true|", fields[0].toString());
        assertEquals("Second Field", "3|isVerified|0|0|0|1|1|false|true|false", fields[1].toString());
        assertEquals("Third Field",  "4|canSendMessages|0|0|0|1|1|false|true|true", fields[2].toString());
        assertEquals("Fourth Field", "0|username|0|0|0|0|255|true|true|",  fields[3].toString());
        assertEquals("Fifth Field",  "2|password|0|0|0|0|255|false|true|", fields[4].toString());
        assertEquals("Sixth Field",  "5|ID|1|0|0|2|1|true|true|", fields[5].toString());
        assertEquals("Seventh Field", "6|toUser|1|1|0|2|1|false|true|",fields[6].toString());
        assertEquals("Eighth Field", "7|fromUser|1|2|0|2|1|false|true|",fields[7].toString());
        assertEquals("Ninth Field", "8|message|1|0|0|0|255|false|true|Sent from Connector",fields[8].toString());
    }
}
