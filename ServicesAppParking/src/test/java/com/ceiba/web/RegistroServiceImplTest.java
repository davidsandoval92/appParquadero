package com.ceiba.web;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.model.Registro;
import com.ceiba.repository.RegistroRepository;
import com.ceiba.service.RegistroService;
import com.ceiba.service.RegistroServiceImpl;
import com.ceiba.testdatabuilder.RegistroTestDataBuilder;

@RunWith(SpringRunner.class)
public class RegistroServiceImplTest {

	@TestConfiguration
	static class RegistroServiceImplTestContextConfiguration {

		@Bean
		public RegistroService registroService() {
			return new RegistroServiceImpl();
		}
	}

	@Autowired
	private RegistroService registroService;

	@MockBean
	private RegistroRepository registroRepository;

	@Before
	public void setUp() {
		Registro registro = new RegistroTestDataBuilder().build();
		// Mockito para cuando consulte al reposotiry devuelva el vehiculo
		// creado
		// en la linea anterior y simular registros de BD y probar el service.
		List<Registro> listaRegistro = new ArrayList<Registro>();
		listaRegistro.add(registro);
		
		Mockito.when(registroRepository.findAll()).thenReturn(listaRegistro);
	}

	/**
	 * Test para simular la solicitud por la interfaz (registroService) del
	 * metodo "recuperarRegistros".
	 */
	@Test
	public void validarPeticionListAllRegistros() {
		// Arrange
		int cantidadRegistros = 1;
		// Act
		List<Registro> registroRecuperados = (List<Registro>) registroService.listAllRegistro();
		int cantidadRegistrosRecuperdos = registroRecuperados.size();
		// Assert
		Assert.assertEquals("Valor recuperado es igual", cantidadRegistrosRecuperdos, cantidadRegistros);
	}

	/**
	 * Test para simular la solicitud por la interfaz (registroService) del
	 * metodo "recuperarRegistroById".
	 */
	@Test
	public void validarPeticionGetIdRegistro() {
		// Arrange
		String idRegistro = "1";
		Registro registro = new RegistroTestDataBuilder().withIdRegistro("1").build();
		Mockito.when(registroRepository.findOne(registro.getIdVehiculo())).thenReturn(registro);
		// Act
		Registro registroRecuperado = registroService.getRegistroById(idRegistro);
		// Assert
		Assert.assertEquals("Valor recuperado es igual", registroRecuperado.getIdRegistro(), idRegistro);
	}

	/**
	 * Test para simular la solicitud por la interfaz (registroService) del
	 * metodo "getRegistroByIdVehiculo".
	 */
	@Test
	public void validarPeticionRecuperarRegistroByIdVehiculo() {
		// Arrange
		String idVehiculo = "2";
		Registro registro = new RegistroTestDataBuilder().withIdVehiculo("2").build();
		List<Registro> registros = new ArrayList<>();
		registros.add(registro);
		Mockito.when(registroRepository.findByidVehiculo(registro.getIdVehiculo())).thenReturn(registros);
		// Act
		List<Registro> registroRecuperado = registroService.getRegistrosByIdVehiculo(idVehiculo);
		// Assert
		Assert.assertEquals("Valor recuperado es igual", registroRecuperado.get(0).getIdVehiculo(), idVehiculo);
	}
	
	/**
	 * Test para simular la solicitud por la interfaz (vehiculoService) del
	 * metodo "saveRegistro".
	 */
	@Test
	public void validaPeticionSaveRegistro() {
		// Arrange
		Registro registro = new RegistroTestDataBuilder().build();
		Mockito.when(registroRepository.save(registro)).thenReturn(registro);
		// Act
		Registro registroGuardado = new Registro();
		registroGuardado = registroService.saveRegistro(registro);
		// Assert
		Assert.assertNotNull(registroGuardado);
	}

	// #################################### metodo aparte del repository ####################################//
	
	/**
	 * Test para simular la solicitud por la interfaz (vehiculoService) del
	 * metodo "deleteRegistro".
	 */
	@Test
	public void validaPeticionDeleteVehiculo() {
		// Arrange
		Registro registro = new RegistroTestDataBuilder().withIdVehiculo("5").build();
		registroService.saveRegistro(registro);
		boolean flag = false;
		// Act
		try{
		registroService.deleteRegistro(registro.getIdVehiculo());
		flag = true;
		}catch(Exception e){
			flag =false;
		}
		// Assert
		Assert.assertTrue(flag);
	}

}
