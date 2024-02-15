package com.springboot.fileupload.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.fileupload.helper.ExcelHelper;
import com.springboot.fileupload.message.ResponseMessage;
import com.springboot.fileupload.model.Invoice;
import com.springboot.fileupload.service.impl.ExcelService;

//@CrossOrigin("http://localhost:5001")
@Controller
@RequestMapping("/api/v1/excel")
public class ExcelFileController {

	@Autowired
	ExcelService fileService;

	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("excelFile") MultipartFile excelFile) {
		String message = "";
		//System.out.println("Excel File ??????? : " + excelFile);
		if (ExcelHelper.hasExcelFormat(excelFile)) {
			try {
				fileService.saveInvoice(excelFile);

				message = "Uploaded the file successfully: " + excelFile.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file: " + excelFile.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload an excel file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	@GetMapping("/invoices")
	public ResponseEntity<List<Invoice>> getAllTutorials() {
		try {
			List<Invoice> tutorials = fileService.getAllInvoices();

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/download")
	public ResponseEntity<Resource> getFile() throws IOException {
		String filename = "invoice.xlsx";
		InputStreamResource file = new InputStreamResource(fileService.load());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
}
