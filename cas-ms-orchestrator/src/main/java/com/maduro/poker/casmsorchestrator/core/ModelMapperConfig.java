package com.maduro.poker.casmsorchestrator.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

//		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
//			.addMappings(mapper -> mapper.skip(ItemPedido::setId));
//		
//		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
//				Endereco.class, EnderecoModel.class);
//		
//		enderecoToEnderecoModelTypeMap.<String>addMapping(
//				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
//				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));

		return modelMapper;
	}

}
