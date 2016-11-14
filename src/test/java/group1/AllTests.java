package group1;

import group1.Inputs.FileParserTest;
import group1.Outputs.MySQLTest;
import group1.model.ConnectorTest;
import group1.model.FieldTest;
import group1.model.TableTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(MySQLTest.class);
        suite.addTestSuite(ConnectorTest.class);
        suite.addTestSuite(FileParserTest.class);
        suite.addTestSuite(FieldTest.class);
        suite.addTestSuite(TableTest.class);

        //$JUnit-END$
        return suite;
    }

}
