package com.ceiba.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import com.ceiba.model.Vehiculo;
import com.ceiba.testdatabuilder.VehiculoTestDataBuilder;

public class VehiculoServiceImplTest {

	private VehiculoServiceImpl vehiculoServiceIml;
	
	@Before
	public void setup() {
		vehiculoServiceIml = new VehiculoServiceImpl();//Mockito.mock(EmailService.class);
	}
	
	@Test
	public void notifyTest() {
		// Arrange
		//Mockito.when(emailService.sendEmail(Mockito.anyString())).thenReturn("hola mundo");
		Vehiculo vehiculo = new VehiculoTestDataBuilder().buil();
		// Act
		Vehiculo response = vehiculoServiceIml.guardarVehiculo(vehiculo);
		// Assert
		Assert.assertNotNull(response);
	}
}
