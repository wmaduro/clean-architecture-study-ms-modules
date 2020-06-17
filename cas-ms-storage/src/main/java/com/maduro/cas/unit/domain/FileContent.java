package com.maduro.cas.unit.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@Table(name = "t_file_content")
@SequenceGenerator(name = "sq_file_content", sequenceName = "sq_file_content", allocationSize = 1, schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class FileContent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "sq_file_content")
	private Long id;
	
	@Column(nullable = false)
	private byte[] content;

}
