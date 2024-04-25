package main_package;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class ExcelReader {

	public String[][] readExcel(String filePath) throws IOException {
	    FileInputStream fis = new FileInputStream(filePath);
	    XSSFWorkbook workbook = new XSSFWorkbook(fis);
	    Sheet sheet = workbook.getSheetAt(0);

	    String[][] data = new String[sheet.getLastRowNum() + 1][]; // Initialize with number of rows

	    String[] parts1 = new String[2], parts2 = new String[3], parts3 = new String[5];
	    boolean foundMarker = false, set = true;
	    int rowIndex = 0;

	    for (Row row : sheet) {
	        for (Cell cell : row) {
	            if (cell.getCellType() == CellType.STRING) {
	                String cellValue = cell.getStringCellValue();
	                if (cellValue.equalsIgnoreCase("COURSE/PAPER TITLE")) {
	                    foundMarker = true;
	                    break;
	                }
	            }
	        }

	        if (foundMarker) {
	            for (int i = row.getRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
	                Row nextRow = sheet.getRow(i);
	                if (set || parts1[0] == null) {
	                    Row full = sheet.getRow(i - 2);
	                    String full_name = full.getCell(2).getStringCellValue();
	                    parts1 = full_name.split("\\(");
	                    parts2 = parts1[1].split("\\)");
	                    parts3 = parts2[1].split("\\s+");
	                    set = false;
	                }

	                if (nextRow != null) {
	                    String paper_title = nextRow.getCell(1).getStringCellValue();
	                    if (paper_title == null || paper_title.isEmpty()) {
	                        break;
	                    }
	                    String paper_code = nextRow.getCell(2).getStringCellValue();
	                    String coll = nextRow.getCell(5).getStringCellValue();
	                    String cate = nextRow.getCell(6).getStringCellValue();
	                    String number = "";

	                    String paper_type = nextRow.getCell(3).getStringCellValue();
	                    String full_marks = "";
	                    String marks_obt = "";

	                    String stream = parts1[0], sem = parts3[2], year = parts3[3], subject = parts2[0];

	                    Cell cell_number = nextRow.getCell(7);
	                    switch (cell_number.getCellType()) {
	                        case STRING:
	                            number = nextRow.getCell(7).getStringCellValue();
	                            break;
	                        case NUMERIC:
	                            double number_d = nextRow.getCell(7).getNumericCellValue();
	                            DecimalFormat decimalFormat = new DecimalFormat("#");
	                            number = decimalFormat.format(number_d);
	                            break;
	                        default:
	                            System.out.println("Cell does not contain a string or numeric value");
	                            break;
	                    }

	                    Cell cell_fm = nextRow.getCell(8);
	                    switch (cell_fm.getCellType()) {
	                        case STRING:
	                            full_marks = nextRow.getCell(8).getStringCellValue();
	                            break;
	                        case NUMERIC:
	                            double full_marks_d = nextRow.getCell(8).getNumericCellValue();
	                            DecimalFormat decimalFormat = new DecimalFormat("#");
	                            full_marks = decimalFormat.format(full_marks_d);
	                            break;
	                        default:
	                            System.out.println("Cell does not contain a string or numeric value");
	                            break;
	                    }

	                    Cell cell_mo = nextRow.getCell(9);
	                    switch (cell_mo.getCellType()) {
	                        case STRING:
	                            marks_obt = nextRow.getCell(9).getStringCellValue();
	                            break;
	                        case NUMERIC:
	                            double marks_obt_d = nextRow.getCell(9).getNumericCellValue();
	                            DecimalFormat decimalFormat = new DecimalFormat("#");
	                            marks_obt = decimalFormat.format(marks_obt_d);
	                            break;
	                        default:
	                            System.out.println("Cell does not contain a string or numeric value");
	                            break;
	                    }

	                    // Populate data array
	                    data[rowIndex] = new String[] {
	                        paper_code, paper_title, coll, cate, number, paper_type,
	                        full_marks, marks_obt, stream, sem, year, subject
	                    };
	                    rowIndex++;

	                    if (rowIndex >= data.length) {
	                        // Resize the array if needed
	                        String[][] newData = new String[data.length * 2][];
	                        System.arraycopy(data, 0, newData, 0, data.length);
	                        data = newData;
	                    }
	                }
	            }
	        }
	    }

	    workbook.close();
	    // Trim the array to remove excess null entries
	    return Arrays.copyOf(data, rowIndex);
	}

	private String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return "";
		}
		if (cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == CellType.BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == CellType.BLANK) {
			return "";
		} else {
			return "";
		}
	}
}