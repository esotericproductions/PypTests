package front.helpers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by jdwinters on 7/19/17.
 */
public class GenericHelper {

    public static BufferedImageWrapper getImageAndTypeFromInputStream(InputStream is) {

        String        format        = null;
        BufferedImage bufferedimage = null;

        try (ImageInputStream iis = ImageIO.createImageInputStream(is)) {

            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

            if (readers.hasNext()) {

                ImageReader reader = readers.next();
                format = reader.getFormatName();
                reader.setInput(iis);
                bufferedimage = reader.read(0);
            }

        } catch (IOException e) { e.printStackTrace(); }

        return new BufferedImageWrapper(format, bufferedimage);
    }

    public static void bringWindowToFront(WebDriver driver) {

        String currentWindowHandle = driver.getWindowHandle();

        ((JavascriptExecutor) driver).executeScript("alert('Test')");

        driver.switchTo().alert().accept();

        driver.switchTo().window(currentWindowHandle);
    }


    public static void robotToSelectFileInUploadDialog() throws AWTException, InterruptedException {

        Robot r = new Robot();

        Thread.sleep(1500);

        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(2500);

        r.keyPress(KeyEvent.VK_C);
        r.keyRelease(KeyEvent.VK_C);

        Thread.sleep(2500);

        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);
    }

    public static void robotToSelectAllFilesInUploadDialog() throws AWTException, InterruptedException {

        Robot r = new Robot();

        Thread.sleep(1500);

        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(2500);

        r.keyPress(KeyEvent.VK_META);

        r.keyPress(KeyEvent.VK_A);
        r.keyRelease(KeyEvent.VK_A);

        r.keyRelease(KeyEvent.VK_META);

        Thread.sleep(2500);

        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);
    }
}
