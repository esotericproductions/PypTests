package back.models.data_src.data_providers;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by jdwinters on 8/27/16.
 */
@SuppressWarnings("serial")
public class UITestInfoObject implements Serializable
{
    private String testName;
    private String testID;
    private String description;
    private String expected;

    public String getTestName() { return testName; }

    public void setTestName(String testName) { this.testName = testName; }

    public String getTestID() { return testID; }

    public void setTestID(String testID) { this.testID = testID; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getExpectedResult() { return expected; }

    public void setExpectedResult(String expectedResult) { this.expected = expectedResult; }

    public UITestInfoObject(){}

    public UITestInfoObject(String testName, String testId, String testDesc, String testExpect) {

        setTestName(testName);
        setTestID(testId);
        setDescription(testDesc);
        setExpectedResult(testExpect);
    }

    public UITestInfoObject(HashMap<String, String> table) {

        setTestName(table.get(UITestKV.TestInfo.name));
        setTestID(table.get(UITestKV.TestInfo.id));
        setDescription(table.get(UITestKV.TestInfo.description));
        setExpectedResult(table.get(UITestKV.TestInfo.expected));
    }
}
