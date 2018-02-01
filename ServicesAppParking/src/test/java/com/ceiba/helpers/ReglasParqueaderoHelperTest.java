package com.ceiba.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.testdatabuilder.AppParametrosTestDataBuilder;
import com.ceiba.testdatabuilder.VehiculoTestDataBuilder;
import com.ceiba.helpers.ReglasParqueaderoHelper;
import com.ceiba.model.AppLog;
import com.ceiba.model.AppParametro;
import com.ceiba.model.Vehiculo;
import com.ceiba.service.AppParametrosServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReglasParqueaderoHelperTest {
	
	private static final Logger log = LoggerFactory.getLogger(ReglasParqueaderoHelperTest.class);
	
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
		ReglasParqueaderoHelper helperParqueadero = new ReglasParqueaderoHelper();
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
		ReglasParqueaderoHelper helperParqueadero = new ReglasParqueaderoHelper();
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
		ReglasParqueaderoHelper pruHelper = new ReglasParqueaderoHelper();
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
		ReglasParqueaderoHelper pruHelper = new ReglasParqueaderoHelper();
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
		ReglasParqueaderoHelper pruHelper = new ReglasParqueaderoHelper();
		Vehiculo vehiculoPru = new Vehiculo("1");
		log.info("Valor", vehiculoPru);
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
		AppLog appLog = new AppLog();
		Date date = new Date();
		appLog.setFecha(date);
		appLog.setIdLog("1");
		appLog.setServicio("ValidarCobro");
		log.info(appLog.getIdLog());
		log.info(appLog.getFecha().toString());
		log.info(appLog.getServicio());
		
		int valorPagar = 0;
		int valorEsperado = 2000;
		int cilindraje = 2000;
		final String tipoVehiculoC = "carro";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Date fechaInicial=dateFormat.parse("2017-01-15 23:00:00");
        Date fechaFinal=dateFormat.parse("2017-01-16 01:10:10");
		ReglasParqueaderoHelper pruHelper = new ReglasParqueaderoHelper();
		

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
		AppParametro parametroCampos = new AppParametro();
		parametroCampos.setIdParametros("1");
		parametroCampos.setParametro("Cilindraje");
		parametroCampos.setValor("5000");
		log.info(parametroCampos.getIdParametros());
		log.info(parametroCampos.getParametro());
		log.info(parametroCampos.getValor());
		
		int valorPagar = 0;
		int valorEsperado = 8000;
		int cilindraje = 2000;
		final String tipoVehiculoC = "carro";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Date fechaInicial=dateFormat.parse("2017-01-15 07:00:00");
        Date fechaFinal=dateFormat.parse("2017-01-15 16:10:10");
		ReglasParqueaderoHelper pruHelper = new ReglasParqueaderoHelper();

		// Act
		valorPagar = pruHelper.procesarCobro(fechaInicial, fechaFinal, tipoVehiculoC, cilindraje, appParametrosService);
		// Assert
		Assert.assertEquals(valorPagar, valorEsperado);
	}

	@Test
	public void validarCobroHoraMoto() throws ParseException {
		AppParametro parametro = new AppParametrosTestDataBuilder().withParametro("ValorHoraMoto").withValor("500").build();
		Mockito.when(appParametrosService.getParametroByParametro("ValorHoraMoto")).thenReturn(parametro);
		AppParametro appParametro = new AppParametrosTestDataBuilder().withIdParametros("1").withParametro("CilindrajeMaxMoto").withValor("500").build();
		AppParametro appParametroC = new AppParametrosTestDataBuilder().withIdParametros("1").withParametro("ValorCilindrajeMax").withValor("2000").build();
		Mockito.when(appParametrosService.getParametroByParametro("CilindrajeMaxMoto")).thenReturn(appParametro);
		Mockito.when(appParametrosService.getParametroByParametro("ValorCilindrajeMax")).thenReturn(appParametroC);		
		
		int valorPagar = 0;
		int valorEsperado = 4000;
		int cilindraje = 2000;
		final String tipoVehiculoM = "moto";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Date fechaInicial=dateFormat.parse("2017-01-15 07:00:00");
        Date fechaFinal=dateFormat.parse("2017-01-15 11:10:10");
		ReglasParqueaderoHelper pruHelper = new ReglasParqueaderoHelper();

		// Act
		valorPagar = pruHelper.procesarCobro(fechaInicial, fechaFinal, tipoVehiculoM, cilindraje, appParametrosService);
		// Assert
		Assert.assertEquals(valorPagar, valorEsperado);
	}
	
	@Test
	public void validarCobroDiaMoto() throws ParseException {
		AppParametro parametro = new AppParametrosTestDataBuilder().withParametro("ValorDiaMoto").withValor("600").build();
		Mockito.when(appParametrosService.getParametroByParametro("ValorDiaMoto")).thenReturn(parametro);
		AppParametro appParametro = new AppParametrosTestDataBuilder().withIdParametros("1").withParametro("CilindrajeMaxMoto").withValor("500").build();
		AppParametro appParametroC = new AppParametrosTestDataBuilder().withIdParametros("1").withParametro("ValorCilindrajeMax").withValor("2000").build();
		Mockito.when(appParametrosService.getParametroByParametro("CilindrajeMaxMoto")).thenReturn(appParametro);
		Mockito.when(appParametrosService.getParametroByParametro("ValorCilindrajeMax")).thenReturn(appParametroC);		
		
		int valorPagar = 0;
		int valorEsperado = 2600;
		int cilindraje = 2000;
		final String tipoVehiculoM = "moto";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Date fechaInicial=dateFormat.parse("2017-01-15 07:00:00");
        Date fechaFinal=dateFormat.parse("2017-01-15 16:00:10");
		ReglasParqueaderoHelper pruHelper = new ReglasParqueaderoHelper();

		// Act
		valorPagar = pruHelper.procesarCobro(fechaInicial, fechaFinal, tipoVehiculoM, cilindraje, appParametrosService);
		// Assert
		Assert.assertEquals(valorPagar, valorEsperado);
	}

}
