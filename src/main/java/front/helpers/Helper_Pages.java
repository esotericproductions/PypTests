package front.helpers;

import back.controllers.enums.SeleniumActions;
import back.models.exceptions.CustomElementNotFoundException;
import org.openqa.selenium.*;
import org.testng.Reporter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by jdwinters on 7/19/17.
 */
public class Helper_Pages {

    public static String generateXPATH(WebElement childElement, String current) {

        try {
            String childTag = childElement.getTagName();

            if(childTag.equals("html")) {

                return "/html[1]" + current;
            }

            WebElement       parentElement    = childElement.findElement(By.xpath(".."));
            List<WebElement> childrenElements = parentElement.findElements(By.xpath("*"));

            int count = 0;

            for(int i=0; i < childrenElements.size(); i++) {

                WebElement childrenElement    = childrenElements.get(i);
                String     childrenElementTag = childrenElement.getTagName();

                if(childTag.equals(childrenElementTag)) {

                    count++;
                }

                if(childElement.equals(childrenElement)) {

                    return generateXPATH(parentElement, "/" + childTag + "[" + count + "]" + current);
                }
            }
        } catch (StaleElementReferenceException | NoSuchElementException e) { e.printStackTrace(); }


        return null;
    }

    public static Set<Field> findFields(Class<?> classs, Class<? extends Annotation> ann) {

        Set<Field> set = new HashSet<>();
        Class<?> c = classs;

        while (c != null) {

            for (Field field : c.getDeclaredFields()) {

                if (field.isAnnotationPresent(ann)) {
                    set.add(field);
                }
            }
            c = c.getSuperclass();
        }
        return set;
    }

    public static List<WebElement> getWebElementsOrThrowCustomNotVisibleException(WebElement w, String clazz) {

        if(w.isDisplayed()) {

            return w.findElements(By.xpath("*"));

        } else { throw new CustomElementNotFoundException(clazz);
        }
    }

    public static List<WebElement> getWebElementsOrReturnNull(WebElement w) {

        if(w.isDisplayed()) {

            return w.findElements(By.xpath("*"));

        } else { return null;
        }
    }

    public static WebElement getWebElementOrThrowCustomNotVisibleException(WebElement w, String meth) {

        if(w.isDisplayed()) {

            return w;

        } else { throw new CustomElementNotFoundException(meth);
        }
    }

    public static WebElement getWebElementOrThrowCustomNotVisibleException(boolean forWait, WebElement w, String meth) {

        if(!forWait) {

            if(w.isDisplayed()) {

                return w;

            } else { throw new CustomElementNotFoundException(meth); }

        } else return w;
    }

    public static List<WebElement> getWebElementOrThrowCustomNotVisibleException(boolean forWait, List<WebElement> w, String meth) {

        if(!forWait) {

            if(w.get(0).isDisplayed()) {

                return w;

            } else { throw new CustomElementNotFoundException(meth); }

        } else return w;
    }

    public static WebElement actOnWebElementOrThrowCustomNotVisibleException(WebElement w, SeleniumActions action,
                                                                             String reportMessage, String sendKeys, String clazz) {
        if (w.isDisplayed()) {

            Reporter.log("\n********** " + reportMessage + " **********\n", true);

            switch (action) {

                case SENDKEYS:

                    w.sendKeys(sendKeys);
                    break;
            }

        } else { throw new CustomElementNotFoundException(clazz); }

        return w;
    }

    public static WebElement clickOnWebElementOrThrowCustomNotVisibleException(WebElement w, String reportMessage, String name) {

        if (w.isDisplayed()) {

            Reporter.log("\n********** " + reportMessage + " **********\n", true);

            w.click();

        } else { throw new CustomElementNotFoundException(name); }

        return w;
    }


    public static WebElement actOnWebElementOrThrowCustomNotVisibleException(WebElement w, SeleniumActions action,
                                                                             String reportMessage, String sendKeys, String clazz, boolean addReturn) {
        if (w.isDisplayed()) {

            Reporter.log("\n********** " + reportMessage + " **********\n", true);

            switch (action) {

                case CLICK:

                    w.click();
                    break;

                case SENDKEYS:

                    w.sendKeys(sendKeys);
                    if(addReturn) w.sendKeys(Keys.RETURN);
                    break;
            }

        } else { throw new CustomElementNotFoundException(clazz); }

        return w;
    }

    public static String getStringFromWebElementOrThrowCustomNotVisibleException(WebElement w, SeleniumActions action,
                                                                                 String reportMessage, String clazz) {

        if (w.isDisplayed()) {

            Reporter.log("\n********* " + reportMessage + " *********\n", true);

            switch (action) {

                case GETTEXT:

                    return w.getText();

                default: return w.getAttribute("class");
            }

        } else { throw new CustomElementNotFoundException(clazz); }
    }


    public static By getByOrThrowCustomNotVisibleException(boolean forWait, WebElement w, String css) {

        if(w.isDisplayed()) {

            return (By) w.findElement(By.cssSelector(css));

        } else { throw new CustomElementNotFoundException(new Object() {}.getClass().getEnclosingMethod().getName()); }

    }




    public static String pollUntilNotEmpty(WebElement we, WebDriver driver, int timeOutSec) {

        long timeStart = System.nanoTime();

        for (long stop = timeStart + TimeUnit.SECONDS.toNanos(timeOutSec); stop > System.nanoTime(); ) {

            try {
                String returnString = we.getText();
                Reporter.log("returnString:: " + returnString);

                if (returnString.length() > 15) {

                    long elapsedTimeSec = TimeUnit.SECONDS.convert(System.nanoTime() - timeStart, TimeUnit.NANOSECONDS);
                    Reporter.log("Polling ended after ~" + elapsedTimeSec + " seconds.", true);
                    return returnString;
                }

            } catch (Exception ex) { ex.printStackTrace(); }

            long elapsedTimeSec = TimeUnit.SECONDS.convert(System.nanoTime() - timeStart, TimeUnit.NANOSECONDS);
            Reporter.log("Still polling: elapsed time: " + elapsedTimeSec, true);

            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        Reporter.log("Polling exceeded specified timeout(sec): " + timeOutSec, true);
        return "";
    }
}
