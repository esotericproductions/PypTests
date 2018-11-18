package back.models.data_src.data_sources;

import back.models.data_src.data_providers.DataLoader;

import java.util.Collection;


public class FetchDataProviders {


    private final String dataDrivenPTest_qa = "src/main/resources/csv/DataDrivenPTest_qa.csv";

    public Collection<Object[]> getTestData(String data) {

        DataLoader tdl = new DataLoader();
        Collection<Object[]> csvData = null;

            try {

                csvData = tdl.loadDataFromCSV(getDataProvider(data));

            } catch (Exception e) { e.printStackTrace(); }

        return csvData;
    }

    public String  getDataProvider(String csv) {

        switch(csv) {

            case "DataDrivenPTest_qa": return dataDrivenPTest_qa;
            default: return null;
        }
    }
}
