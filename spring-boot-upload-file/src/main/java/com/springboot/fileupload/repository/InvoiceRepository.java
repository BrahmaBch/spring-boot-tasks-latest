package com.springboot.fileupload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.fileupload.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
