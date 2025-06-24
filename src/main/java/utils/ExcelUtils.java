package utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.File;
import java.util.*;

public class ExcelUtils {
    public static List<Map<String, String>> getData(String filePath, String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            Workbook wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheet(sheetName);
            Iterator<Row> rows = sheet.iterator();
            Row header = rows.next();
            List<String> headers = new ArrayList<>();
            for (Cell cell : header) headers.add(cell.getStringCellValue());

            while (rows.hasNext()) {
                Row row = rows.next();
                Map<String, String> rowData = new HashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    rowData.put(headers.get(i), cell.toString());
                }
                data.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}