package com.maduro.poker.casmsorchestrator.unit.teste.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
