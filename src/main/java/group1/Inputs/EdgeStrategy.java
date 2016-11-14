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
     * All inputs must expose this method
     */
    public static Database open(BufferedReader br, String currentLine) throws IOException {
        ArrayList<Table> tables = new ArrayList<>();
        ArrayList<Field> fields = new ArrayList<>();
        ArrayList<Connector> connectors = new ArrayList<>();
        do {
            if (currentLine.startsWith("Figure ")) {
                Figure figure = new Figure(FileParser.parseAttributes(br, currentLine), tables);
                if (figure.hasTable()) tables.add(figure.getTable());
                if (figure.hasField()) fields.add(figure.getField());
            }
            if (currentLine.startsWith("Connector ")) {
                HashMap<String, String> attributes = FileParser.parseAttributes(br, currentLine);
                int numConnector = Integer.parseInt(attributes.get("Connector")); //get the Connector number
                int endPoint1 = Integer.parseInt(attributes.get("Figure1"));
                int endPoint2 = Integer.parseInt(attributes.get("Figure2"));
                connectors.add(new Connector(numConnector + Constants.DELIM + endPoint1 + Constants.DELIM + endPoint2 + Constants.DELIM + null + Constants.DELIM + null));
            }
        } while ((currentLine = br.readLine()) != null);
        return new Database(tables.toArray(new Table[tables.size()]), fields.toArray(new Field[fields.size()]), connectors.toArray(new Connector[connectors.size()]));
    }
}
