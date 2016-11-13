package group1;

import group1.model.FieldTest;
import group1.model.TableTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(CreateDDLMySQLTest.class);
        suite.addTestSuite(EdgeConnectorTest.class);
        suite.addTestSuite(EdgeConvertFileParserTest.class);
        suite.addTestSuite(FieldTest.class);
        suite.addTestSuite(TableTest.class);

        //$JUnit-END$
        return suite;
    }

}
