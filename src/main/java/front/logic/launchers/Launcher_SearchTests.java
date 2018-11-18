package front.logic.launchers;

import back.controllers.driver.MainTLauncher;
import back.models.data_src.data_sources.FetchDataProviders;
import front.interfaces.AllTests;
import front.interfaces.SearchTests;
import front.logic.behaviors.SearchBehaviors;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.DataProvider;

public class Launcher_SearchTests extends MainTLauncher implements AllTests, SearchTests {

    SearchBehaviors behaviors = new SearchBehaviors();

    @DataProvider(name = "Search_TestDataProvider")
    public Object[][] readDataFromFile() {
        return (Object[][]) new FetchDataProviders().getTestData(dataProvider).toArray();
    }

    @Override
    public Launcher_SearchTests startPypTest(Object data) {
        try { setup(data); } catch (Exception e) { e.printStackTrace(); }
        return this;
    }

    @Override
    public Launcher_SearchTests searchSingleWord() {
                try {
                    behaviors.simpleQuery(pages, wait,
                            (AndroidDriver) driver, dataGlobal, queries);
            } catch (Exception e) {
            validatorMngr.evaluateSystemStateAsExpected(driver, e);
        }
        return this;
    }

    @Override
    public Launcher_SearchTests tapImage() {
                try {
                    behaviors.tapImage_bvr(pages, wait);
            } catch (Exception e) {
            validatorMngr.evaluateSystemStateAsExpected(driver, e);
        }
        return this;
    }

    @Override
    public Launcher_SearchTests openPopup() {
                try {
                    behaviors.tapOpenPopup(pages, wait);
            } catch (Exception e) {
            validatorMngr.evaluateSystemStateAsExpected(driver, e);
        }
        return this;
    }

    @Override
    public Launcher_SearchTests closePopup() {
                try {
                    behaviors.swipeClosePopup(wait, (AndroidDriver) driver);
            } catch (Exception e) {
            validatorMngr.evaluateSystemStateAsExpected(driver, e);
        }
        return this;
    }
    @Override
    public Launcher_SearchTests openShareMenu() {
        try {
            behaviors.tapBrandingToShareMenu(pages, wait);
        } catch (Exception e) {
            validatorMngr.evaluateSystemStateAsExpected(driver, e);
        }
        return this;
    }

    @Override
    public Launcher_SearchTests swipeUp() {
                try {
                    behaviors.upScroll(pages, wait, (AndroidDriver) driver);
            } catch (Exception e) {
            validatorMngr.evaluateSystemStateAsExpected(driver, e);
        }
        return this;
    }

    @Override
    public Launcher_SearchTests swipeDown() {
                try {
                    behaviors.downScroll(pages, wait, (AndroidDriver) driver);
            } catch (Exception e) {
            validatorMngr.evaluateSystemStateAsExpected(driver, e);
        }
        return this;
    }

    @Override
    public Launcher_SearchTests stopPypTest() {
        return this;
    }
}
