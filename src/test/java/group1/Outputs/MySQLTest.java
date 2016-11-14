package group1.Outputs;

import group1.model.Field;
import group1.model.Table;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by brett on 9/30/16.
 */
public class MySQLTest extends TestCase {
    // Field Types
    private final int VARCHAR = 0;
    private final int BOOL = 1;
    private final int INT = 2;
    private final int DOUBLE = 3;



    @Test
    public void testCreateDDL() throws Exception {
        Table usersTable = new Table("0|users");
        usersTable.addRelatedTable(1);
        usersTable.addNativeField(0);
        usersTable.addNativeField(1);
        usersTable.addNativeField(2);
        usersTable.makeArrays();

        Field userId = new Field("1|ID"); // TODO: BUG no way to create a FK where the to field is 0
        userId.setTableID(0);
        userId.setDisallowNull(true);
        userId.setIsPrimaryKey(true);
        userId.setDataType(INT);

        Field username = new Field("0|username");
        username.setTableID(0);
        username.setDisallowNull(true);
        username.setIsPrimaryKey(true);
        username.setVarcharValue(255);
        username.setDataType(VARCHAR);

        Field password = new Field("2|password");
        password.setTableID(0);
        password.setDisallowNull(true);
        password.setVarcharValue(255);
        password.setDataType(VARCHAR);

        Field isVerified = new Field("3|isVerified");
        isVerified.setTableID(0);
        isVerified.setDisallowNull(true);
        isVerified.setDataType(BOOL);
        isVerified.setDefaultValue("false");

        Field canSendMessages = new Field("4|canSendMessages");
        canSendMessages.setTableID(0);
        canSendMessages.setDisallowNull(true);
        canSendMessages.setDataType(BOOL);
        canSendMessages.setDefaultValue("true");

        Table messagesTable = new Table("1|messages");
        messagesTable.addRelatedTable(0);
        messagesTable.addNativeField(3);
        messagesTable.addNativeField(4);
        messagesTable.addNativeField(5);
        messagesTable.addNativeField(6);
        messagesTable.makeArrays();
        messagesTable.setRelatedField(1, 1);
        messagesTable.setRelatedField(2, 1);

        Field messageId = new Field("5|ID");
        messageId.setTableID(1);
        messageId.setDisallowNull(true);
        messageId.setIsPrimaryKey(true);
        messageId.setDataType(INT);

        Field toUser = new Field("6|toUser");
        toUser.setTableID(1);
        toUser.setTableBound(0);
        toUser.setFieldBound(1);
        toUser.setDisallowNull(true);
        toUser.setDataType(INT);

        Field fromUser = new Field("7|fromUser");
        fromUser.setTableID(1);
        fromUser.setTableBound(0);
        fromUser.setFieldBound(2);
        fromUser.setDisallowNull(true);
        fromUser.setDataType(INT);

        Field message = new Field("8|message");
        message.setTableID(1);
        message.setDisallowNull(true);
        message.setDefaultValue("Sent from Connector");
        message.setVarcharValue(255);
        message.setDataType(VARCHAR);

        String dbName = "MySQLDB";
        Table[] tables = new Table[] { usersTable, messagesTable };
        Field[] fields = new Field[] { userId, isVerified, canSendMessages, username, password, messageId, toUser, fromUser, message };
        String actual = MySQL.convert(dbName, tables, fields).replaceAll("\\s","");

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

        assertEquals("", actual, expected);
    }

}
