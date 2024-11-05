package api.utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProviders {

    @DataProvider(name = "Data")
    public String[][] getAllData() throws IOException {
        String path = System.getProperty("user.dir") + "//testData//userdata.xlsx";
        XLUtility xl = new XLUtility(path);

        int rownum = xl.getRowCount("Sheet1");
        int colcount = xl.getCellCount("Sheet1", 1);

        List<String[]> validRows = new ArrayList<>(); // Use a list to store valid rows

        for (int i = 1; i <= rownum; i++) {
            String[] rowData = new String[colcount];
            boolean isEmptyRow = true; // Track if the row is empty

            for (int j = 0; j < colcount; j++) {
                String cellData = xl.getCellData("Sheet1", i, j);
                rowData[j] = cellData;
                if (cellData != null && !cellData.trim().isEmpty()) {
                    isEmptyRow = false; // Found a non-empty cell
                }
            }

            // Only add non-empty rows to the list
            if (!isEmptyRow) {
                validRows.add(rowData);
            }
        }

        // Convert the list to a 2D array
        String[][] apidata = new String[validRows.size()][colcount];
        for (int i = 0; i < validRows.size(); i++) {
            apidata[i] = validRows.get(i);
        }

        return apidata;
    }

    @DataProvider(name = "UserNames")
    public String[] getUserNames() throws IOException {
        String path = System.getProperty("user.dir") + "//testData//userdata.xlsx";
        XLUtility xl = new XLUtility(path);

        int rownum = xl.getRowCount("Sheet1");

        List<String> userNames = new ArrayList<>();
        for (int i = 1; i <= rownum; i++) {
            String userName = xl.getCellData("Sheet1", i, 1);
            if (userName != null && !userName.trim().isEmpty()) { // Check for non-empty usernames
                userNames.add(userName);
            }
        }

        // Convert the list to a string array
        return userNames.toArray(new String[0]);
    }
}
