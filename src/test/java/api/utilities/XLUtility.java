package api.utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XLUtility {

    private String path;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public XLUtility(String path) {
        this.path = path;
    }

    private void openWorkbook() throws IOException {
        FileInputStream fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        fi.close();
    }

    private void saveWorkbook() throws IOException {
        try (FileOutputStream fo = new FileOutputStream(path)) {
            workbook.write(fo);
        }
    }

    public int getRowCount(String sheetName) throws IOException {
        openWorkbook();
        sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getPhysicalNumberOfRows();
        workbook.close();
        return rowCount;
    }

    public int getCellCount(String sheetName, int rownum) throws IOException {
        openWorkbook();
        sheet = workbook.getSheet(sheetName);
        XSSFRow row = sheet.getRow(rownum);
        int cellCount = row != null ? row.getPhysicalNumberOfCells() : 0;
        workbook.close();
        return cellCount;
    }

    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        openWorkbook();
        sheet = workbook.getSheet(sheetName);
        XSSFRow row = sheet.getRow(rownum);
        XSSFCell cell = row != null ? row.getCell(colnum) : null;

        DataFormatter formatter = new DataFormatter();
        String data = (cell != null) ? formatter.formatCellValue(cell) : "";
        workbook.close();
        return data;
    }

    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
        File xlFile = new File(path);
        if (!xlFile.exists()) {
            workbook = new XSSFWorkbook();
        } else {
            openWorkbook();
        }

        if (workbook.getSheetIndex(sheetName) == -1) {
            workbook.createSheet(sheetName);
        }
        sheet = workbook.getSheet(sheetName);

        XSSFRow row = sheet.getRow(rownum);
        if (row == null) {
            row = sheet.createRow(rownum);
        }

        XSSFCell cell = row.createCell(colnum);
        cell.setCellValue(data);

        saveWorkbook();
        workbook.close();
    }
}
