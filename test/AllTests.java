import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(CreateDDLMySQLTest.class);
        suite.addTestSuite(EdgeConnectorTest.class);
        suite.addTestSuite(EdgeConvertCreateDDLTest.class);
        suite.addTestSuite(EdgeConvertFileParserTest.class);
        suite.addTestSuite(EdgeFieldTest.class);
        suite.addTestSuite(EdgeTableTest.class);

        //$JUnit-END$
        return suite;
    }

}
