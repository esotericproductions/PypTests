package back.models.reporters;

import back.models.data_obj.global.GlobalData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static back.models.reporters.Helper_ReportDirs.writeImageToReports;
import static back.models.reporters.Reports.reportIssueToJSON;
import static front.helpers.Helper_RandomString.prettyFormatter;
import static front.helpers.Helper_RandomString.randomTwoDigits;

/**
 * Created by jwinters on 9/14/16.
 */
public class JSONReporter {

    public static void putIssueInJson(WebDriver driver, GlobalData data,
                                     String errorMessage, String stackTrace) {

        try {

            reportIssueToJSON(errorMessage, data.getTestId());
            writeImageToReports(ImageIO.read(((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE)), data.getMethodUnderTest(),
                        data.getReportDir(), "issue_screenshot_" + randomTwoDigits());

            String     testId = data.getTestId();

            JSONObject obj = data.getJsonReport();
            JSONArray array = new JSONArray();
            JSONArray issues = data.getJsonReport().getJSONArray("failures");

            List<JSONArray> stack_message = createArraysForReport(errorMessage, stackTrace);

                array.put("tests: "    + testId);
                array.put("method: "   + data.getMethodUnderTest());

                array.put(new JSONObject().put("message: ",    stack_message.get(0)));
                array.put(new JSONObject().put("stackTrace: ", stack_message.get(1)));

            obj.put("issues_total", data.getErrNum());

            issues.put(new JSONObject().put(testId, array));

            data.setJsonReport(obj);

            FileWriter file = new FileWriter(new File(data.getReportDir() + "/"
                    + data.getReportName() + "/jsonReport.json"));

            file.write(prettyFormatter(data.getJsonReport().toString()));
            file.flush();
            file.close();

        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void putSimpleIssueInJson(WebDriver driver, GlobalData data, String errorMessage) {

        try {

            reportIssueToJSON(errorMessage, data.getTestId());

            writeImageToReports(ImageIO.read(((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE)), data.getMethodUnderTest(),
                            data.getReportDir(), "issue_screenshot_" + randomTwoDigits());

            String     testId = data.getTestId();

            JSONObject obj    = data.getJsonReport();
            JSONArray  array  = new JSONArray();
            JSONArray  issues = data.getJsonReport().getJSONArray("failures");

            List<JSONArray> stack_message = createArraysForReport(errorMessage, "");

            array.put("tests: "    + testId);
            array.put("method: "   + data.getMethodUnderTest());
            array.put(new JSONObject().put("message: ",    stack_message.get(0)));

            obj.put("issues_total", data.getErrNum());

            issues.put(new JSONObject().put(testId, array));

            data.setJsonReport(obj);

        } catch (Exception e) { e.printStackTrace(); }
    }

    private static List<JSONArray> createArraysForReport(String errorMessage, String stackTrace) {

        List<JSONArray> jray = new ArrayList<>();

        JSONArray  trray  = new JSONArray();
        JSONArray  mrray  = new JSONArray();

        String newStack   = stackTrace.replace("[", "").replace("]","").replace(",", "").replace("\n", "");
        String newMessage = errorMessage.replace(",", "").replace("\n", "");

        for(int i = 0; i < newStack.split(" ").length; i ++) {

            trray.put(stackTrace.split(", ")[i]);
        }

        for(int j = 0; j < newMessage.split(", ").length; j++) {

            mrray.put(newMessage.split(", ")[j]);
        }

        jray.add(0, mrray);
        jray.add(1, trray);

        return jray;
    }
}
