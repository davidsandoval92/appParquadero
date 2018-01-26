package com.ceiba.web;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.model.Vehiculo;
import com.ceiba.repository.VehiculoRepository;
import com.ceiba.testdatabuilder.VehiculoTestDataBuilder;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VehiculoRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private VehiculoRepository vehiculoRepositori;

	/**
	 * Test para probar busqueda (get) el repositorio extendido de vehiculo.
	 */
	@Test
	public void validarBusquedaVehiculoById() {
		// Arrange
		// Mockito.when(emailService.sendEmail(Mockito.anyString())).thenReturn("hola
		// mundo");
		Vehiculo vehiculo = new VehiculoTestDataBuilder().withTipoVehiculo("moto").build();
		entityManager.persist(vehiculo);
		entityManager.flush();
		// Act
		Vehiculo vehiculoRecuperado = vehiculoRepositori.findOne(vehiculo.getIdVehiculo());
		// Assert
		Assert.assertEquals("Valor recuperado es igual", vehiculoRecuperado.getIdVehiculo(), vehiculo.getIdVehiculo());
	}

	/**
	 * Test para probar guardar (Post) el repositorio extendido de vehiculo.
	 */
	@Test
	public void validarGuardarVehiculo() {
		// Arrange
		// Mockito.when(emailService.sendEmail(Mockito.anyString())).thenReturn("hola
		// mundo");
		Vehiculo vehiculo = new VehiculoTestDataBuilder().withTipoVehiculo("moto").build();
		entityManager.persist(vehiculo);
		entityManager.flush();
		// Act
		Vehiculo vehiculoGuardado = vehiculoRepositori.save(vehiculo);
		// Assert
		Assert.assertNotNull(vehiculoGuardado);
	}

	/**
	 * Test para probar borrar (Delete) un registro del repositorio extendido de
	 * vehiculo.
	 */
	@Test
	public void validarBorrarVehiculoById() {
		// Arrange
		// Mockito.when(emailService.sendEmail(Mockito.anyString())).thenReturn("hola
		// mundo");
		boolean flag = false;
		Vehiculo vehiculo = new VehiculoTestDataBuilder().withTipoVehiculo("moto").build();
		entityManager.persist(vehiculo);
		entityManager.flush();
		// Act
		try {
			vehiculoRepositori.delete(vehiculo.getIdVehiculo());
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		// Assert
		Assert.assertTrue(flag);
	}

	@Test
	public void validadCantidadVehiculosActivos() {

		// Arrange
		VehiculoRepository vehiculoRepositoriTem = mock(VehiculoRepository.class);
		final int cantidadMax = 20;
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		List<Vehiculo> vehiculosActivos = new ArrayList<>();
		vehiculosActivos.size();
		for (int i = 0; i < 20; i++) {
			vehiculosActivos.add(vehiculo);
		}
		when(vehiculoRepositoriTem.findAll()).thenReturn(vehiculosActivos);
//		Mockito.when(vehiculoRepositori.findAll()).thenReturn(vehiculosActivos);

		// Act
		List<Vehiculo> vehiculos = (List<Vehiculo>) vehiculoRepositoriTem.findAll();
		// Assert
		Assert.assertEquals(vehiculos.size(), cantidadMax);
	}

}
