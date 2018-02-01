package com.ceiba.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.helpers.SistemaCobro;
import com.ceiba.helpers.SistemaConsultasHelper;
import com.ceiba.helpers.SistemaRegistroHelper;
import com.ceiba.model.Registro;
import com.ceiba.service.AppParametrosService;
import com.ceiba.service.RegistroService;
import com.ceiba.service.VehiculoService;

@RestController
@RequestMapping("/registro-service")
public class RegistroController {

	@Autowired
	private RegistroService registroService;

	@Autowired
	private VehiculoService vehiculoService;

	@Autowired
	private AppParametrosService appParametrosService;

	/**
	 * Servicio para recuperar todos los registros(tickets) creados.
	 * 
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.GET, value = "/registros")
	public Iterable<Registro> recuperarRegistros() {

		Iterable<Registro> registro;
		registro = registroService.listAllRegistro();
		return registro;
	}

	/**
	 * Servicio que recupera un registro por el Id del registro(ticket).
	 * 
	 * @param id
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.GET, value = "/registrobyid/{id}")
	public Map<Object, Object> recuperarRegistroById(@PathVariable String id) {

		SistemaConsultasHelper sistemaConsultaHelper = new SistemaConsultasHelper();
		return sistemaConsultaHelper.recuperarRegistroByIdHelper(id, registroService);

	}

	/**
	 * Servicio que recupera un registro por el Id del vehiculo.
	 * 
	 * @param idVehiculo
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.GET, value = "/registrosbyidVehiculo/{idVehiculo}")
	public List<Registro> recuperarRegistrosByIdVehiculo(@PathVariable String idVehiculo) {

		List<Registro> registro;
		registro = registroService.getRegistrosByIdVehiculo(idVehiculo);
		return registro;
	}

	/**
	 * Servicio que persiste la informacion de un registro(ticket);
	 * 
	 * @param registro
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.POST, value = "/guardar-registro")
	public Map<String, String> guardarRegistro(@RequestBody Registro registro) {

		SistemaRegistroHelper registrohelper = new SistemaRegistroHelper();
		return registrohelper.guardarRegistroHelper(registro, registroService);

	}

	/**
	 * Servicio procesa el cobro de un registro(ticket).
	 * 
	 * @param placa
	 * @param idRegistro
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.GET, value = "/pagar-ticket/{placa}/{idRegistro}")
	public Map<Object, Object> pagarTicketCarro(@PathVariable String placa, @PathVariable String idRegistro) {

		SistemaCobro sistemaCobro = new SistemaCobro();
		return sistemaCobro.validacionesPagarTicket(vehiculoService, registroService, appParametrosService, placa,
				idRegistro);
	}

	/**
	 * Servicio que recupera los registros(tickets) por la placa del vehiculo.
	 * 
	 * @param placa
	 * @return
	 */
	@CrossOrigin(origins = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.GET, value = "/vehiculos-consulta/{placa}")
	public List<HashMap<String, String>> recuperarRegistrosVehiculoConsulta(@PathVariable String placa) {

		SistemaConsultasHelper sistemaConsultaHelper = new SistemaConsultasHelper();
		return sistemaConsultaHelper.recuperarRegistroHelper(vehiculoService, registroService, placa);
	}
}
