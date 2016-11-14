package group1.Outputs;

import group1.fixtures.FakeDatabase;
import group1.model.Database;
import junit.framework.TestCase;
import org.junit.Test;


/**
 * Created by brett on 9/30/16.
 */
public class MySQLStrategyTest extends TestCase {

    @Test
    public void testCreateDDL() throws Exception {
        Database database = FakeDatabase.createFakeDatabase();
        String actual = MySQLStrategy.convert("MySQLDB", database.getTables(), database.getFields()).replaceAll("\\s","");

        String expected = (
            "CREATE DATABASE MySQLDB;" +
            "USE MySQLDB;" +
            "CREATE TABLE users (" +
            "        username VARCHAR(255) NOT NULL," +
            "        ID INT NOT NULL," +
            "        password VARCHAR(255) NOT NULL," +
            "        CONSTRAINT users_PK PRIMARY KEY (username, ID)" +
            ");" +
            "CREATE TABLE messages (" +
            "        isVerified BOOL NOT NULL DEFAULT 0," +
            "        canSendMessages BOOL NOT NULL DEFAULT 1," +
            "        ID INT NOT NULL," +
            "        toUser INT NOT NULL," +
            "        CONSTRAINT messages_PK PRIMARY KEY (ID)," +
            "        CONSTRAINT messages_FK1 FOREIGN KEY(canSendMessages) REFERENCES users(ID)CONSTRAINT messages_FK2 FOREIGN KEY(ID) REFERENCES users(ID)\n" +
            ");"
        ).replaceAll("\\s","");

        assertEquals("", actual, expected);
    }

}
