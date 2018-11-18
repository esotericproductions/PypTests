package back.models.reporters;

import front.helpers.Helper_RandomString;
import org.testng.Reporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by jwinters on 9/14/16.
 */
public class ZipDirectory {

    private List<String> fileList;
    private static final String REPORTS_FOLDER = "./src/main/resources/reports";

    public ZipDirectory()  { fileList = new ArrayList<>(); }

    public static void zipMyReports()  {

        ZipDirectory appZip = new ZipDirectory();

        appZip.generateFileList(new File(REPORTS_FOLDER));
        appZip.zipIt(REPORTS_FOLDER + "/Tests_Output_" + new Helper_RandomString(5).nextString() + ".zip");
    }

    public void zipIt(String zipFile)  {

        byte[] buffer = new byte[1024];

        String source;
        FileOutputStream fos;
        ZipOutputStream zos = null;

        try  {

            try  {

                source = REPORTS_FOLDER.substring(REPORTS_FOLDER.lastIndexOf("\\") + 1, REPORTS_FOLDER.length());

            }  catch (Exception e)  { source = REPORTS_FOLDER; }

            fos = new FileOutputStream(zipFile);

            zos = new ZipOutputStream(fos);

            Reporter.log("\nZipping file : " + zipFile + "..........\n", true);

            FileInputStream in = null;

            for (String file : this.fileList)  {

                Reporter.log("Contains report: " + file, true);

                ZipEntry ze = new ZipEntry(source + File.separator + file);

                zos.putNextEntry(ze);

                try  {

                    in = new FileInputStream(REPORTS_FOLDER + File.separator + file);

                    int len;

                    while ((len = in.read(buffer)) > 0)  { zos.write(buffer, 0, len); }

                }  finally  { in.close(); }
            }

            zos.closeEntry();

            Reporter.log("\n\n++++++++ Folder successfully compressed; Have a nice day ++++++++\n\n", true);

        }
        catch (IOException ex)  {

            ex.printStackTrace();

        }  finally  {

            try  {

                zos.close();

            }  catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void generateFileList(File node) {
        // add file only
        if (node.isFile()) {

            fileList.add(generateZipEntry(node.toString()));
        }

        if (node.isDirectory()) {

            String[] subNote = node.list();

            for (String filename : subNote) {

                generateFileList(new File(node, filename));
            }
        }
    }

    private String generateZipEntry(String file) { return file.substring(REPORTS_FOLDER.length() + 1, file.length()); }
}
