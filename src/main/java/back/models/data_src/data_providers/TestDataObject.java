package back.models.data_src.data_providers;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by jdwinters on 8/27/16.
 */

public class TestDataObject implements Serializable
{
    private static final long serialVersionUID = 1L;

    private UITestInfoObject testInfo;
    private HashMap<String, String> testCaseData;
    private String testName = null;

    public TestDataObject(HashMap<String, String> map) throws Exception {

        setTestInfo(new UITestInfoObject(map));
        setTestCaseData(map);
    }

    public UITestInfoObject getTestInfo() {

        return testInfo;
    }

    public void setTestInfo(UITestInfoObject testInfo)
    {
        this.testInfo = testInfo;
    }

    public HashMap<String, String> getTestCaseData()
    {
        return testCaseData;
    }

    public void setTestCaseData(HashMap<String, String> testCaseData)
    {
        this.testCaseData = testCaseData;
    }

    public String getTestName() {
        return testName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {

        return this.getTestCaseData().get("test_id");

    }
}
