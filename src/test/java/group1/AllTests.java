package group1;

import group1.Inputs.FileParserTest;
import group1.Outputs.MySQLStrategyTest;
import group1.model.ConnectorTest;
import group1.model.FieldTest;
import group1.model.TableTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$

        // inputs
        suite.addTestSuite(FileParserTest.class);
        suite.addTestSuite(group1.Inputs.SaveFileStrategyTest.class);

        //model
        suite.addTestSuite(ConnectorTest.class);
        suite.addTestSuite(FieldTest.class);
        suite.addTestSuite(TableTest.class);

        // outputs
        suite.addTestSuite(MySQLStrategyTest.class);
        suite.addTestSuite(group1.Outputs.SaveFileStrategyTest.class);

        //$JUnit-END$
        return suite;
    }

}
