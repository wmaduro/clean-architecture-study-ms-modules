package com.maduro.poker.casmsorchestrator.unit.orchestrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maduro.poker.casmsorchestrator.unit.orchestrator.domain.Orchestration;

public interface OrchestrationRepository 
	extends JpaRepository<Orchestration, Long> {
}
