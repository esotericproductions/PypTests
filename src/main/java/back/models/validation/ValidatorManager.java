package back.models.validation;

import back.models.data_obj.global.GlobalData;
import back.models.exceptions.CustomBooleanValueIsFalseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static back.models.reporters.JSONReporter.putIssueInJson;

/**
 * Created by jdwinters on 7/15/17.
 */
public class ValidatorManager {


    private GlobalData data;
    private int totalExpected;

    public ValidatorManager(GlobalData data) {

        this.data   = data;
    }

    public void evaluateSystemStateAsExpected(WebDriver driver, Exception e) {

        try {
            putIssueInJson(driver, data,
                    (e.getMessage().contains("\n") ? e.getMessage().split("\n")[0] : e.getMessage()),
                    getStackTraceElements(exceptionToString(e)));

        } catch (Exception f) {

            putIssueInJson(driver, data, e.getMessage() != null ? e.getMessage()
                    : "*** No Message Provided in ST ***", getStackTraceElements(exceptionToString(e)));
        }
    }



    public void setExpectedConditions(Object expected) {

        List<WebElement>  expectedElementsOnPage = new ArrayList<>();
        List<Boolean>     expectedByCallsOnPage  = new ArrayList<>();
        List<Boolean>     expectedAPICallsOnPage = new ArrayList<>();

        for(int tt = 0; tt < ((List) expected).size(); tt++) {
            if(((List) expected).get(tt) instanceof WebElement) {
                expectedElementsOnPage.add( (WebElement) ((List) expected).get(tt));
            }
            if(((List) expected).get(tt) instanceof By) {
                expectedByCallsOnPage.add( (Boolean) ((List) expected).get(tt));
            }
            if(((List) expected).get(tt) instanceof Boolean) {
                expectedAPICallsOnPage.add( (Boolean) ((List) expected).get(tt));
            }
        }

        if(expectedElementsOnPage.size() > 0)
            Reporter.log("\n********** System is expecting " + expectedElementsOnPage.size()
                    + " WebElement validation(s) on the page **********\n", true);

        if(expectedByCallsOnPage.size() > 0)
            Reporter.log("\n********** System is expecting " + expectedElementsOnPage.size()
                    + " org.openqa.selenium.By validation(s) on the page **********\n", true);

        if(expectedAPICallsOnPage.size() > 0)
            Reporter.log("\n********** System is expecting " + expectedAPICallsOnPage.size()
                    + " boolean validation(s) on the page **********\n", true);

        totalExpected = expectedElementsOnPage.size() + expectedByCallsOnPage.size() + expectedAPICallsOnPage.size();
    }



    private String getStackTraceElements(String exceptionAsString) {

        //This will filter out everything but class paths and line numbers
        Pattern tracePattern = Pattern.compile("\\s*at\\s+([\\w\\.$_]+)\\.([\\w$_]+)(\\(.*java)?:(\\d+)\\)(\\n|\\r\\n)");

        Matcher traceMatcher = tracePattern.matcher(exceptionAsString);

        List<StackTraceElement> stackTrace = new ArrayList<>();

        while (traceMatcher.find()) {

            String className  = traceMatcher.group(1);
            String methodName = traceMatcher.group(2);
            String sourceFile = traceMatcher.group(3);

            int lineNum = Integer.parseInt(traceMatcher.group(4));

            stackTrace.add(new StackTraceElement(className, methodName, sourceFile, lineNum));
        }

        return stackTrace.toString();
    }


    private String exceptionToString(Exception e) {

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));

        return sw.toString();
    }


    public static Boolean looseValidation_Boolean(Boolean bool, String msg) throws CustomBooleanValueIsFalseException {

        Boolean booler;

        if(bool.equals(false)) {

            throw new CustomBooleanValueIsFalseException("######### BOOLEAN CONDITION IS FALSE " + msg);

        } else {

            booler = true;
        }

        return booler;
    }


}
