package com.ceiba.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.helpers.ParqueaderoHelper;
import com.ceiba.model.Registro;
import com.ceiba.model.Vehiculo;
import com.ceiba.service.RegistroService;
import com.ceiba.service.VehiculoService;

@RestController
@RequestMapping("/registro-service")
public class RegistroController {

	private static final Logger log = LoggerFactory.getLogger(VehiculoController.class);
	private static final String ERRORT = "Fallo al guardar el registro del vehiculo";
	private static final String EXITOT = "Vehiculo guardado correctamente";

	@Autowired
	private RegistroService registroService;

	@Autowired
	private VehiculoService vehiculoService;

	@RequestMapping(method = RequestMethod.GET, value = "/registros")
	public Iterable<Registro> recuperarRegistros() {

		Iterable<Registro> registro;
		registro = registroService.listAllRegistro();
		return registro;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/registrobyid/{id}")
	public Registro recuperarRegistroById(@PathVariable String id) {

		Registro registro;
		registro = registroService.getRegistroById(id);
		return registro;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/registrosbyidVehiculo/{idVehiculo}")
	public List<Registro> recuperarRegistrosByIdVehiculo(@PathVariable String idVehiculo) {

		List<Registro> registro;
		registro = registroService.getRegistrosByIdVehiculo(idVehiculo);
		return registro;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/guardar-registro")
	public Map<String, String> guardarRegistro(@RequestBody Registro registro) {

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

	@RequestMapping(method = RequestMethod.PUT, value = "/actualizar-registro")
	public Map<String, String> actualizarRegistro(@RequestBody Registro registro) {

		HashMap<String, String> mapResponse = new HashMap<>();
		try {
			registroService.saveRegistro(registro);
			mapResponse.put("Response", "Registro actualizado correctamente");
		} catch (Exception e) {
			log.error(ERRORT);
			mapResponse.put("ResponseError", "No se actualizo el registro");
		}
		log.info(EXITOT);
		return mapResponse;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/eliminar-registro/{id}")
	public Map<String, String> eliminarRegistro(@PathVariable String id) {

		HashMap<String, String> mapResponse = new HashMap<>();
		try {
			registroService.deleteRegistro(id);
			mapResponse.put("Response", "Registro eliminado");
		} catch (Exception e) {
			log.error(e.getMessage());
			mapResponse.put("ResponseError", "No se elimino el registro, revisar error");
		}
		return mapResponse;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/pagar-ticket/{placa}")
	public Registro pagarTicketCarro(@PathVariable String placa) {

		final String carro = "carro";
		final String moto = "moto";
		int valorGenerado = 0;
		Date date = new Date();

		Registro registro = new Registro();
		Vehiculo vehiculo = new Vehiculo();
		try {
			vehiculo = vehiculoService.getVehiculoByPlaca(placa);
			registro = registroService.getRegistroByidVehiculoAndEstado(vehiculo.getIdVehiculo(), "activo");

			if (registro != null && vehiculo.getTipoVehiculo().equals(carro)) {
				registro.setFechasalida(date);
				registroService.saveRegistro(registro);
				ParqueaderoHelper helper = new ParqueaderoHelper();
				valorGenerado = helper.procesarCobro(registro.getFechaingreso(), registro.getFechasalida(), vehiculo.getTipoVehiculo());
				registro.setValorpagar(valorGenerado);
				registro.setEstado("cancelado");
				registroService.saveRegistro(registro);

			} else if (registro != null && vehiculo.getTipoVehiculo().equals(moto)) {

				ParqueaderoHelper helper = new ParqueaderoHelper();
				valorGenerado = helper.procesarCobro(registro.getFechaingreso(), registro.getFechasalida(), vehiculo.getTipoVehiculo());
				if(vehiculo.getCilindraje()>500){
					valorGenerado = valorGenerado +2000;
				}
				registro.setValorpagar(valorGenerado);
				registro.setEstado("cancelado");
				registroService.saveRegistro(registro);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return registro;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/vehiculos-consulta/{placa}")
	public List<HashMap<String, String>> recuperarRegistrosVehiculoConsulta(@PathVariable String placa) {

		HashMap<String, String> mapResponse = new HashMap<>();
		List<HashMap<String, String>> mapResponses = new ArrayList<>();
		Vehiculo vehiculo = vehiculoService.getVehiculoByPlaca(placa);
		mapResponse.put("placa", vehiculo.getPlaca());
		mapResponse.put("tipoVehiculo", vehiculo.getPlaca());
		List<Registro>registros = registroService.getRegistrosByIdVehiculo(vehiculo.getIdVehiculo());
		
		for (Registro registro : registros) {
			mapResponse.put("placa", vehiculo.getPlaca());
			mapResponse.put("tipoVehiculo", vehiculo.getPlaca());
			mapResponse.put("fechaingreso", registro.getFechaingreso().toString());
			mapResponses.add(mapResponse);
		}
		
		return mapResponses;
	}
}
