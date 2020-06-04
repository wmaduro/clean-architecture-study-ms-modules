package com.maduro.cas.unit.orchestration.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "t_orchestration")
@SequenceGenerator(name = "sq_orchestration", sequenceName = "sq_orchestration", allocationSize = 1, schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Orchestration implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "sq_orchestration")
	private Long id;
	
	@Column(nullable = false)
	private String filePath;

}
