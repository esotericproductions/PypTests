package back.models.data_src.data_providers;

import au.com.bytecode.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jdwinters on 7/16/2017.
 */
public class CsvLoader
{
    /**
     * This will take a CSV where the first row is header values, and load it into an appropriate data_sources
     * structure (read: not Object[][]).
     * @param inFileLoc
     * @return
     * @throws MalformedURLException
     * @throws Exception
     */
    public static ArrayList<Map<String,String>> loadTestData(String inFileLoc) throws MalformedURLException, Exception
    {
        return (ArrayList<Map<String, String>>) loadAllContentFromTestDataFile((new HashMap<String, String>()).getClass(), inFileLoc);
    }

    @SuppressWarnings("unchecked")
    private static <T> List<Map<String, String>> loadAllContentFromTestDataFile(Class<T> clazz, String inFileLoc) throws MalformedURLException, Exception
    {
        List<T> returnMe = new ArrayList<T>();
        CSVReader csvReader = null;
        List<String[]> dataRows = null;

        try
        {
            // attempt to load file as resource. The file should be located in the src/main/resource folder.
            InputStream cis = CsvLoader.class.getClassLoader().getResourceAsStream(inFileLoc);

            if (cis == null)
                throw new java.io.FileNotFoundException(inFileLoc + " could not be found as a resource of CsvLoader.class");

            csvReader = new CSVReader(new InputStreamReader(cis));
            dataRows = csvReader.readAll();
        }
        finally
        {
            if (csvReader != null)
                csvReader.close();
        }

        // build header row.
        String[] headerRow = dataRows.remove(0);

        // for every row.
        for (String[] curRow : dataRows)
        {
            T curRowMap = clazz.newInstance();

            // for each header column
            for (int i=0; i<headerRow.length; i++)
            {
                // add the value with the header as a key to the row.
                ((Map<String,String>) curRowMap).put(headerRow[i].trim(), curRow[i].trim());
            }
            returnMe.add(curRowMap);
        }

        return (List<Map<String, String>>)returnMe;
    }
}