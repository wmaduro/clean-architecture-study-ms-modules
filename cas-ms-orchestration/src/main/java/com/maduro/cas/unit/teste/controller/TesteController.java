package com.maduro.cas.unit.teste.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping(path = "/teste")
public class TesteController {

	@Autowired
	private TesteModelAssembler testeModelAssembler;

	@GetMapping
	public CollectionModel<TesteModel> listar() {
		List<TesteEntity> all = List.of(TesteEntity.builder().id(1L).message("teste 1").build());

		return testeModelAssembler.toCollectionModel(all);
	}
	
	@GetMapping("/{id}")
	public TesteModel buscar(@PathVariable Long id) {
		TesteEntity teste = TesteEntity.builder().id(1L).message("teste 1").build();
		
		return testeModelAssembler.toModel(teste);
	}
	
	@GetMapping(path="/lixo")
	public String lixo() {

		StringWriter sw = new StringWriter();

		try {

			Path path = Paths.get("/home/maduro/lixo/lixo.txt");
			BufferedReader reader = Files.newBufferedReader(path);

			char[] buffer = new char[1024 * 4];
			int n = 0;
			while (-1 != (n = reader.read(buffer))) {
				sw.write(buffer, 0, n);
			}
			String text = sw.toString();
			System.err.println(text);

		} catch (IOException e) {
			e.printStackTrace();
		}
	
		String response =  WebClient
				  .builder()
				  .baseUrl("http://localhost:20005")
				  .build()
				  .method(HttpMethod.POST)
				  .uri("/file-content")
				  .body(BodyInserters.fromValue(sw.toString().getBytes()))
				  .retrieve()
				  .bodyToMono(String.class)
				  .block()
				  ;
		
		System.out.println("response - "+response);
		
		return "lixo";
	}
}

@Data
@Builder
class TesteEntity {
	private Long id;
	private String message;
}

@Setter
@Getter
class TesteModel extends RepresentationModel<TesteModel> {
	private String message;
}

@Component
class TesteModelAssembler extends RepresentationModelAssemblerSupport<TesteEntity, TesteModel> {

	public TesteModelAssembler() {
		super(TesteController.class, TesteModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public TesteModel toModel(TesteEntity entity) {
		TesteModel testeModel = createModelWithId(entity.getId(), entity);
		modelMapper.map(entity, testeModel);

		return testeModel;
	}

	@Override
	public CollectionModel<TesteModel> toCollectionModel(Iterable<? extends TesteEntity> entities) {
		return super.toCollectionModel(entities);
	}

}
