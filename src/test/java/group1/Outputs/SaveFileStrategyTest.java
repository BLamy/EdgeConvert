package group1.Outputs;

import group1.fixtures.FakeDatabase;
import group1.model.Database;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by brett on 11/13/16.
 */
public class SaveFileStrategyTest extends TestCase {

    @Test
    public void testCreateSaveFile() throws Exception {
        Database database = FakeDatabase.createFakeDatabase();
        String actual = SaveFileStrategy.convert("MySQLDB", database.getTables(), database.getFields()).replaceAll("\\s","");

        String expected = (
                "EdgeConvert Save File" +
                "MySQLDB" +
                "Table: 0" +
                "{" +
                    "TableName: users" +
                    "NativeFields: 0|1|2" +
                    "RelatedTables: 1" +
                    "RelatedFields: 0|0|0" +
                "}" +
                "Table: 1" +
                "{" +
                    "TableName: messages" +
                    "NativeFields: 3|4|5|6" +
                    "RelatedTables: 0" +
                    "RelatedFields: 0|1|1|0" +
                "}" +
                "1|ID|0|0|0|2|1|true|true|" +
                "3|isVerified|0|0|0|1|1|false|true|false" +
                "4|canSendMessages|0|0|0|1|1|false|true|true" +
                "0|username|0|0|0|0|255|true|true|" +
                "2|password|0|0|0|0|255|false|true|" +
                "5|ID|1|0|0|2|1|true|true|" +
                "6|toUser|1|0|1|2|1|false|true|" +
                "7|fromUser|1|0|2|2|1|false|true|" +
                "8|message|1|0|0|0|255|false|true|Sent from Connector"
        ).replaceAll("\\s","");

        assertEquals("", actual, expected);
    }
}
