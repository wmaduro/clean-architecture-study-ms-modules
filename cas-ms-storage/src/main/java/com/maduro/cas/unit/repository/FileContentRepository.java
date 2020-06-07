package com.maduro.cas.unit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maduro.cas.unit.domain.FileContent;

public interface FileContentRepository 
	extends JpaRepository<FileContent, Long> {
}
