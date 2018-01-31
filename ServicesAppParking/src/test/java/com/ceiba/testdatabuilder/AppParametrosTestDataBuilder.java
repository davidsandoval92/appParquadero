package com.ceiba.testdatabuilder;

import com.ceiba.model.AppParametro;

public class AppParametrosTestDataBuilder {
	
	private String idParametros;
	private String parametro;
	private String valor;
	
	public AppParametrosTestDataBuilder(){
		
		this.idParametros = "1";
		this.parametro = "ValorHoraCarro";
		this.valor = "1000";
	}
	
	public AppParametrosTestDataBuilder withIdParametros(String idParametros) {
		this.idParametros = idParametros;
		return this;
	}
	
	public AppParametrosTestDataBuilder withParametro(String parametro) {
		this.parametro = parametro;
		return this;
	}
	
	public AppParametrosTestDataBuilder withValor(String valor) {
		this.valor = valor;
		return this;
	}
	
	public AppParametro build() {
		return new AppParametro(this.idParametros, this.parametro, this.valor);
	}

}
