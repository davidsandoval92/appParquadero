package com.ceiba.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.ceiba.model.Vehiculo;
import com.ceiba.repository.VehiculoRepository;
import com.ceiba.service.VehiculoService;
import com.ceiba.service.VehiculoServiceImpl;
import com.ceiba.testdatabuilder.VehiculoTestDataBuilder;

@RunWith(SpringRunner.class)
public class VehiculoServiceImplTest {

	@TestConfiguration
	static class VehiculoServiceImplTestContextConfiguration {

		@Bean
		public VehiculoService vehiculoService() {
			return new VehiculoServiceImpl();
		}
	}

	@Autowired
	private VehiculoService vehiculoService;

	@MockBean
	private VehiculoRepository vehiculoRepository;

	@Before
	public void setUp() {
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		// Mockito para cuando consulte al reposotiry devuelva el vehiculo
		// creado
		// en la linea anterior y simular registros de BD y probar el service.
		List<Vehiculo> listaVehiculos = new ArrayList<Vehiculo>();
		listaVehiculos.add(vehiculo);

		Mockito.when(vehiculoRepository.findOne(vehiculo.getIdVehiculo())).thenReturn(vehiculo);
		Mockito.when(vehiculoRepository.findAll()).thenReturn(listaVehiculos);
	}

	/**
	 * Test para simular la solicitud por la interfaz (vehiculoService) del
	 * metodo "getVehiculoById".
	 */
	@Test
	public void validaPeticionListAllVehiculos() {
		// Arrange
		int cantidadVehiculos = 1;
		// Act
		List<Vehiculo> vehiculosRecuperados = (List<Vehiculo>) vehiculoService.listAllVehiculos();
		int cantidadRegistros = vehiculosRecuperados.size();
		// Assert
		Assert.assertEquals("Valor recuperado es igual", cantidadRegistros, cantidadVehiculos);
	}

	/**
	 * Test para simular la solicitud por la interfaz (vehiculoService) del
	 * metodo "getVehiculoById".
	 */
	@Test
	public void validaPeticionGetIdVehiculo() {
		// Arrange
		String idVehiculo = "2";
		// Act
		Vehiculo vehiculoRecuperado = vehiculoService.getVehiculoById(idVehiculo);
		// Assert
		Assert.assertEquals("Valor recuperado es igual", vehiculoRecuperado.getIdVehiculo(), idVehiculo);
	}

	/**
	 * Test para simular la solicitud por la interfaz (vehiculoService) del
	 * metodo "getVehiculoById".
	 */
	@Test
	public void validaPeticionSaveVehiculo() {
		// Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		Mockito.when(vehiculoRepository.save(vehiculo)).thenReturn(vehiculo);
		// Act
		Vehiculo vehiculoGuardado = new Vehiculo();
		vehiculoGuardado = vehiculoService.saveVehiculo(vehiculo);
		// Assert
		Assert.assertNotNull(vehiculoGuardado);
	}
	
	/**
	 * Test para simular la solicitud por la interfaz (vehiculoService) del
	 * metodo "getVehiculoById".
	 */
	@Test
	public void validaPeticionDeleteVehiculo() {
		// Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().withIdVehiculo("5").build();
		vehiculoService.saveVehiculo(vehiculo);
		boolean flag = false;
		// Act
		try{
		vehiculoService.deleteVehiculo(vehiculo.getIdVehiculo());
		flag = true;
		}catch(Exception e){
			flag =false;
		}
		// Assert
		Assert.assertTrue(flag);
	}
}
