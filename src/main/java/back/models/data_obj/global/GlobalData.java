package back.models.data_obj.global;

import org.json.JSONObject;

import java.awt.image.BufferedImage;

/**
 * Created by jdwinters on 7/15/17.
 */
public class GlobalData {

    private String methodUnderTest;
    private String reportName;
    private String reportDir;
    private String testId;
    private String id;
    private String env;
    private String absolutePathOne;
    private String absolutePathTwo;
    private String fileOneName;
    private String fileTwoName;

    private int errNum;

    private JSONObject jsonReport;

    private BufferedImage imageForDiffingOne;
    private BufferedImage imageForDiffingTwo;

    public String getMethodUnderTest() { return methodUnderTest; }

    public void setMethodUnderTest(String methodUnderTest) { this.methodUnderTest = methodUnderTest; }

    public String getReportName() { return reportName; }

    public void setReportName(String reportName) { this.reportName = reportName; }

    public String getReportDir() { return reportDir; }

    public void setReportDir(String reportDir) { this.reportDir = reportDir; }

    public String getTestId() { return testId; }

    public void setTestId(String testId) { this.testId = testId; }

    public JSONObject getJsonReport() { return jsonReport; }

    public void setJsonReport(JSONObject jsonReport) { this.jsonReport = jsonReport; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public int getErrNum() { return ++errNum; }

    public String getEnv() { return env; }

    public void setEnv(String env) { this.env = env; }



    public String getAbsolutePathFileOne() { return absolutePathOne; }

    public void setAbsolutePathFileOne(String absolutePath) { this.absolutePathOne = absolutePath; }


    public String getAbsolutePathFileTwo() { return absolutePathTwo; }

    public void setAbsolutePathFileTwo(String absolutePath) { this.absolutePathTwo = absolutePath; }



    public String getName_FileOne() { return fileOneName; }

    public void setName_fileOne(String name) { this.fileOneName = name; }

    public String getName_FileTwo() { return fileTwoName; }

    public void setName_fileTwo(String name2) { this.fileTwoName = name2; }



    public BufferedImage getImageForDiffingOne() { return imageForDiffingOne; }

    public void setImageForDiffingOne(BufferedImage buff) { this.imageForDiffingOne = buff; }



    public BufferedImage getImageForDiffingTwo() { return imageForDiffingOne; }

    public void setImageForDiffingTwo(BufferedImage buff) { this.imageForDiffingOne = buff; }

}
