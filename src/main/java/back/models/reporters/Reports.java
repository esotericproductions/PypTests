package back.models.reporters;

import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * Created by jwinters on 9/14/16.
 */
public class Reports {




    public static void     reportMethodUnderTest(ITestResult result, boolean start) {

        Reporter.log("\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
                "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"     +
                (start ? "Launching" : "Terminating") + " Test:" + result.getMethod().getMethodName() + "_" + result.getTestContext().getCurrentXmlTest().getName() + "\n"                            +
                "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"     +
                "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n", true);
    }

    public static int     reportTestPassFail(ITestResult result, boolean passFail, int[] testsLeft) {

        int    remaining   = testsLeft[1];
        String testAndMeth = result.getMethod().getMethodName() + "_" + result.getTestContext().getCurrentXmlTest().getName();

        Reporter.log("\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"               +
                         "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"               +
                          (passFail ? "P A S S E D " : "F A I L E D ")  + "Test: " + testAndMeth   +
                          (passFail ? " P A S S E D" : " F A I L E D")  + "\n"                     +
                         "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"               +
                         "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"               +
                         "Tests remaining in suite: " + --remaining + "\n" +
                         "Tests completed in suite: " + (testsLeft[0] - remaining + "\n\n"), true);

        return remaining;
    }

    public static void reportIssueToJSON(String err, String id) {

        Reporter.log("\n\n!*!*!*!*!#########################################################################!*!*!*!*!" +
                       "\n!*!*!*!*!########=========== POSTING " + id + " ISSUE TO JSON REPORT ===========########!*!*!*!*!" +
                       "\n!*!*!*!*!#########################################################################!*!*!*!*!" +
                       "\n"  +
                       "\n " + err +
                       "\n______________________________________________________________________________________\n\n", true);
    }

    public static int     reportTestSkipped(int[] testsLeft) {

        int remaining = testsLeft[1];

        Reporter.log("\n#################### !!! SKIPPED !! TEST !! SKIPPED !!! ####################\n" +
                     "  #################### !!! Tests remaining in suite: " + --remaining + "\n", true);

        return remaining;
    }


    public static String   reportImagesNotMatch() {

        return "!!!!!!!!!!!!!!! IMAGES DO NOT MATCH !!!!!!!!!!!!!!!";
    }



}
