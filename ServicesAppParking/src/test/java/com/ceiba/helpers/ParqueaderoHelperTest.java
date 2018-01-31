package com.ceiba.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.testdatabuilder.AppParametrosTestDataBuilder;
import com.ceiba.testdatabuilder.VehiculoTestDataBuilder;
import com.ceiba.helpers.ParqueaderoHelper;
import com.ceiba.model.AppParametro;
import com.ceiba.model.Vehiculo;
import com.ceiba.service.AppParametrosServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParqueaderoHelperTest {
	
	@TestConfiguration
	static class AppParametrosServiceImplTestContextConfiguration {

		@Bean
		public com.ceiba.service.AppParametrosService appParametrosService() {
			return new AppParametrosServiceImpl();
		}
	}
	
	@Autowired
	private TestEntityManager entityManager;
	
	@MockBean
	private com.ceiba.service.AppParametrosService appParametrosService ;

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
		boolean flag = true;
		Vehiculo vehiculo = new VehiculoTestDataBuilder().withPlaca("bRM17D").build();
		ParqueaderoHelper pruHelper = new ParqueaderoHelper();
		// Act
		flag = pruHelper.validarPlacaLunesDomingos(vehiculo.getPlaca());
		// Assert
		Assert.assertTrue(flag);

	}

	@Test
	public void validarCobroHoraCarro() throws ParseException {
		// Arrange
		AppParametro parametro = new AppParametrosTestDataBuilder().build();
		Mockito.when(appParametrosService.getParametroByParametro("ValorHoraCarro")).thenReturn(parametro);
		
		int valorPagar = 0;
		int valorEsperado = 2000;
		int cilindraje = 2000;
		final String tipoVehiculoC = "carro";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Date fechaInicial=dateFormat.parse("2017-01-15 23:00:00");
        Date fechaFinal=dateFormat.parse("2017-01-16 01:10:10");
		ParqueaderoHelper pruHelper = new ParqueaderoHelper();
		

		// Act
		valorPagar = pruHelper.procesarCobro(fechaInicial, fechaFinal, tipoVehiculoC, cilindraje, appParametrosService);
		// Assert
		Assert.assertEquals(valorPagar, valorEsperado);
	}
	
	@Test
	public void validarCobroDiaCarro() throws ParseException {
		// Arrange
		AppParametro parametro = new AppParametrosTestDataBuilder().withParametro("ValorDiaCarro").withValor("8000").build();
		Mockito.when(appParametrosService.getParametroByParametro("ValorDiaCarro")).thenReturn(parametro);
		
		int valorPagar = 0;
		int valorEsperado = 8000;
		int cilindraje = 2000;
		final String tipoVehiculoC = "carro";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Date fechaInicial=dateFormat.parse("2017-01-15 07:00:00");
        Date fechaFinal=dateFormat.parse("2017-01-15 16:10:10");
		ParqueaderoHelper pruHelper = new ParqueaderoHelper();

		// Act
		valorPagar = pruHelper.procesarCobro(fechaInicial, fechaFinal, tipoVehiculoC, cilindraje, appParametrosService);
		// Assert
		Assert.assertEquals(valorPagar, valorEsperado);
	}

	@Test
	public void validarCobroHoraMoto() throws ParseException {
		AppParametro parametro = new AppParametrosTestDataBuilder().withParametro("ValorHoraMoto").withValor("500").build();
		Mockito.when(appParametrosService.getParametroByParametro("ValorHoraMoto")).thenReturn(parametro);
		
		int valorPagar = 0;
		int valorEsperado = 4000;
		int cilindraje = 2000;
		final String tipoVehiculoM = "moto";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Date fechaInicial=dateFormat.parse("2017-01-15 07:00:00");
        Date fechaFinal=dateFormat.parse("2017-01-15 11:10:10");
		ParqueaderoHelper pruHelper = new ParqueaderoHelper();

		// Act
		valorPagar = pruHelper.procesarCobro(fechaInicial, fechaFinal, tipoVehiculoM, cilindraje, appParametrosService);
		// Assert
		Assert.assertEquals(valorPagar, valorEsperado);
	}
	
	@Test
	public void validarCobroDiaMoto() throws ParseException {
		AppParametro parametro = new AppParametrosTestDataBuilder().withParametro("ValorDiaMoto").withValor("600").build();
		Mockito.when(appParametrosService.getParametroByParametro("ValorDiaMoto")).thenReturn(parametro);
		
		int valorPagar = 0;
		int valorEsperado = 2600;
		int cilindraje = 2000;
		final String tipoVehiculoM = "moto";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Date fechaInicial=dateFormat.parse("2017-01-15 07:00:00");
        Date fechaFinal=dateFormat.parse("2017-01-15 16:00:10");
		ParqueaderoHelper pruHelper = new ParqueaderoHelper();

		// Act
		valorPagar = pruHelper.procesarCobro(fechaInicial, fechaFinal, tipoVehiculoM, cilindraje, appParametrosService);
		// Assert
		Assert.assertEquals(valorPagar, valorEsperado);
	}

}
