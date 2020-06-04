package com.maduro.cas.unit.orchestration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maduro.cas.unit.orchestration.domain.Orchestration;

public interface OrchestrationRepository 
	extends JpaRepository<Orchestration, Long> {
}
