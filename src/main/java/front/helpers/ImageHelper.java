package front.helpers;

import back.models.data_obj.global.GlobalData;
import back.models.exceptions.CustomBooleanValueIsFalseException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static back.models.reporters.Helper_ReportDirs.writeImageToReports;
import static back.models.reporters.Reports.reportImagesNotMatch;
import static front.helpers.Helper_RandomString.randomTwoDigits;

/**
 * Created by jdwinters on 7/19/17.
 */
public class ImageHelper {

    public static BufferedImage resize(BufferedImage inputImage, int scaledWidth, int scaledHeight) throws IOException {

        BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

        Graphics2D g2d = outputImage.createGraphics();

        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);

        g2d.dispose();

        return outputImage;
    }




    public static double pecentImagesAreDifferent(BufferedImage img1, BufferedImage img2) {

        int width1 = img1.getWidth(null);
        int width2 = img2.getWidth(null);
        int height1 = img1.getHeight(null);
        int height2 = img2.getHeight(null);

        if ((width1 != width2) || (height1 != height2)) {
            System.err.println("\n\n##### Error: Images dimensions mismatch #####\n\n");
            return 0;
        }

        long diff = 0;

        for (int y = 0; y < height1; y++) {

            for (int x = 0; x < width1; x++) {

                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >>  8) & 0xff;
                int b1 = (rgb1      ) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >>  8) & 0xff;
                int b2 = (rgb2      ) & 0xff;

                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }

        double n = width1 * height1 * 3;
        double p = diff / n / 255.0;

        return (p * 100.0);
    }



    public static double returnRGBDifference(BufferedImage buff,
                                             BufferedImage fullImg,
                                             WebElement thumb,
                                             GlobalData data) throws IOException {

        Point point = thumb.getLocation();

        int iWidth  = thumb.getSize().getWidth();
        int iHeight = thumb.getSize().getHeight();

        BufferedImage rowPic = fullImg.getSubimage(point.getX(), point.getY(), iWidth, iHeight);

        data.setImageForDiffingOne(rowPic);

        writeImageToReports(rowPic, data.getMethodUnderTest(), data.getReportDir(), "diff_image_" + randomTwoDigits());

        double diff = pecentImagesAreDifferent(resize(buff, iWidth, iHeight), rowPic);

        Reporter.log("\n\n***************************************************" +
                "\n******* Image Percentage Difference: " + diff                      +
                "\n***************************************************\n\n", true);

        return diff;
    }


    /**
     * Throws custom exception if images do not match within passed threshold value
     * @param data
     * @param driver
     * @param buff
     * @param threshold
     * @throws IOException
     * @throws CustomBooleanValueIsFalseException
     */
    public static void assertImagesMatch(GlobalData data,
                                         TakesScreenshot driver,
                                         BufferedImage buff,
                                         WebElement wel,
                                         int threshold) throws IOException, CustomBooleanValueIsFalseException {

        if(returnRGBDifference(buff, ImageIO.read(driver.getScreenshotAs(OutputType.FILE)), wel, data) > threshold) {

            throw new CustomBooleanValueIsFalseException(reportImagesNotMatch());
        }
    }
}
