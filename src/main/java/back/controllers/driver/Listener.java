package back.controllers.driver;

import org.apache.commons.io.FileUtils;
import org.testng.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static back.models.reporters.Reports.*;
import static back.models.reporters.ZipDirectory.zipMyReports;

/**
 * Created by jdwinters on 7/19/17.
 */
public class Listener extends TestListenerAdapter implements ITestListener,
        ISuiteListener {

    int testNum, testsLeft;
    static int errNum = 0;

    Map<String, Integer> allTests = new HashMap<>();

    private final long startTime  = System.currentTimeMillis();

    @Override
    public void onTestStart(ITestResult result) { reportMethodUnderTest(result, true); }

    @Override
    public void onTestSuccess(ITestResult result) { testsLeft = reportTestPassFail(result, true, new int[] {testNum, testsLeft}); }

    @Override
    public void onTestFailure(ITestResult result) { testsLeft = reportTestPassFail(result, false,  new int[] {testNum, testsLeft}); }

    @Override
    public void onTestSkipped(ITestResult result) {
        testsLeft = reportTestSkipped( new int[] {testNum, testsLeft});
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

    @Override
    public void onStart(ISuite iSuite) {

        String directoryName = "./src/main/resources/reports";

        File directory = new File(String.valueOf(directoryName));

        if (! directory.exists()){

            directory.mkdir();

        } else {

            try {  FileUtils.cleanDirectory(directory); }  catch (IOException e) {}

        }



        testNum   = 0;
        testsLeft = 0;

        testNum  += iSuite.getAllMethods().size();
        testsLeft = testNum;

        reportTestsAtStart(iSuite);
    }

    @Override
    public void onFinish(ISuite iSuite) {

        reportTestsAtFinish(iSuite);

//        copyReportsForZipping();

        zipMyReports();
    }

    private void copyReportsForZipping() {

        try {

            copyReportDirsToBeZipped(new File("./test-output"),
                    new File("./src/main/resources/reports/reportNg"));

            copyReportDirsToBeZipped(new File("./target/surefire-reports"),
                    new File("./src/main/resources/reports/surefire-output"));

        }  catch (IOException e) { e.printStackTrace(); }

    }


    /**
     * Method to copy surefire-report directory and all contents to the test suite report directory
     * @param sourceLocation
     * @param targetLocation
     * @throws IOException
     */
    public void copyReportDirsToBeZipped(File sourceLocation , File targetLocation) throws IOException {

        if (sourceLocation.isDirectory()) {

            if (!targetLocation.exists()) targetLocation.mkdir();

            String[] children = sourceLocation.list();

            for (int i = 0; i < children.length; i++) {

                copyReportDirsToBeZipped(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }

        } else {

            InputStream in   = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {

                out.write(buf, 0, len);
            }

            in.close();
            out.close();
        }
    }

    private void reportTestsAtStart(ISuite iSuite) {

        Reporter.log("\n*********************************************************************************\n" +
                "*********************************************************************************\n\n" +
                "LAUNCHING "    + iSuite.getXmlSuite().getTests().size() + " TESTS FROM SUITE: " + iSuite.getXmlSuite().getName()      +
                " --- Start Time: " + new SimpleDateFormat("HH:mm:ss").format(new Date(startTime)) + "\n", true);

        int j = 1;
        for (ITestNGMethod m : iSuite.getAllMethods()) {

            Reporter.log(m.getXmlTest().getName() + "_" + m.getMethodName(), true);

            allTests.put(m.getXmlTest().getName() + "_" + m.getMethodName(), j++);
        }

        Reporter.log("\n*********************************************************************************\n" +
                "*********************************************************************************\n", true);
    }

    private void reportTestsAtFinish(ISuite iSuite) {

        Reporter.log("\n*********************************************************************************\n" +
                "*********************************************************************************\n\n" +
                "COMPLETED " + iSuite.getXmlSuite().getTests().size() + " TESTS FROM SUITE: " + iSuite.getXmlSuite().getName() + "\n", true);

        int j = 1;
        for (ITestNGMethod m : iSuite.getAllMethods()) {

            Reporter.log(m.getXmlTest().getName() + "_" + m.getMethodName(), true);

            allTests.put(m.getXmlTest().getName() + "_" + m.getMethodName(), j++);
        }

        Reporter.log("\nTOTAL NUMBER OF ISSUES IN SUITE REPORT: " + errNum, true);

        Reporter.log("\n*********************************************************************************\n" +
                "*********************************************************************************\n", true);
    }

}

