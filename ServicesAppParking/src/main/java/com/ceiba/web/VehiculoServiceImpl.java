package com.ceiba.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceiba.model.Vehiculo;
import com.ceiba.service.VehiculoInterface;

@RestController
public class VehiculoServiceImpl {

	private static final Logger log = LoggerFactory.getLogger(VehiculoServiceImpl.class);
	private static final String ERRORT = "Fallo al guardar el registro del vehiculo";
	private static final String EXITOT = "Vehiculo guardado correctamente";
	
	@Autowired
	private VehiculoInterface vehiculoInterface;

	@RequestMapping(method = RequestMethod.GET, value = "/vehiculos")
	public Iterable<Vehiculo> recuperarVehiculos() {

		Iterable<Vehiculo> vehiculo;
		vehiculo = vehiculoInterface.listAllVehiculos();
		return vehiculo;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/vehiculo/{idVehiculo}")
	public Vehiculo recuperarVehiculo(@PathVariable String idVehiculo) {

		Vehiculo vehiculo;
		vehiculo = vehiculoInterface.getVehiculoById(idVehiculo);
		return vehiculo;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/guardar-vehiculo")
	public Vehiculo guardarVehiculo(@RequestBody Vehiculo vehiculo) {

		Vehiculo vehi = new Vehiculo();
		try {
			vehi = vehiculoInterface.saveVehiculo(vehiculo);
		} catch (Exception e) {
			log.error(ERRORT);
		}
		log.info(EXITOT);
		return vehi;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/actualizar-vehiculo")
	public Vehiculo actualizarVehiculo(@RequestBody Vehiculo vehiculo) {

		Vehiculo vehi = new Vehiculo();
		try {
			vehi = vehiculoInterface.saveVehiculo(vehiculo);
		} catch (Exception e) {
			log.error(ERRORT);
		}
		log.info(EXITOT);
		return vehi;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/eliminar-vehiculo/{id}")
	public String eliminarVehiculo(@PathVariable String id) {

		try {
			vehiculoInterface.deleteVehiculo(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ERRORT;
		}
		log.info(EXITOT);
		return EXITOT;
	}

}
