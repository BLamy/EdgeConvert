import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.*;

/**
 * Created by brett on 9/30/16.
 */
public class CreateDDLMySQLTest extends TestCase {
    // Field Types
    private final int VARCHAR = 0;
    private final int BOOL = 1;
    private final int INT = 2;
    private final int DOUBLE = 3;



    @Test
    public void testCreateDDL() throws Exception {
        EdgeTable usersTable = new EdgeTable("0|users");
        usersTable.addRelatedTable(1);
        usersTable.addNativeField(0);
        usersTable.addNativeField(1);
        usersTable.addNativeField(2);
        usersTable.makeArrays();

        EdgeField userId = new EdgeField("1|ID"); // TODO: BUG no way to create a FK where the to field is 0
        userId.setTableID(0);
        userId.setDisallowNull(true);
        userId.setIsPrimaryKey(true);
        userId.setDataType(INT);

        EdgeField username = new EdgeField("0|username");
        username.setTableID(0);
        username.setDisallowNull(true);
        username.setIsPrimaryKey(true);
        username.setVarcharValue(255);
        username.setDataType(VARCHAR);

        EdgeField password = new EdgeField("2|password");
        password.setTableID(0);
        password.setDisallowNull(true);
        password.setVarcharValue(255);
        password.setDataType(VARCHAR);

        EdgeField isVerified = new EdgeField("3|isVerified");
        isVerified.setTableID(0);
        isVerified.setDisallowNull(true);
        isVerified.setDataType(BOOL);
        isVerified.setDefaultValue("false");

        EdgeField canSendMessages = new EdgeField("4|canSendMessages");
        canSendMessages.setTableID(0);
        canSendMessages.setDisallowNull(true);
        canSendMessages.setDataType(BOOL);
        canSendMessages.setDefaultValue("true");

        EdgeTable messagesTable = new EdgeTable("1|messages");
        messagesTable.addRelatedTable(0);
        messagesTable.addNativeField(3);
        messagesTable.addNativeField(4);
        messagesTable.addNativeField(5);
        messagesTable.addNativeField(6);
        messagesTable.makeArrays();
        messagesTable.setRelatedField(1, 1);
        messagesTable.setRelatedField(2, 1);

        EdgeField messageId = new EdgeField("5|ID");
        messageId.setTableID(1);
        messageId.setDisallowNull(true);
        messageId.setIsPrimaryKey(true);
        messageId.setDataType(INT);

        EdgeField toUser = new EdgeField("6|toUser");
        toUser.setTableID(1);
        toUser.setTableBound(0);
        toUser.setFieldBound(1);
        toUser.setDisallowNull(true);
        toUser.setDataType(INT);

        EdgeField fromUser = new EdgeField("7|fromUser");
        fromUser.setTableID(1);
        fromUser.setTableBound(0);
        fromUser.setFieldBound(2);
        fromUser.setDisallowNull(true);
        fromUser.setDataType(INT);

        EdgeField message = new EdgeField("8|message");
        message.setTableID(1);
        message.setDisallowNull(true);
        message.setDefaultValue("Sent from EdgeConnector");
        message.setVarcharValue(255);
        message.setDataType(VARCHAR);

        CreateDDLMySQL spy = Mockito.spy(new CreateDDLMySQL(
                new EdgeTable[] { usersTable, messagesTable },
                new EdgeField[] {
                        userId, isVerified, canSendMessages, username, password,
                        messageId, toUser, fromUser, message
                }
        ));

        Mockito.doReturn("MySQLDB").when(spy).generateDatabaseName();


        String expected = (
            "        CREATE DATABASE MySQLDB;\n" +
            "        USE MySQLDB;\n" +
            "        CREATE TABLE users (\n" +
            "                username VARCHAR(255) NOT NULL,\n" +
            "                ID INT NOT NULL,\n" +
            "                password VARCHAR(255) NOT NULL,\n" +
            "                CONSTRAINT users_PK PRIMARY KEY (username, ID)\n" +
            "        );\n" +
            "\n" +
            "        CREATE TABLE messages (\n" +
            "                isVerified BOOL NOT NULL DEFAULT 0,\n" +
            "                canSendMessages BOOL NOT NULL DEFAULT 1,\n" +
            "                ID INT NOT NULL,\n" +
            "                toUser INT NOT NULL,\n" +
            "                CONSTRAINT messages_PK PRIMARY KEY (ID),\n" +
            "                CONSTRAINT messages_FK1 FOREIGN KEY(canSendMessages) REFERENCES users(ID)CONSTRAINT messages_FK2 FOREIGN KEY(ID) REFERENCES users(ID)\n" +
            "        );"
            ).replaceAll("\\s","");

        String actual = spy.getSQLString().replaceAll("\\s","");
        assertEquals("", actual, expected);
    }

}