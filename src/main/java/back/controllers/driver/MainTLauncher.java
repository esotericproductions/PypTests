package back.controllers.driver;

import front.pages.Pages_InitElements;
import back.models.data_obj.applications.PApplicationData;
import back.models.data_obj.global.GlobalData;
import back.models.data_src.data_providers.TestDataObject;
import back.models.validation.ValidatorManager;
import front.helpers.Helper_RandomString;
import io.appium.java_client.android.AndroidDriver;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static back.controllers.driver.Listener.errNum;
import static back.models.reporters.Helper_ReportDirs.makeSuiteReportDirs;
import static back.models.reporters.Helper_ReportDirs.makeTestReportDirs;
import static front.helpers.Helper_RandomString.prettyFormatter;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_ACTIVITY;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_PACKAGE;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.AVD;
import static io.appium.java_client.remote.MobileCapabilityType.*;

public class MainTLauncher {

    private String reportDir;
    private String testId;
    private String suiteName;
    protected String dataProvider;
    private static int failureRatio;

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Pages_InitElements pages;
    protected Wait<WebDriver> waiter;

    private TestDataObject testData;

    protected PApplicationData appData;
    protected GlobalData dataGlobal;
    protected ValidatorManager validatorMngr;

    private Properties prop;

    private static final String time = new Date().toString().replace(" ", "_").replace(":", "");
    private static final String identifier = new Helper_RandomString(5).nextString();

    private static final String QA_TEST_PROPERTIES = "src/main/java/back/controllers/props/qa.env";
    private static String config = null;
    protected String queries;

    @BeforeSuite
    public void startUp() {

        InputStream propInputStrm;

        try {
            prop = System.getProperties();
            config = QA_TEST_PROPERTIES;
            Reporter.log("Use default env file " + config, true);
            propInputStrm = new FileInputStream(config);
            prop.load( propInputStrm );
            Reporter.log("Successfully loaded " + config, true);
            failureRatio     = Integer.parseInt(prop.getProperty("failureRatio"));
        } catch(Exception exec) {
            exec.printStackTrace();
        }
    }


    @Parameters({"testId","suiteName"})
    @BeforeTest
    public void  data(@Optional String testId, @Optional String suiteName) {

        try {
                prop = System.getProperties();
                prop.load(new FileInputStream(config));

                this.suiteName      = suiteName;
                this.testId         = testId;
                this.reportDir      = prop.getProperty("reportDir");
                this.dataProvider   = getDataProvider();

                dataGlobal          = new GlobalData();

                String suiteReportDir = reportDir + suiteName + "_" + time;
                dataGlobal.setReportDir(suiteReportDir);
                dataGlobal.setTestId(testId);
                dataGlobal.setId(testId + "_" + identifier);
                makeSuiteReportDirs(suiteReportDir);

            } catch (Exception e) {
                e.printStackTrace();
        }
    }


    @Parameters({"testId", "queries"})
    @BeforeMethod
    public void init(Method meth, String testId, @Optional String queries) {

        try {

            this.testId         = testId;
            this.queries        = queries;

            String name = testId + "_" + meth.getName();
            dataGlobal.setMethodUnderTest(name);
            makeTestReportDirs(name, dataGlobal);
            createJsonReport(dataGlobal);
            validatorMngr = new ValidatorManager(dataGlobal);

        } catch (Exception d) { d.printStackTrace(); }
    }

    /**
     * Beautify tests-level JSON report w/ test case params and JSONArray w/ issues
     * posted from JSONReporter(). Master JSON report file is rebuilt/updated after
     * every test case to provide real-time tracking/reporting.
     */
    @AfterTest
    public void  shutdown() {
        try{
            if(!new File(dataGlobal.getReportDir() + "/"
                    + dataGlobal.getReportName() + "/jsonReport.json").exists()) {
                FileWriter file =
                        new FileWriter(new File(dataGlobal.getReportDir()
                                + "/" + dataGlobal.getReportName() + "/jsonReport.json"));

                file.write(prettyFormatter(dataGlobal.getJsonReport().toString()));
                file.flush();
                file.close();
            }
            int errors = new JSONObject(
                    new String(Files.readAllBytes(
                            Paths.get(dataGlobal.getReportDir() + "/"
                                    + dataGlobal.getReportName()
                                        + "/jsonReport.json")))).getInt("issues_total");
            if(errors > failureRatio)
                Assert.fail("###### FAILED!!! NUMBER OF ERRORS "
                        + "{" + errors + "}" + " EXCEEDS THRESHOLD ######");
        } catch (IOException io) { io.printStackTrace(); }
        driver.quit();
        makeMasterJson();
    }

    /**
     * Initialize driver, page objects and test data
     * @param data
     * @throws Exception
     */
    public void setup(Object data) {

        this.testData = (TestDataObject) data;
        this.queries = testData.getTestCaseData().get("queries").trim();
        driverSetup();
        wait   = new WebDriverWait(driver, 180);
        waiter = new FluentWait<>(driver).withTimeout(1100, TimeUnit.SECONDS)
                .pollingEvery(20, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
    }

    /**
     *
     * @param //TODO this should get an enum for test type for associated data file
     * @return the data provider csv to be used for specified environment
     */
    private String getDataProvider() { return "DataDrivenPTest_qa"; }

    /**
     * Initial creation of tests-level JSON report
     * @param data
     */
    private void createJsonReport(GlobalData data) {

        JSONObject jsonReport = new JSONObject();

        jsonReport.put("testId", testId);
        jsonReport.put("queries", queries);
        jsonReport.put("testSuite", suiteName);
        jsonReport.put("issues_total", 0);

        jsonReport.put("failures",     new JSONArray());

        data.setJsonReport(jsonReport);
    }

    /**
     * Initializing driver
     */
    private void driverSetup() {

        DesiredCapabilities desiredCaps = new DesiredCapabilities();
        desiredCaps.setCapability(DEVICE_NAME,"Nexus 4 API 22");
        desiredCaps.setCapability(PLATFORM_VERSION, "5.1");
        desiredCaps.setCapability(NO_RESET,"true");
        desiredCaps.setCapability(FULL_RESET,"false");
        desiredCaps.setCapability(APP_PACKAGE, "com.exo.pip");
        desiredCaps.setCapability(APP_ACTIVITY, "com.exo.pip.activity.MainActivity");
        desiredCaps.setCapability(AVD,"Nexus_4_API_22");
        try {
            driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), desiredCaps);
        } catch (Exception d) { d.printStackTrace(); }

        pages = new Pages_InitElements(driver);
    }


    private void makeMasterJson() {

        List<String> issues = new ArrayList<>();

        JSONObject jo = new JSONObject();
        JSONArray ja  = new JSONArray();

        String suiteName;
        String name;
        errNum = 0;

        try {
            File[] files = new File("./src/main/resources/reports").listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    Reporter.log("\nDirectory: " + file.getName(), true);
                    suiteName = file.getName();

                    for (File filet : new File(file.getAbsolutePath()).listFiles()) {

                        if (filet.isDirectory()  && new File(filet.getAbsolutePath()
                                + "/jsonReport.json").exists()) {
                            JSONObject jobject = new JSONObject(
                                    new String(Files.readAllBytes(
                                            Paths.get(filet.getAbsolutePath()
                                                    + "/jsonReport.json"))));
                            try {
                                String totIss = "";
                                try {
                                    totIss = " - " + Integer.toString(jobject.getInt("issues_total"));
                                } catch (Exception e) { e.printStackTrace(); }

                                if (jobject.getInt("issues_total") > 0)
                                    issues.add(jobject.getString("testId") + totIss);

                                errNum+= jobject.getInt("issues_total");
                            } catch (JSONException je) { je.printStackTrace(); }
                            ja.put(new JSONObject().put("test_result", jobject));
                        }
                    }

                    name = suiteName.split("_")[0] + "_suite_report";

                    jo.put(name, ja);
                    jo.put("count_issues", errNum);
                    jo.put("tests_issues", issues);

                    FileWriter reportFile =
                            new FileWriter(new File(file.getAbsolutePath() + "/AllIssuesReport.json"));

                    reportFile.write(prettyFormatter(jo.toString()));
                    reportFile.flush();
                    reportFile.close();
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

}
