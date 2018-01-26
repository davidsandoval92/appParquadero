package com.ceiba.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceiba.helpers.ParqueaderoHelper;
import com.ceiba.model.Registro;
import com.ceiba.model.Vehiculo;
import com.ceiba.service.VehiculoService;

@RestController
@RequestMapping("/vehiculo-service")
public class VehiculoController {

	private static final Logger log = LoggerFactory.getLogger(VehiculoController.class);
	private static final String ERRORT = "Fallo al guardar el registro del vehiculo";
	private static final String EXITOT = "Vehiculo guardado correctamente";

	@Autowired
	private VehiculoService vehiculoService;

	@RequestMapping(method = RequestMethod.GET, value = "/vehiculos")
	public Iterable<Vehiculo> recuperarVehiculos() {

		Iterable<Vehiculo> vehiculo;
		vehiculo = vehiculoService.listAllVehiculos();
		return vehiculo;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/vehiculo/{idVehiculo}")
	public Vehiculo recuperarVehiculo(@PathVariable String idVehiculo) {

		Vehiculo vehiculo;
		vehiculo = vehiculoService.getVehiculoById(idVehiculo);
		return vehiculo;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/guardar-vehiculo")
	public Vehiculo guardarVehiculo(@RequestBody Vehiculo vehiculo) {

		Vehiculo vehi = new Vehiculo();
		Date date = new Date();
		try {
			ParqueaderoHelper helper = new ParqueaderoHelper();
			if (helper.validarTipoVehiculo(vehiculo) && catidadCarrosActivos(vehiculo.getTipoVehiculo()) && helper.validarPlacaLunesDomingos(vehiculo.getPlaca())) {
				vehi = vehiculoService.saveVehiculo(vehiculo);
				Registro registro = new Registro();
				registro.setVehiculo(vehiculo);
				registro.setEstado("activo");
				crearRegistro(registro);
			}
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
			vehi = vehiculoService.saveVehiculo(vehiculo);
		} catch (Exception e) {
			log.error(ERRORT);
		}
		log.info(EXITOT);
		return vehi;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/eliminar-vehiculo/{id}")
	public String eliminarVehiculo(@PathVariable String id) {

		try {
			vehiculoService.deleteVehiculo(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ERRORT;
		}
		log.info(EXITOT);
		return EXITOT;
	}

	public boolean catidadCarrosActivos(String tipoVehiculo) {

		int cantidad = 0;
		final int estado = 1;
		boolean flag = false;

		try {
			List<Vehiculo> vehiculos = (List<Vehiculo>) vehiculoService.cantidadVehiculosActivos(tipoVehiculo, estado);
			cantidad = vehiculos.size();
			ParqueaderoHelper pruHelper = new ParqueaderoHelper();
			flag = pruHelper.validarDisponibilidadTipoVehiculo(cantidad, tipoVehiculo);

		} catch (Exception e) {

			throw new RuntimeException(e);
		}

		return flag;
	}
	
	public Registro crearRegistro(Registro registro){
		
		String urlService = "http://localhost:8080/registro-service/guardar-registro";
		
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource(urlService);
		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, registro);
		Registro registroGuardado = response.getEntity(Registro.class);
		
		return registroGuardado;
		
	}

}
