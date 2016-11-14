package group1.Outputs;

import group1.Util.Constants;
import group1.model.Field;
import group1.model.Table;

/**
 * Created by brett on 11/12/16.
 */
public class SaveFileStrategy {
    /**
     * All outputs must expose this method
     */
    public static String convert(String databaseName, Table[] tables, Field[] fields) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.SAVE_ID + "\r\n");
        sb.append(databaseName + "\r\n");
        for (Table table : tables) {
            sb.append(table.toString() + "\r\n");
        }
        for (Field field : fields) {
            sb.append(field.toString() + "\r\n");
        }
        return sb.toString();
    }
}