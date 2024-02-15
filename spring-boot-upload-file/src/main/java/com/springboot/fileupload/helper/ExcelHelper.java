package com.springboot.fileupload.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.fileupload.model.Invoice;

public class ExcelHelper {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Name", "Amount", "Number", "Received Date"};
	static String SHEET = "Invoice";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<Invoice> excelToInvoice(InputStream is) {
		//Workbook workbook = new XSSFWorkbook(is); // // Sheet sheet = workbook.getSheet(SHEET); to Workbook workbook = WorkbookFactory.create(is); Sheet sheet = workbook.getSheetAt(0);

		try {
			//Workbook workbook = new XSSFWorkbook(is);
			Workbook workbook = WorkbookFactory.create(is);
			//Sheet sheet = workbook.getSheet(SHEET);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();

			List<Invoice> invoiceList = new ArrayList<Invoice>();

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				Invoice invoice = new Invoice();

				int cellIdx = 0;
				 while (cellsInRow.hasNext()) {
		                Cell currentCell = cellsInRow.next();

		                switch (cellIdx) {
		                    case 0:
		                        if (currentCell.getCellType() == CellType.NUMERIC) {
		                            invoice.setId((long) currentCell.getNumericCellValue());
		                        }
		                        break;

		                    case 1:
		                        if (currentCell.getCellType() == CellType.STRING) {
		                            invoice.setName(currentCell.getStringCellValue());
		                        }
		                        break;

		                    case 2:
		                        if (currentCell.getCellType() == CellType.NUMERIC) {
		                            invoice.setAmount(currentCell.getNumericCellValue());
		                        }
		                        break;

		                    case 3:
		                        if (currentCell.getCellType() == CellType.NUMERIC) {
		                            invoice.setNumber((long) currentCell.getNumericCellValue());
		                        }
		                        break;

		                    case 4:
		                        if (currentCell.getCellType() == CellType.NUMERIC) {
		                            invoice.setReceivedDate(currentCell.getDateCellValue());
		                        }
		                        break;

		                    default:
		                        break;
		                }
		                cellIdx++;
		            }
		            invoiceList.add(invoice);
				/*
				 * while (cellsInRow.hasNext()) { Cell currentCell = cellsInRow.next();
				 * 
				 * switch (cellIdx) { case 0: invoice.setId((long)
				 * currentCell.getNumericCellValue()); break;
				 * 
				 * case 1: invoice.setName(currentCell.getStringCellValue()); break;
				 * 
				 * case 2: invoice.setAmount(currentCell.getNumericCellValue()); break;
				 * 
				 * case 3: invoice.setNumber((long) currentCell.getNumericCellValue()); break;
				 * 
				 * case 4: invoice.setReceivedDate(currentCell.getDateCellValue()); break;
				 * 
				 * default: break; } cellIdx++; } invoiceList.add(invoice);
				 */
			}
			workbook.close();
			return invoiceList;
		} catch (IOException e) {
		      throw new RuntimeException("failed to parse Excel file: " + e.getMessage());
	    }
	}

	
	public static ByteArrayInputStream invoiceToExcel(List<Invoice> invoices) throws IOException {

	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);

	      Font headerFont = workbook.createFont();
          headerFont.setBold(true);
	   // Create a CellStyle with the bold font
          CellStyle headerCellStyle = workbook.createCellStyle();
          headerCellStyle.setFont(headerFont);
	      // Header
	      Row headerRow = sheet.createRow(0);
	      

	      for (int col = 0; col < HEADERs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERs[col]);
	        cell.setCellStyle(headerCellStyle);
	      }

	      int rowIdx = 1;
	      for (Invoice invoice : invoices) {
	        Row row = sheet.createRow(rowIdx++);

	        row.createCell(0).setCellValue(invoice.getId());
	        row.createCell(1).setCellValue(invoice.getName());
	        row.createCell(2).setCellValue(invoice.getAmount());
	        row.createCell(3).setCellValue(invoice.getNumber().longValue());
	        
	        // Format the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String receivedDateStr = dateFormat.format(invoice.getReceivedDate());
            row.createCell(4).setCellValue(receivedDateStr);
	      }
	        workbook.write(out);
	        return new ByteArrayInputStream(out.toByteArray());
	      } catch (IOException e) {
	        throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
	      }
	    }
}