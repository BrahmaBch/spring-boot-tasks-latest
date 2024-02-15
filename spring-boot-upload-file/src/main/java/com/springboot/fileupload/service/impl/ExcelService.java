package com.springboot.fileupload.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.fileupload.helper.ExcelHelper;
import com.springboot.fileupload.model.Invoice;
import com.springboot.fileupload.repository.InvoiceRepository;

@Service
public class ExcelService {

	@Autowired
	InvoiceRepository repository;

	public void saveInvoice(MultipartFile file) {
		try {
			List<Invoice> invoices = ExcelHelper.excelToInvoice(file.getInputStream());
			repository.saveAll(invoices);
		} catch (IOException e) {
			throw new RuntimeException("failed to store invoice excel data: " + e.getMessage());
		}
	}

	public List<Invoice> getAllInvoices() {
		return repository.findAll();
	}
	
	public ByteArrayInputStream load() throws IOException {
	    List<Invoice> invoice = repository.findAll();

	    ByteArrayInputStream in = ExcelHelper.invoiceToExcel(invoice);
	    return in;
	  }

}
