package com.maduro.cas.core.exception.base;

import com.maduro.cas.core.exception.base.enums.ExternalServiceEnum;

import lombok.Getter;

public abstract class BaseExternalException extends BaseRuntimeException{

	@Getter
	private ExternalServiceEnum externalServiceEnum;
	
	public BaseExternalException(String message, ExternalServiceEnum externalServiceEnum) {
		super("External Service ("+externalServiceEnum.toString()+") -  "+message);
		this.externalServiceEnum = externalServiceEnum;
	}

	private static final long serialVersionUID = 1L;

}
