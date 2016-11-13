package group1;

import group1.Strategies.MySQL;

import javax.swing.*;

public class CreateDDLMySQL {

    protected EdgeTable[] tables; //master copy of EdgeTable objects
    protected EdgeField[] fields; //master copy of EdgeField objects
    protected String databaseName;
    protected StringBuffer sb;

    public CreateDDLMySQL(EdgeTable[] tables, EdgeField[] fields) {
        this.tables = tables;
        this.fields = fields;
        this.sb = new StringBuffer();
    }


    public String generateDatabaseName() { //prompts user for database name
        do {
            databaseName = (String) JOptionPane.showInputDialog(
                null, "Enter the database name:", "Database Name",
                JOptionPane.PLAIN_MESSAGE, null, null, "MySQLDB"
            );
            if (databaseName == null) {
                EdgeConvertGUI.setReadSuccess(false);
                return "";
            }
            if (databaseName.equals("")) {
                JOptionPane.showMessageDialog(null, "You must select a name for your database.");
            }
        } while (databaseName.equals(""));
        return databaseName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getProductName() {
        return "MySQL";
    }

    public String getSQLString() {
        EdgeConvertGUI.setReadSuccess(true);
        return MySQL.convert(generateDatabaseName(), tables, fields);
    }

}
