package group1.Inputs;

import group1.model.*;
import group1.model.Connector;
import group1.Util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by brett on 11/12/16.
 */
public class EdgeStrategy {
    /**
     * SAX based parser for getting attributes out of the edge file.
     * @param br - A buffer reader who's head is pointing at the beginning of a EdgeStrategy Figure
     * @param currentLine - The current line that the buffer reader is on. (Needed for
     * @throws IOException
     */
    private static HashMap<String, String> parseAttributes(BufferedReader br, String currentLine) throws IOException {
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
     *
     * @param br
     * @param currentLine
     * @return
     * @throws IOException
     */
    public static Database open(BufferedReader br, String currentLine) throws IOException {
        ArrayList<Table> tables = new ArrayList<>();
        ArrayList<Field> fields = new ArrayList<>();
        ArrayList<Connector> connectors = new ArrayList<>();
        do {
            if (currentLine.startsWith("Figure ")) {
                Figure figure = new Figure(parseAttributes(br, currentLine), tables);
                if (figure.hasTable()) tables.add(figure.getTable());
                if (figure.hasField()) fields.add(figure.getField());
            }
            if (currentLine.startsWith("Connector ")) {
                HashMap<String, String> attributes = parseAttributes(br, currentLine);
                int numConnector = Integer.parseInt(attributes.get("Connector")); //get the Connector number
                int endPoint1 = Integer.parseInt(attributes.get("Figure1"));
                int endPoint2 = Integer.parseInt(attributes.get("Figure2"));
                connectors.add(new Connector(numConnector + Constants.DELIM + endPoint1 + Constants.DELIM + endPoint2 + Constants.DELIM + null + Constants.DELIM + null));
            }
        } while ((currentLine = br.readLine()) != null);
        return new Database(tables.toArray(new Table[tables.size()]), fields.toArray(new Field[fields.size()]), connectors.toArray(new Connector[connectors.size()]));
    }
}
