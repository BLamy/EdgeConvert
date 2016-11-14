package group1.Inputs;

import group1.Util.Constants;
import group1.Util.ErrorMessages;
import group1.model.Database;

import javax.swing.*;
import java.io.*;


public class FileParser {

    /**
     * Internally handle different file types
     */
    private static Database openFile(BufferedReader br, String currentLine, String type) throws IOException {
        switch (type) {
            case Constants.EDGE_ID:
                return EdgeStrategy.open(br, currentLine);
            case Constants.SAVE_ID:
                return SaveFileStrategy.open(br, currentLine);
            default: return null;
        }
    }

    /**
     *
     * @param firstLine
     * @return
     */
    public static String getFileType(String firstLine) {
        if (firstLine.startsWith(Constants.EDGE_ID)) {
            return Constants.EDGE_ID;
        } else if(firstLine.startsWith(Constants.SAVE_ID)) {
            return Constants.SAVE_ID;
        }
        return null;
    }

    /**
     *
     * @param inputFile
     * @return
     */
    public static Database openFile(File inputFile) {
        Database database = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String currentLine = br.readLine().trim();
            String fileType = getFileType(currentLine); // First line determines file type
            if (fileType == null) {
                JOptionPane.showMessageDialog(null, ErrorMessages.unknownFile());
                return null;
            }
            database = openFile(br, currentLine, fileType);
            br.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("Cannot find \"" + inputFile.getName() + "\".");
            System.exit(0);
        } catch (IOException ioe) {
            System.out.println(ioe);
            System.exit(0);
        }
        return database;
    }
}
