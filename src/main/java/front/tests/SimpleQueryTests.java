package front.tests;

import front.logic.launchers.Launcher_SearchTests;
import org.testng.annotations.Test;

public class SimpleQueryTests extends Launcher_SearchTests {

    @Test(dataProvider = "Search_TestDataProvider")
    public void simpleSearch(Object data) {
        startPypTest(data)
            .searchSingleWord()
            .tapImage()
            .openPopup()
            .openShareMenu()
            .closePopup()
                .stopPypTest();
    }

    @Test(dataProvider = "Search_TestDataProvider")
    public void searchScrollPop(Object data) {
        startPypTest(data)
            .searchSingleWord()
            .swipeDown()
            .tapImage()
            .openPopup()
            .openShareMenu()
            .closePopup()
                .stopPypTest();
    }

    @Test(dataProvider = "Search_TestDataProvider")
    public void searchMultiScrollPop(Object data) {
        startPypTest(data)
                .searchSingleWord()
                .swipeDown()
                .swipeUp()
                .tapImage()
                .openPopup()
                .openShareMenu()
                .closePopup()
                .stopPypTest();
    }
}
