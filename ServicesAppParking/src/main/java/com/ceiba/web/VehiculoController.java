package com.ceiba.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import com.ceiba.helpers.SistemaConsultasHelper;
import com.ceiba.helpers.SistemaRegistroHelper;
import com.ceiba.model.Vehiculo;
import com.ceiba.service.VehiculoService;

@RestController
@RequestMapping("/vehiculo-service")
public class VehiculoController {

	@Autowired
	private VehiculoService vehiculoService;

	/**
	 * Servicio que recupera la informacion de todos los vehiculos almacenados.
	 * 
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.GET, value = "/vehiculos")
	public Iterable<Vehiculo> recuperarVehiculos() {

		SistemaConsultasHelper consultaH = new SistemaConsultasHelper();
		return consultaH.recuperarVehiculoHelper(vehiculoService);

	}

	/**
	 * Servicio que recupera la informacion de un vehiculo por el Id del
	 * vehiculo.
	 * 
	 * @param idVehiculo
	 * @return
	 */
	@CrossOrigin(origins = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.GET, value = "/vehiculo/{idVehiculo}")
	public Vehiculo recuperarVehiculo(@PathVariable String idVehiculo) {

		SistemaConsultasHelper consultaH = new SistemaConsultasHelper();
		return consultaH.recuperarVehiculoHelper(idVehiculo, vehiculoService);
	}

	/**
	 * Servicio que recupera la informacion de un vehiculo seguna la placa del
	 * mismo.
	 * 
	 * @param placa
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.GET, value = "/vehiculobyplaca/{placa}")
	public Vehiculo recuperarVehiculoByPlaca(@PathVariable String placa) {

		SistemaConsultasHelper consultaH = new SistemaConsultasHelper();
		return consultaH.recuperarVehiculoByPlacaHelper(placa, vehiculoService);
	}

	/**
	 * Servicio que persiste la informacion de un vehiculo.
	 * 
	 * @param vehiculo
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.POST, value = "/guardar-vehiculo", produces = { "application/json" })
	public Map<String, String> guardarVehiculo(@RequestBody Vehiculo vehiculo) {

		SistemaRegistroHelper registrohelper = new SistemaRegistroHelper();
		return registrohelper.guardarVehiculoHelper(vehiculo, vehiculoService);

	}

}
