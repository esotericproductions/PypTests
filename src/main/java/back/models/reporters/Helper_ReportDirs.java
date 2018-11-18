package back.models.reporters;

import back.models.data_obj.global.GlobalData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by jwinters on 9/14/16.
 */
public class Helper_ReportDirs {

    public static void makeTestReportDirs(String s, GlobalData data) {

        String reportName = s + "_" + new Date().toString().replace(" ", "_").replace(":", "");

        data.setReportName(reportName);

        File reportDir = new File(data.getReportDir() + "/" + reportName);
        File imagesDir = new File(data.getReportDir() + "/" + reportName + "/screenshots");

        if(!reportDir.exists()) {

            try{
                reportDir.mkdir();
                imagesDir.mkdir();

            } catch (SecurityException se) { se.printStackTrace(); }
        }
    }


    public static void makeSuiteReportDirs(String dir) {

        File reportsDir = new File("./src/main/resources/reports/");

        if (!reportsDir.exists()) {

            try {

                reportsDir.mkdir();

            } catch (SecurityException s) {
                s.printStackTrace();
            }
        }

        //try {  FileUtils.cleanDirectory(new File("./src/main/resources/reports")); }  catch (IOException e) {}

        File suiteDir = new File(dir);

        if (!suiteDir.exists()) {

            try {

                suiteDir.mkdir();

            } catch (SecurityException s) {
                s.printStackTrace();
            }
        }
    }

    public static void writeImageToReports(BufferedImage fullImg,
                                           String meth,
                                           String dir,
                                           String name) throws IOException {

        try{

            File folder      = new File(dir);

            File[] listOfFiles = folder.listFiles();

            for(File file : listOfFiles) {

                if(file.getName().contains(meth)) {

                    ImageIO.write(fullImg, "png", new File(file.getPath() + "/screenshots/" + name + ".png"));
                }
            }

        } catch (Exception e) {e.printStackTrace();}
    }

    public static void writeImageToReports(BufferedImage fullImg,
                                                 String name,
                                                 GlobalData data) throws IOException {

        try{

            File folder      = new File(data.getReportDir());

            File[] listOfFiles = folder.listFiles();

            for(File file : listOfFiles) {

                if(file.getName().contains(data.getMethodUnderTest())) {

                    String filePath = file.getAbsolutePath() + "/screenshots/" + name + ".png";

                    ImageIO.write(fullImg, "png", new File(filePath));

                    data.setAbsolutePathFileOne(filePath);
                    data.setName_fileOne(name + ".png");
                }
            }

        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void writeTwoImagesToReports(BufferedImage fullImg1,
                                                     String name1,
                                                     BufferedImage fullImg2,
                                                     String name2,
                                                     GlobalData data) throws IOException {

        try {

            File folder      = new File(data.getReportDir());

            File[] listOfFiles = folder.listFiles();

            for(File file : listOfFiles) {

                if(file.getName().contains(data.getMethodUnderTest())) {

                    String filePath1 = file.getAbsolutePath() + "/screenshots/" + name1 + ".png";
                    String filePath2 = file.getAbsolutePath() + "/screenshots/" + name2 + ".png";

                    ImageIO.write(fullImg1, "png", new File(filePath1));
                    ImageIO.write(fullImg2, "png", new File(filePath2));

                    data.setAbsolutePathFileOne(filePath1);
                    data.setAbsolutePathFileTwo(filePath2);

                    data.setName_fileOne(name1);
                    data.setName_fileTwo(name2);
                }
            }

        } catch (Exception e) { e.printStackTrace(); }
    }





}
