import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by brett on 9/30/16.
 */
public class CreateDDLMySQLTest extends TestCase {
    CreateDDLMySQL fixture;

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
//        usersTable.setRelatedField(0, 4);
//        usersTable.setRelatedField(0, 5);

        EdgeField userId = new EdgeField("0|ID");
        userId.setTableID(0);
//        userId.setTableBound(8);
//        userId.setFieldBound(8);
        userId.setDisallowNull(true);
        userId.setIsPrimaryKey(true);
        userId.setDataType(INT);

        EdgeField username = new EdgeField("1|username");
        username.setTableID(0);
//        username.setTableBound(8);
//        username.setFieldBound(8);
        username.setDisallowNull(true);
        username.setVarcharValue(255);
        username.setDataType(VARCHAR);

        EdgeField password = new EdgeField("2|password");
        password.setTableID(0);
//        password.setTableBound(8);
//        password.setFieldBound(8);
        password.setDisallowNull(true);
        password.setVarcharValue(255);
        password.setDataType(VARCHAR);

        EdgeTable messagesTable = new EdgeTable("1|messages");
        messagesTable.addRelatedTable(0);
        messagesTable.addNativeField(3);
        messagesTable.addNativeField(4);
        messagesTable.addNativeField(5);
        messagesTable.addNativeField(6);
        messagesTable.makeArrays();
//        messagesTable.setRelatedField(4, 0);
//        messagesTable.setRelatedField(5, 0);

        EdgeField messageId = new EdgeField("3|ID");
        messageId.setTableID(1);
//        messageId.setTableBound(8);
//        messageId.setFieldBound(8);
        messageId.setDisallowNull(true);
        messageId.setIsPrimaryKey(true);
        messageId.setDataType(INT);

        EdgeField toUser = new EdgeField("4|toUser");
        messageId.setTableID(1);
//        messageId.setTableBound(0);
        messageId.setDisallowNull(true);
        messageId.setDataType(INT);

        EdgeField fromUser = new EdgeField("5|fromUser");
        messageId.setTableID(1);
//        messageId.setTableBound(0);
        messageId.setDisallowNull(true);
        messageId.setDataType(BOOL);

        EdgeField message = new EdgeField("6|message");
        message.setTableID(1);
        message.setDisallowNull(true);
        message.setDefaultValue("");
        message.setVarcharValue(255);
        message.setDataType(VARCHAR);

        fixture = new CreateDDLMySQL(
                new EdgeTable[] { usersTable, messagesTable },
                new EdgeField[] {
                        userId, username, password,
                        messageId, toUser, fromUser, message
                }
        );

//        fixture.createDDL();

        System.out.println(fixture.getSQLString());
    }

    @Test
    public void testConvertStrBooleanToInt() throws Exception {

    }

    @Test
    public void testGenerateDatabaseName() throws Exception {

    }

    @Test
    public void testGetDatabaseName() throws Exception {

    }

    @Test
    public void testGetProductName() throws Exception {

    }

    @Test
    public void testGetSQLString() throws Exception {

    }
}