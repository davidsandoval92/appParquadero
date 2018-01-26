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

import java.util.HashMap;
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
	private static final String Response = "Response";

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

	@RequestMapping(method = RequestMethod.GET, value = "/vehiculobyplaca/{placa}")
	public Vehiculo recuperarVehiculoByPlaca(@PathVariable String placa) {

		Vehiculo vehiculo;
		vehiculo = vehiculoService.getVehiculoByPlaca(placa);
		return vehiculo;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/guardar-vehiculo", produces = { "application/json" })
	public HashMap<String, String> guardarVehiculo(@RequestBody Vehiculo vehiculo) {

		HashMap<String, String> mapResponse = new HashMap<>();
		ParqueaderoHelper helper = new ParqueaderoHelper();
		boolean validacionTipoVehiculo = helper.validarTipoVehiculo(vehiculo);
		boolean validacionDisponibilidad = catidadCarrosActivos(vehiculo.getTipoVehiculo());
		boolean validacionPlaca = helper.validarPlacaLunesDomingos(vehiculo.getPlaca());
		try {
			if (vehiculoService.getVehiculoByPlaca(vehiculo.getPlaca()) == null) {
				if (validacionTipoVehiculo && validacionDisponibilidad && validacionPlaca) {
					String formattedPlaca = vehiculo.getPlaca().toUpperCase();
					vehiculo.setPlaca(formattedPlaca);
					Registro registro = new Registro();
					registro.setVehiculo(vehiculo);
					registro.setEstado("activo");
					crearRegistro(registro);
				} else {
					if (!validacionTipoVehiculo) {
						mapResponse.put(Response, "Solo se aceptan tipo de vehiculo como carro o moto.");
					} else if (!validacionDisponibilidad) {
						mapResponse.put(Response, "No hay cupo disponible para: " + vehiculo.getTipoVehiculo());
					} else if (!validacionPlaca) {
						mapResponse.put(Response, "No puede ingresar porque no está en un dia hábil");
					}
				}
			} else {
				mapResponse.put("Respuesta", "El vehiculo ya se encuentra registrado");
				return mapResponse;
			}
		} catch (Exception e) {
			log.error(ERRORT);
			mapResponse.put("RespuestaError", "Ocurrio un error");
		}
		return mapResponse;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/actualizar-vehiculo")
	public HashMap<String, String> actualizarVehiculo(@RequestBody Vehiculo vehiculo) {

		HashMap<String, String> mapResponse = new HashMap<>();
		try {
			vehiculoService.saveVehiculo(vehiculo);
			mapResponse.put(Response, "Vehiculo actualizado correctamente");
		} catch (Exception e) {
			log.error(ERRORT);
			mapResponse.put("ResponseError", "No se actualizo el vehiculo");
		}
		log.info(EXITOT);
		return mapResponse;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/eliminar-vehiculo/{id}")
	public HashMap<String, String> eliminarVehiculo(@PathVariable String id) {

		HashMap<String, String> mapResponse = new HashMap<>();

		try {
			vehiculoService.deleteVehiculo(id);
			mapResponse.put(Response, "Vehiculo eliminado");
		} catch (Exception e) {
			log.error(e.getMessage());
			mapResponse.put("ResponseError", "No se elimino el vehiculo, revisar error");
		}
		return mapResponse;
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

	public Registro crearRegistro(Registro registro) {

		String urlService = "http://localhost:8081/registro-service/guardar-registro";

		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource(urlService);
		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, registro);

		return response.getEntity(Registro.class);

	}

}
