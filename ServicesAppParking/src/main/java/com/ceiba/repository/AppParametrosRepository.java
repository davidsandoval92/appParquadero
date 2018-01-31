package com.ceiba.repository;

import org.springframework.data.repository.CrudRepository;

import com.ceiba.model.AppParametro;

public interface AppParametrosRepository extends CrudRepository<AppParametro, String>{
	
	AppParametro findByParametro(String parametro);
	
}
