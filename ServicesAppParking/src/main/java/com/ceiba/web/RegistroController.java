package com.ceiba.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.helpers.ParqueaderoHelper;
import com.ceiba.model.Registro;
import com.ceiba.model.Vehiculo;
import com.ceiba.service.AppParametrosService;
import com.ceiba.service.RegistroService;
import com.ceiba.service.VehiculoService;

@RestController
@RequestMapping("/registro-service")
public class RegistroController {

	private static final Logger log = LoggerFactory.getLogger(VehiculoController.class);
	private static final String ERRORT = "Fallo al guardar el registro del vehiculo";
	private static final String EXITOT = "Vehiculo guardado correctamente";
	private static final String RESPONSE = "Response";
	

	@Autowired
	private RegistroService registroService;

	@Autowired
	private VehiculoService vehiculoService;

	@Autowired
	private AppParametrosService appParametrosService;

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.GET, value = "/registros")
	public Iterable<Registro> recuperarRegistros() {

		Iterable<Registro> registro;
		registro = registroService.listAllRegistro();
		return registro;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.GET, value = "/registrobyid/{id}")
	public Map<Object,Object> recuperarRegistroById(@PathVariable String id) {

		Registro registro;
		registro = registroService.getRegistroById(id);
		HashMap<Object,Object> registroRecu = new HashMap<>();
		registroRecu.put("idRegistro", registro.getIdRegistro());
		registroRecu.put("estado", registro.getEstado());
		registroRecu.put("fechaingreso",(registro.getFechaingreso()!= null)?registro.getFechaingreso().toString():null);
		registroRecu.put("fechasalida",(registro.getFechasalida()!= null)?registro.getFechaingreso().toString():null);
		registroRecu.put("valorpagar", registro.getValorpagar());
		registroRecu.put("idVehiculo", registro.getIdVehiculo());
		return registroRecu;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.GET, value = "/registrosbyidVehiculo/{idVehiculo}")
	public List<Registro> recuperarRegistrosByIdVehiculo(@PathVariable String idVehiculo) {

		List<Registro> registro;
		registro = registroService.getRegistrosByIdVehiculo(idVehiculo);
		return registro;
	}

	@CrossOrigin(origins = "*")
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

	@CrossOrigin(origins = "*")
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

	@CrossOrigin(origins = "*")
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

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.GET, value = "/pagar-ticket/{placa}/{idRegistro}")
	public Map<Object,Object> pagarTicketCarro(@PathVariable String placa, @PathVariable String idRegistro) {

		final String carro = "carro";
		final String moto = "moto";
		int valorGenerado = 0;
		Date date = new Date();
		HashMap<Object,Object>response = new HashMap<>();

		Registro registro = new Registro();
		Vehiculo vehiculo = new Vehiculo();
		try {
			vehiculo = vehiculoService.getVehiculoByPlaca(placa);
			if(vehiculo.getActivo()==1){
			registro = registroService.getRegistroByidVehiculoAndEstadoAndIdRegistro(vehiculo.getIdVehiculo(), "activo",idRegistro);

			if (registro != null && vehiculo.getTipoVehiculo().equals(carro)) {
				registro.setFechasalida(date);
				registroService.saveRegistro(registro);
				ParqueaderoHelper helper = new ParqueaderoHelper();
				valorGenerado = helper.procesarCobro(registro.getFechaingreso(), registro.getFechasalida(), vehiculo.getTipoVehiculo(), vehiculo.getCilindraje(), appParametrosService);
				registro.setValorpagar(valorGenerado);
				registro.setEstado("cancelado");
				registroService.saveRegistro(registro);
				vehiculo.setActivo(0);
				vehiculoService.saveVehiculo(vehiculo);
				response.put(RESPONSE, "Cobro exitoso para la placa: "+vehiculo.getPlaca());

			} else if (registro != null && vehiculo.getTipoVehiculo().equals(moto)) {
				registro.setFechasalida(date);
				registroService.saveRegistro(registro);
				ParqueaderoHelper helper = new ParqueaderoHelper();
				valorGenerado = helper.procesarCobro(registro.getFechaingreso(), registro.getFechasalida(), vehiculo.getTipoVehiculo(), vehiculo.getCilindraje(), appParametrosService);
				if(vehiculo.getCilindraje()>500){
					valorGenerado = valorGenerado +2000;
				}
				registro.setValorpagar(valorGenerado);
				registro.setEstado("cancelado");
				registroService.saveRegistro(registro);
				vehiculo.setActivo(0);
				vehiculoService.saveVehiculo(vehiculo);
				response.put(RESPONSE, "Cobro exitoso para la placa: "+vehiculo.getPlaca());
			}else{
				response.put(RESPONSE, "Ese registro ya esta cancelado para la placa: "+vehiculo.getPlaca());	
			}
			}else{
				response.put(RESPONSE, "No tiene registros activos para la placa: "+vehiculo.getPlaca());	
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return response;
	}
	
	@CrossOrigin(origins = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.GET, value = "/vehiculos-consulta/{placa}")
	public List<HashMap<String, String>> recuperarRegistrosVehiculoConsulta(@PathVariable String placa) {

		
		List<HashMap<String, String>> mapResponses = new ArrayList<>();
		Vehiculo vehiculo = vehiculoService.getVehiculoByPlaca(placa.toUpperCase());
		List<Registro>registros = registroService.getRegistrosByIdVehiculo(vehiculo.getIdVehiculo());
		
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
}
