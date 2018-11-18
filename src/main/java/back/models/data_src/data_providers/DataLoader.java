package back.models.data_src.data_providers;

import au.com.bytecode.opencsv.CSVReader;
import org.testng.Reporter;
import org.testng.log4testng.Logger;

import java.io.*;
import java.util.*;

/**
 * Created by jdwinters on 8/27/16.
 */

public class DataLoader
{

    private Collection<Object[]> loadFromURL(String csvFileURL) throws IOException {

        Reporter.log("Loading data_sources from file: " + csvFileURL);

        CSVReader csvReader = null;
        FileReader fileReader = null;
        Collection<Object[]> result = null;

        try {

            fileReader = new FileReader(new File(csvFileURL));
            csvReader = new CSVReader(fileReader);
            result = parseCsvFile(csvReader);

        } catch(Exception e) {
            e.printStackTrace();
            return null;

        } finally {

            if (csvReader != null) {

                csvReader.close();
                fileReader.close();
            }

        }

        return result;
    }

    private Collection<Object[]> loadFromResource(String fileName) throws Exception {

        Reporter.log("Loading data_sources from: " + fileName);

        CSVReader csvReader = null;
        Collection<Object[]> result = null;

        try {

            InputStream cis = CsvLoader.class.getClassLoader().getResourceAsStream(fileName);

            if (cis == null)
                throw new FileNotFoundException(fileName + " could not be found as a resource of DataLoader.class");

            csvReader = new CSVReader(new InputStreamReader(cis));
            result = parseCsvFile(csvReader);

        } catch(Exception e) {

            throw new Exception(e);

        } finally {

            if (csvReader != null)
                csvReader.close();
        }

        return result;
    }


    protected final static Logger log = Logger.getLogger(DataLoader.class);

    public Collection<Object[]> loadDataFromCSV (String csvFileURL) throws Exception {

        Collection<Object[]> result = loadFromURL(csvFileURL);

        if (result == null) {

            String url = csvFileURL.toString();
            String fileName = url.substring( url.lastIndexOf('/')+1, url.length() );
            result = loadFromResource(fileName);
        }

        return result;
    }

    public Collection<Object[]> loadDataFromCSV(InputStream csvInputStream) throws Exception {

        log.debug("Loading data_sources from :: passed in InputStream");

        CSVReader csvReader = new CSVReader(new InputStreamReader(csvInputStream));

        Collection<Object[]> result = parseCsvFile(csvReader);

        // close any open resources
        csvReader.close();

        return result;
    }

    protected Collection<Object[]> parseCsvFile(CSVReader csvReader) throws Exception {

        List<String[]> dataRows;

        dataRows = csvReader.readAll();

        Reporter.log("\n# of rows == " + dataRows.size() + "\n");

        HashMap<String, String> testDataTable;

        Object[][] testDataArray = new Object[dataRows.size()-1][1];

        for (int i = 1; i < dataRows.size(); i++)	// skip header row
        {
            // get the current row
            Reporter.log("\nTesting data_sources # of column: " + (dataRows.get(i)).length + "\n");

            testDataTable = new HashMap<>();

            for (int j = 0; j < (dataRows.get(i)).length; j++)
            {
                String columnName = (dataRows.get(0))[j];
                String columnRowValue = (dataRows.get(i))[j];

                log.debug(columnName + "=" + columnRowValue);

                // Replace all of the ~ with ,
                columnRowValue = checkForReplacementCommasAndSet(columnRowValue);

                // check for {NULL} and set
                columnRowValue = checkForNullAndSet(columnRowValue);

                // check for {RANDOM_AN[24]} (random alpha-numeric of length 24) and set
                // check for {RANDOM_N[17]} (random number of length 17) and set
                // check for {RANDOM_A[8]} (random alphabet of length 8) and set
                columnRowValue = checkForRandomAndSet(columnRowValue);

                testDataTable.put(columnName.trim(), columnRowValue);
            }
            testDataArray[i - 1][0] = new TestDataObject(testDataTable);
        }

        return Arrays.asList(testDataArray);
    }

    /**
     * <p>This method will return the passed in string with all ~ replaced with a ,</p>
     *
     * @param columnRowValue The String to replace the ~ in
     * @return A String with , instead of ~
     */
    protected String checkForReplacementCommasAndSet(String columnRowValue)
    {
        if (columnRowValue == null)
            return null;
        return columnRowValue.replaceAll("~", ",");
    }


    protected String checkForRandomAndSet(String columnRowValue) throws NumberFormatException
    {
        Random rand = new Random();

        // if starts with "{RANDOM" and ends with "}"
        if (columnRowValue != null && columnRowValue.startsWith("{RANDOM") && (columnRowValue.substring(columnRowValue.length() - 1, columnRowValue.length()).equals("}")))
        {
            // if length not defined for random then set to random length
            int length = rand.nextInt();

            // check for length modifier
            if (columnRowValue.matches(".*(\\[)[\\d]+(\\]})"))
            {
                // found a length modifier so parse out the length
                String lengthStr = columnRowValue.substring(columnRowValue.indexOf('[')+1, columnRowValue.length() - 2);
                length = Integer.parseInt(lengthStr);
            }

            // check for alpha-numeric
            if (columnRowValue.startsWith("{RANDOM_AN"))
            {
                //				return RandomHelper.getRandomAlphaNumericStr(length);
            } // check for alphabet only
            else if (columnRowValue.startsWith("{RANDOM_A"))
            {
                //				return RandomHelper.getRandomAlphaStr(length);
            } // check for numeric only
            else if (columnRowValue.startsWith("{RANDOM_N"))
            {
                //				return RandomHelper.getRandomNumericStr(length);
            }
        }

        return columnRowValue;
    }

    /**
     * @param columnRowValue the String to check for {NULL}
     * @return null if input String is {NULL} otherwise return the input String
     * exactly as passed in
     */
    protected String checkForNullAndSet(String columnRowValue)
    {
        if (columnRowValue != null && columnRowValue.equals("{NULL}"))
        {
            return null;
        }

        return columnRowValue;
    }


}
