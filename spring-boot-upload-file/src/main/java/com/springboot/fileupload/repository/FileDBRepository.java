package com.springboot.fileupload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.fileupload.model.FileDB;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}
