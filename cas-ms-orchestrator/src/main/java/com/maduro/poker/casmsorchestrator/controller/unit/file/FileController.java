package com.maduro.poker.casmsorchestrator.controller.unit.file;

import java.nio.file.Path;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/file")
public class FileController {

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void processFoto(MultipartFile file) {

		var nomeArquivo = UUID.randomUUID().toString() + "_" + file.getName();

		var arquivoFoto = Path.of("/home/maduro/lixo/lixo", nomeArquivo);

		System.out.println(arquivoFoto);

		try {
			file.transferTo(arquivoFoto);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
