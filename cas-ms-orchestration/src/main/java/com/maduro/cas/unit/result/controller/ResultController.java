package com.maduro.cas.unit.result.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maduro.cas.unit.result.dto.ResultDTO;
import com.maduro.cas.unit.result.service.ResultService;

@RestController
@RequestMapping(path = "/result")
public class ResultController {

	@Autowired
	private ResultService resultService;
	
	@GetMapping("/{id}")
	public ResultDTO result(@PathVariable Long id) {
		return resultService.resultById(id);
	}

}
