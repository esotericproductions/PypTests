package back.controllers.waits;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

/**
 * Created by jdwinters on 7/15/17.
 */
public class Wait {


    public static void waitVisible(WebDriverWait wait, WebElement wel) throws Exception {

        Reporter.log("\n########## Waiting for visibilityOf(): " + wel.toString() + "\n", true);

        wait.until(ExpectedConditions.elementToBeClickable(wel));

    }

    public static void waitInvisible(WebDriverWait wait, WebElement wel) throws Exception {

        Reporter.log("\n########## Waiting for invisibilityOf(): " + wel.toString() + "\n", true);

        wait.until(ExpectedConditions.invisibilityOfAllElements(wel.findElements(By.xpath("*"))));

        Thread.sleep(5000);
    }

    public static void waitClickable(WebDriverWait wait, WebElement wel) throws Exception {

        Reporter.log("\n########## Waiting for elementToBeClickable(): " + wel.toString() + "\n", true);

        wait.until(ExpectedConditions.elementToBeClickable(wel));

    }
}
