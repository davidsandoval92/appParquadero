package com.ceiba.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceiba.model.Registro;
import com.ceiba.model.Vehiculo;
import com.ceiba.service.AppParametrosService;
import com.ceiba.service.RegistroService;
import com.ceiba.service.VehiculoService;
import com.ceiba.web.VehiculoController;

public class SistemaCobro {

	private static final String ESTADO_ACTIVO = "activo";
	private static final String RESPONSE = "Response";
	private static final Logger log = LoggerFactory.getLogger(VehiculoController.class);
	
	/**
	 * Metodo para validar el pago de un registro(ticket).
	 * 
	 * @param vehiculoService
	 * @param registroService
	 * @param appParametrosService
	 * @param placa
	 * @param idRegistro
	 * @return
	 */
	public Map<Object, Object> validacionesPagarTicket(VehiculoService vehiculoService, RegistroService registroService,
			AppParametrosService appParametrosService, String placa, String idRegistro) {
		Registro registro = new Registro();
		Vehiculo vehiculo = new Vehiculo();

		int valorGenerado = 0;
		Date date = new Date();
		HashMap<Object, Object> response = new HashMap<>();

		try {
			vehiculo = vehiculoService.getVehiculoByPlaca(placa);
			if (vehiculo.getActivo() == 1) {
				registro = registroService.getRegistroByidVehiculoAndEstadoAndIdRegistro(vehiculo.getIdVehiculo(),
						ESTADO_ACTIVO, idRegistro);

				if (registro != null && vehiculo.getTipoVehiculo().equals("carro")) {
					registro.setFechasalida(date);
					registroService.saveRegistro(registro);
					ReglasParqueaderoHelper helper = new ReglasParqueaderoHelper();
					valorGenerado = helper.procesarCobro(registro.getFechaingreso(), registro.getFechasalida(),
							vehiculo.getTipoVehiculo(), vehiculo.getCilindraje(), appParametrosService);
					registro.setValorpagar(valorGenerado);
					registro.setEstado("cancelado");
					registroService.saveRegistro(registro);
					vehiculo.setActivo(0);
					vehiculoService.saveVehiculo(vehiculo);
					response.put(RESPONSE, "Cobro exitoso para la placa: " + vehiculo.getPlaca());

				} else if (registro != null && vehiculo.getTipoVehiculo().equals("moto")) {
					registro.setFechasalida(date);
					registroService.saveRegistro(registro);
					ReglasParqueaderoHelper helper = new ReglasParqueaderoHelper();
					valorGenerado = helper.procesarCobro(registro.getFechaingreso(), registro.getFechasalida(),
							vehiculo.getTipoVehiculo(), vehiculo.getCilindraje(), appParametrosService);
					if (vehiculo.getCilindraje() > 500) {
						valorGenerado = valorGenerado + 2000;
					}
					registro.setValorpagar(valorGenerado);
					registro.setEstado("cancelado");
					registroService.saveRegistro(registro);
					vehiculo.setActivo(0);
					vehiculoService.saveVehiculo(vehiculo);
					response.put(RESPONSE, "Cobro exitoso para la placa: " + vehiculo.getPlaca());
				} else {
					response.put(RESPONSE, "Ese registro ya esta cancelado para la placa: " + vehiculo.getPlaca());
				}
			} else {
				response.put(RESPONSE, "No tiene registros activos para la placa: " + vehiculo.getPlaca());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return response;
	}

}
