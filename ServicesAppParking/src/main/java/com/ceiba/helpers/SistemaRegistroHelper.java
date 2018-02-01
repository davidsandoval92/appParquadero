package com.ceiba.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ceiba.model.Registro;
import com.ceiba.model.Vehiculo;
import com.ceiba.service.RegistroService;
import com.ceiba.service.VehiculoService;
import com.ceiba.web.VehiculoController;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class SistemaRegistroHelper {

	private static final String RESPONSE = "Response";
	private static final Logger log = LoggerFactory.getLogger(VehiculoController.class);
	private static final String ERRORT = "Fallo al guardar el registro del vehiculo";
	private static final String EXITOT = "Vehiculo guardado correctamente";
	private static final String ESTADO_ACTIVO = "activo";

	

	/**
	 * Metodo que valida las reglas de negocio para guardar un registro(ticket).
	 * 
	 * @param registro
	 * @param registroService
	 * @return
	 */
	public Map<String, String> guardarRegistroHelper(Registro registro, RegistroService registroService) {

		HashMap<String, String> mapResponse = new HashMap<>();

		registro.setIdRegistro(java.util.UUID.randomUUID().toString().replaceAll("-", ""));
		java.util.Date fecha = new Date();
		registro.setFechaingreso(fecha);
		try {
			registroService.saveRegistro(registro);
			mapResponse.put("Respuesta", "Registro guardado correctamente");
		} catch (Exception e) {
			log.error(ERRORT);
			mapResponse.put("RespuestaError", "Ocurrio un error, revisar log");
		}
		return mapResponse;
	}

	/**
	 * Metodo que valida las reglas de negocio para guardar la informacion de un
	 * vehiculo.
	 * 
	 * @param vehiculo
	 * @param vehiculoService
	 * @return
	 */
	public Map<String, String> guardarVehiculoHelper(Vehiculo vehiculo, VehiculoService vehiculoService) {

		HashMap<String, String> mapResponse = new HashMap<>();
		ReglasParqueaderoHelper helper = new ReglasParqueaderoHelper();
		SistemaConsultasHelper consultasH = new SistemaConsultasHelper();
		boolean validacionTipoVehiculo = helper.validarTipoVehiculo(vehiculo);
		boolean validacionDisponibilidad = consultasH.catidadCarrosActivos(vehiculo.getTipoVehiculo(), vehiculoService);
		boolean validacionPlaca = helper.validarPlacaLunesDomingos(vehiculo.getPlaca());
		Vehiculo vehiculoRecu = vehiculoService.getVehiculoByPlaca(vehiculo.getPlaca());
		try {
			if (vehiculoRecu == null) {
				if (validacionTipoVehiculo && validacionDisponibilidad && validacionPlaca) {
					vehiculo.setIdVehiculo(java.util.UUID.randomUUID().toString().replaceAll("-", ""));
					vehiculo.setActivo(1);
					String formattedPlaca = vehiculo.getPlaca().toUpperCase();
					vehiculo.setPlaca(formattedPlaca);
					Registro registro = new Registro();
					registro.setVehiculo(vehiculo);
					registro.setEstado(ESTADO_ACTIVO);
					vehiculoService.saveVehiculo(vehiculo);
					SistemaRegistroHelper sistemaRegistroHelper = new SistemaRegistroHelper();
					sistemaRegistroHelper.crearRegistro(registro);
					mapResponse.put(RESPONSE, EXITOT);
				} else {
					if (!validacionTipoVehiculo) {
						mapResponse.put(RESPONSE, "Solo se aceptan tipo de vehiculo como carro o moto.");
					} else if (!validacionDisponibilidad) {
						mapResponse.put(RESPONSE, "No hay cupo disponible para: " + vehiculo.getTipoVehiculo());
					} else if (!validacionPlaca) {
						mapResponse.put(RESPONSE, "No puede ingresar porque no esta en un dia habil");
					}
				}
			} else {
				vehiculoRecu.setActivo(1);
				Registro registro = new Registro();
				registro.setVehiculo(vehiculoRecu);
				registro.setEstado(ESTADO_ACTIVO);
				vehiculoService.saveVehiculo(vehiculoRecu);
				SistemaRegistroHelper sistemaRegistroHelper = new SistemaRegistroHelper();
				sistemaRegistroHelper.crearRegistro(registro);
				mapResponse.put(RESPONSE, "El vehiculo ya se encuentra registrado, se creo un nuevo registro");
				return mapResponse;
			}
		} catch (Exception e) {
			log.error(ERRORT);
			mapResponse.put(RESPONSE, "Ocurrio un error");
		}
		return mapResponse;
	}

	/**
	 * Metodo utilizado para guardar un registro desde un client.
	 * 
	 * @param registro
	 * @return
	 */
	@CrossOrigin(origins = "*")
	public String crearRegistro(Registro registro) {

		String urlService = "http://localhost:8081/registro-service/guardar-registro";
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource(urlService);
		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, registro);
		log.info("responseClienteService", response);
		return "Ok";

	}
}
