package com.ceiba.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	public Registro guardarRegistro(@RequestBody Registro registro) {

		Registro reg = new Registro();

		registro.setIdRegistro(java.util.UUID.randomUUID().toString().replaceAll("-",""));
		java.util.Date fecha = new Date();
		registro.setFechaingreso(fecha);
		try {
			reg = registroService.saveRegistro(registro);
		} catch (Exception e) {
			log.error(ERRORT);
			log.error(e.getMessage());
		}
		log.info(EXITOT);
		return reg;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/actualizar-registro")
	public Registro actualizarRegistro(@RequestBody Registro registro) {

		Registro reg = new Registro();
		try {
			reg = registroService.saveRegistro(registro);
		} catch (Exception e) {
			log.error(ERRORT);
		}
		log.info(EXITOT);
		return reg;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/eliminar-registro/{id}")
	public String eliminarRegistro(@PathVariable String id) {

		try {
			registroService.deleteRegistro(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ERRORT;
		}
		log.info(EXITOT);
		return EXITOT;
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
			registro.setFechasalida(date);
			registroService.saveRegistro(registro);
			if (registro != null && vehiculo.getTipoVehiculo().equals(carro)) {
				
				ParqueaderoHelper helper = new ParqueaderoHelper();
				valorGenerado = helper.procesarCobroCarro(registro.getFechaingreso(), registro.getFechasalida());
				registro.setValorpagar(valorGenerado);
				registro.setEstado("cancelado");
				registroService.saveRegistro(registro);
				
			} else if (registro != null && vehiculo.getTipoVehiculo().equals(moto)) {
				
				ParqueaderoHelper helper = new ParqueaderoHelper();
				valorGenerado = helper.procesarCobroMoto(registro.getFechaingreso(), registro.getFechasalida());
				registro.setValorpagar(valorGenerado);
				registro.setEstado("cancelado");
				registroService.saveRegistro(registro);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return registro;
	}

}
