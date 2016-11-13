package group1.Util;

/**
 * Created by brett on 11/12/16.
 */
public class ErrorMessages {
    public static String containsRelationships(String fileName) {
        return "The Edge Diagrammer file\n" + fileName + "\ncontains relations.  Please resolve them and try again.";
    }

    public static String containsCompositeAttributes(String fileName) {
        return"The Edge Diagrammer file\n" + fileName + "\ncontains composite attributes. Please resolve them and try again.";
    }

    public static String entityOrAttributeNameBlank() {
        return "There are entities or attributes with blank names in this diagram.\nPlease provide names for them and try again.";
    }

    public static String duplicateTableName(String name) {
        return "There are multiple tables called " + name + " in this diagram.\nPlease rename all but one of them and try again.";
    }

    public static String manyToMany(String table1, String table2) {
        return "There is a many-many relationship between tables\n\"" + table1 + "\" and \"" + table2 + "\"\nPlease resolve this and try again.";
    }

    public static String attributeConnectedToMultipleTables(String name) {
        return "The attribute " + name + " is connected to multiple tables.\nPlease resolve this and try again.";
    }

    public static String unknownFile() {
        return "Unrecognized file format";
    }

    public static String noStyle() {
        return "Style Cannot be empty";
    }
}


