package com.ceiba.web;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.model.Registro;
import com.ceiba.service.RegistroInterface;

@RestController
public class RegistroServiceImpl {
	
	private static final Logger log = LoggerFactory.getLogger(VehiculoServiceImpl.class);
	private static final String ERRORT = "Fallo al guardar el registro del vehiculo";
	private static final String EXITOT = "Vehiculo guardado correctamente";
	
	@Autowired
	private RegistroInterface registroInterface;
	
	@RequestMapping(method = RequestMethod.GET, value = "/registros")
	public Iterable<Registro> recuperarRegistros() {

		Iterable<Registro> registro;
		registro = registroInterface.listAllRegistro();
		return registro;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/registrobyid/{id}")
	public Registro recuperarRegistroById(@PathVariable String id) {

		Registro registro;
		registro = registroInterface.getRegistroById(id);
		return registro;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/registrobyidVehiculo/{idVehiculo}")
	public Registro recuperarRegistroByIdVehiculo(@PathVariable String idVehiculo) {

		Registro registro;
		registro = registroInterface.getRegistroByIdVehiculo(idVehiculo);
		return registro;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/guardar-registro")
	public Registro guardarRegistro(@RequestBody Registro registro) {

		Registro reg = new Registro();
		java.util.Date fecha = new Date();
		registro.setFechaingreso(fecha);
		registro.setFechasalida(fecha);
		try {
			reg = registroInterface.saveRegistro(registro);
		} catch (Exception e) {
			log.error(ERRORT);
			log.error(e.getMessage());
		}
		log.info(EXITOT);
		return reg;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/actualizar-registro")
	public Registro actualizarRegistro(@RequestBody Registro registro) {

		Registro reg = new Registro();
		try {
			reg = registroInterface.saveRegistro(registro);
		} catch (Exception e) {
			log.error(ERRORT);
		}
		log.info(EXITOT);
		return reg;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/eliminar-registro/{id}")
	public String eliminarRegistro(@PathVariable String id) {

		try {
			registroInterface.deleteRegistro(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ERRORT;
		}
		log.info(EXITOT);
		return EXITOT;
	}

}
