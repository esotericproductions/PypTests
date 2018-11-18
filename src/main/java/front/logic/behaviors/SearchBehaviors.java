package front.logic.behaviors;

import back.models.data_obj.applications.PApplicationData;
import back.models.data_obj.global.GlobalData;
import front.pages.Pages_InitElements;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.util.List;

import static back.controllers.waits.Wait.waitVisible;

public class SearchBehaviors {

    private List<MobileElement> cultList;
    private PApplicationData appData = new PApplicationData();

    public void simpleQuery(Pages_InitElements pages, WebDriverWait wait,
                                           AndroidDriver driver, GlobalData data, String query) throws Exception {

        Reporter.log("\n********** Searching for: " + query + " **********\n", true);

        pages.getCulture().sendQuery(query);
        driver.pressKeyCode(AndroidKeyCode.ENTER);

        cultList = pages.getCulture().getCultureRecyclerElems();
    }


    public void tapImage_bvr(Pages_InitElements pages, WebDriverWait wait) throws Exception {
        int tapIndex = 1;
        Reporter.log("\n********** Tapping image at index " + tapIndex + " **********\n", true);
        MobileElement ae = cultList.get(tapIndex);
        appData.setAe(ae);
        pages.getCulture().tapCultureElem(ae);
        waitVisible(wait, pages.getCulture().getFuzzGo());
    }


    public void tapOpenPopup(Pages_InitElements pages, WebDriverWait wait) throws Exception {
        pages.getCulture().tapVisibleFuzzGo();
        waitVisible(wait, pages.getCulture().getProfileImage());
        waitVisible(wait, pages.getCulture().getBrand());
    }

    public void tapBrandingToShareMenu(Pages_InitElements pages, WebDriverWait wait) throws Exception {
        pages.getCulture().tapBranding();
        waitVisible(wait, pages.getCulture().getShare());
    }

    public void swipeClosePopup(WebDriverWait wait, AndroidDriver driver) throws Exception {
        Dimension size = driver.manage().window().getSize();
        new TouchAction(driver)
                .press((int)(size.width * .9), size.height / 2)
                .waitAction(250)
                .moveTo((int)(size.width * .25), size.height / 2)
                .release()
                .perform();
        waitVisible(wait, appData.getAe());
    }

    public void downScroll(Pages_InitElements pages, WebDriverWait wait,
                           AndroidDriver driver) throws Exception {

        Reporter.log("\n********** Scrolling Down **********\n", true);
        waitVisible(wait, pages.getCulture().getCultureRecycler());
        (new TouchAction(driver))
                .press(cultList.get(9))
                .moveTo(cultList.get(1))
                .release().perform();
        waitVisible(wait, pages.getCulture().getCultureRecycler());
        pages.getPam().tapHider();
    }


    public void upScroll(Pages_InitElements pages, WebDriverWait wait,
                           AndroidDriver driver) throws Exception {

        Reporter.log("\n********** Scrolling Up **********\n", true);
        waitVisible(wait, pages.getCulture().getCultureRecycler());
        (new TouchAction(driver))
                .press(cultList.get(1))
                .moveTo(cultList.get(9))
                .release().perform();
        waitVisible(wait, pages.getCulture().getCultureRecycler());
        pages.getPam().tapHider();
    }
}
