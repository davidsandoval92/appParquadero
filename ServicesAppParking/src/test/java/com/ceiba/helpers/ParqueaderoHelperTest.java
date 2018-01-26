package com.ceiba.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.testdatabuilder.RegistroTestDataBuilder;
import com.ceiba.testdatabuilder.VehiculoTestDataBuilder;
import com.ceiba.helpers.ParqueaderoHelper;
import com.ceiba.model.Registro;
import com.ceiba.model.Vehiculo;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParqueaderoHelperTest {

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void validarTipoVehiculoMoto() {

		// Arrange
		ParqueaderoHelper helperParqueadero = new ParqueaderoHelper();
		boolean flag = false;
		Vehiculo vehiculo = new VehiculoTestDataBuilder().withTipoVehiculo("moto").build();
		entityManager.persist(vehiculo);
		entityManager.flush();

		// Act
		flag = helperParqueadero.validarTipoVehiculo(vehiculo);

		// Assert
		Assert.assertTrue(flag);

	}

	@Test
	public void validarTipoVehiculoCarro() {

		// Arrange
		ParqueaderoHelper helperParqueadero = new ParqueaderoHelper();
		boolean flag = false;
		Vehiculo vehiculo = new VehiculoTestDataBuilder().withTipoVehiculo("carro").build();
		entityManager.persist(vehiculo);
		entityManager.flush();

		// Act
		flag = helperParqueadero.validarTipoVehiculo(vehiculo);

		// Assert
		Assert.assertTrue(flag);

	}

	@Test
	public void validarDisponibilidadParqueaderoCarros() {
		// Arrange
		boolean flag = false;
		final int cantidaCarros = 19;
		final String tipoVehiculo = "carro";
		ParqueaderoHelper pruHelper = new ParqueaderoHelper();
		// Act
		flag = pruHelper.validarDisponibilidadTipoVehiculo(cantidaCarros, tipoVehiculo);
		// Assert
		Assert.assertTrue(flag);
	}

	@Test
	public void validarDisponibilidadParqueaderoMotos() {
		// Arrange
		boolean flag = false;
		final int cantidaMotos = 9;
		final String tipoVehiculo = "moto";
		ParqueaderoHelper pruHelper = new ParqueaderoHelper();
		// Act
		flag = pruHelper.validarDisponibilidadTipoVehiculo(cantidaMotos, tipoVehiculo);
		// Assert
		Assert.assertTrue(flag);

	}

	@Test
	public void validadPlaca() {
		// Arrange
		boolean flag = false;
		Vehiculo vehiculo = new VehiculoTestDataBuilder().withPlaca("aRM17D").build();
		ParqueaderoHelper pruHelper = new ParqueaderoHelper();
		// Act
		flag = pruHelper.validarPlacaLunesDomingos(vehiculo.getPlaca());
		// Assert
		Assert.assertTrue(flag);

	}

	@Test
	public void validarCobroHoraCarro() throws ParseException {
		// Arrange
		int valorPagar = 0;
		int valorEsperado = 2000;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Date fechaInicial=dateFormat.parse("2017-01-15 23:00:00");
        Date fechaFinal=dateFormat.parse("2017-01-16 01:10:10");
		ParqueaderoHelper pruHelper = new ParqueaderoHelper();

		// Act
		try {
			valorPagar = pruHelper.procesarCobroCarro(fechaInicial, fechaFinal);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Assert
		Assert.assertEquals(valorPagar, valorEsperado);
	}
	
	@Test
	public void validarCobroDiaCarro() throws ParseException {
		// Arrange
		int valorPagar = 0;
		int valorEsperado = 12000;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Date fechaInicial=dateFormat.parse("2017-01-15 07:00:00");
        Date fechaFinal=dateFormat.parse("2017-01-16 11:10:10");
		ParqueaderoHelper pruHelper = new ParqueaderoHelper();

		// Act
		try {
			valorPagar = pruHelper.procesarCobroCarro(fechaInicial, fechaFinal);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Assert
		Assert.assertEquals(valorPagar, valorEsperado);
	}

	@Test
	public void validarCobroHoraMoto() throws ParseException {
		
		int valorPagar = 0;
		int valorEsperado = 2000;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Date fechaInicial=dateFormat.parse("2017-01-15 07:00:00");
        Date fechaFinal=dateFormat.parse("2017-01-15 11:10:10");
		ParqueaderoHelper pruHelper = new ParqueaderoHelper();

		// Act
		try {
			valorPagar = pruHelper.procesarCobroMoto(fechaInicial, fechaFinal);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Assert
		Assert.assertEquals(valorPagar, valorEsperado);
	}
	
	@Test
	public void validarCobroDiaMoto() throws ParseException {
		
		int valorPagar = 0;
		int valorEsperado = 2600;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Date fechaInicial=dateFormat.parse("2017-01-15 07:00:00");
        Date fechaFinal=dateFormat.parse("2017-01-16 11:10:10");
		ParqueaderoHelper pruHelper = new ParqueaderoHelper();

		// Act
		try {
			valorPagar = pruHelper.procesarCobroMoto(fechaInicial, fechaFinal);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Assert
		Assert.assertEquals(valorPagar, valorEsperado);
	}

}
