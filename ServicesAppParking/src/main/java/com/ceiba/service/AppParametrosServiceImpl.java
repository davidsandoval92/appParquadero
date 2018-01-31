package com.ceiba.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ceiba.model.AppParametro;
import com.ceiba.repository.AppParametrosRepository;

public class AppParametrosServiceImpl implements AppParametrosService{

	@Autowired
	AppParametrosRepository appParametrosRepository;
	
	@Override
	public AppParametro getParametroByParametro(String parametro) {
		return appParametrosRepository.findByParametro(parametro);
	}

}
