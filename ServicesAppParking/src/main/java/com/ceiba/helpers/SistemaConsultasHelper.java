package com.ceiba.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceiba.model.Registro;
import com.ceiba.model.Vehiculo;
import com.ceiba.service.RegistroService;
import com.ceiba.service.VehiculoService;
import com.ceiba.web.VehiculoController;

public class SistemaConsultasHelper {

	private static final Logger log = LoggerFactory.getLogger(VehiculoController.class);

	/**
	 * Metodo para consultar la cantidad de carros activos segun el tipo de
	 * vehiculo.
	 * 
	 * @param tipoVehiculo
	 * @param vehiculoService
	 * @return
	 */
	public boolean catidadCarrosActivos(String tipoVehiculo, VehiculoService vehiculoService) {

		int cantidad = 0;
		final int estado = 1;
		boolean flag = false;

		try {
			List<Vehiculo> vehiculos = (List<Vehiculo>) vehiculoService.cantidadVehiculosActivos(tipoVehiculo, estado);
			cantidad = vehiculos.size();
			ReglasParqueaderoHelper pruHelper = new ReglasParqueaderoHelper();
			flag = pruHelper.validarDisponibilidadTipoVehiculo(cantidad, tipoVehiculo);

		} catch (Exception e) {
			log.info("Error en el metodo catidadCarrosActivos");
		}

		return flag;
	}

	/**
	 * Metodo para recuperar todos los vehiculos registrados.
	 * 
	 * @param vehiculoService
	 * @return
	 */
	public Iterable<Vehiculo> recuperarVehiculoHelper(VehiculoService vehiculoService) {
		Iterable<Vehiculo> vehiculo;
		vehiculo = vehiculoService.listAllVehiculos();
		return vehiculo;
	}

	/**
	 * Metodo para recuperar un vehiculo por la placa.
	 * 
	 * @param placa
	 * @param vehiculoService
	 * @return
	 */
	public Vehiculo recuperarVehiculoByPlacaHelper(String placa, VehiculoService vehiculoService) {
		Vehiculo vehiculo;
		vehiculo = vehiculoService.getVehiculoByPlaca(placa);
		return vehiculo;
	}

	/**
	 * Metodo para recuperar un vehiculo por el id del vehiculo.
	 * 
	 * @param idVehiculo
	 * @param vehiculoService
	 * @return
	 */
	public Vehiculo recuperarVehiculoHelper(String idVehiculo, VehiculoService vehiculoService) {
		Vehiculo vehiculo;
		vehiculo = vehiculoService.getVehiculoById(idVehiculo);
		return vehiculo;
	}

	/////////////////////////////////////////////////////////

	/**
	 * Metodo para recuperar un registro.
	 * 
	 * @param vehiculoService
	 * @param registroService
	 * @param placa
	 * @return
	 */
	public List<HashMap<String, String>> recuperarRegistroHelper(VehiculoService vehiculoService,
			RegistroService registroService, String placa) {

		List<HashMap<String, String>> mapResponses = new ArrayList<>();
		Vehiculo vehiculo = vehiculoService.getVehiculoByPlaca(placa.toUpperCase());
		List<Registro> registros = registroService.getRegistrosByIdVehiculo(vehiculo.getIdVehiculo());

		for (Registro registro : registros) {
			HashMap<String, String> mapResponse = new HashMap<>();
			mapResponse.put("placa", vehiculo.getPlaca());
			mapResponse.put("tipoVehiculo", vehiculo.getTipoVehiculo());
			mapResponse.put("fechaingreso", registro.getFechaingreso().toString());
			mapResponse.put("estado", registro.getEstado());
			mapResponse.put("id", registro.getIdRegistro());
			mapResponses.add(mapResponse);
		}
		return mapResponses;
	}

	/**
	 * Metodo para recuperar un registro por el Id.
	 * 
	 * @param id
	 * @param registroService
	 * @return
	 */
	public Map<Object, Object> recuperarRegistroByIdHelper(String id, RegistroService registroService) {
		Registro registro;
		registro = registroService.getRegistroById(id);
		HashMap<Object, Object> registroRecu = new HashMap<>();
		registroRecu.put("idRegistro", registro.getIdRegistro());
		registroRecu.put("estado", registro.getEstado());
		registroRecu.put("fechaingreso",
				(registro.getFechaingreso() != null) ? registro.getFechaingreso().toString() : null);
		registroRecu.put("fechasalida",
				(registro.getFechasalida() != null) ? registro.getFechasalida().toString() : null);
		registroRecu.put("valorpagar", registro.getValorpagar());
		registroRecu.put("idVehiculo", registro.getIdVehiculo());
		return registroRecu;
	}

}
