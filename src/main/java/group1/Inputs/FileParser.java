package group1.Inputs;

import group1.Util.Constants;
import group1.Util.ErrorMessages;
import group1.model.Database;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;


public class FileParser {
    /**
     * Internally handle different file types
     */
    private static Database openFile(BufferedReader br, String currentLine) throws IOException {
        switch (getFileType(currentLine)) {
            case Constants.EDGE_ID:
                return EdgeStrategy.open(br, currentLine);
            case Constants.SAVE_ID:
                return SaveFileStrategy.open(br, currentLine);
            default: return null;
        }
    }

    /**
     * SAX based parser for getting attributes out of the edge file.
     * @param br - A buffer reader who's head is pointing at the beginning of a EdgeStrategy Figure
     * @param currentLine - The current line that the buffer reader is on. (Needed for
     * @throws IOException
     */
    public static HashMap<String, String> parseAttributes(BufferedReader br, String currentLine) throws IOException {
        HashMap<String, String> attributes = new HashMap<>();
        do {
            String[] components = currentLine.split(" ");
            if (components.length == 2) {
                attributes.put(components[0], components[1].replaceAll("\"", ""));
            }
            currentLine = br.readLine().trim();
        } while (!currentLine.equals("}"));
        return attributes;
    }

    /**
     * Gets the type of file given the first line of that file
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
     * Will the given file
     */
    public static Database openFile(File inputFile) {
        Database database = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String currentLine = br.readLine().trim();
            if (getFileType(currentLine) == null) {
                JOptionPane.showMessageDialog(null, ErrorMessages.unknownFile());
                return null;
            }
            database = openFile(br, currentLine);
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
