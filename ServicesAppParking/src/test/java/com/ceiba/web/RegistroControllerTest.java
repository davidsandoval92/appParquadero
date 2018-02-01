package com.ceiba.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ceiba.model.AppParametro;
import com.ceiba.model.Registro;
import com.ceiba.model.Vehiculo;
import com.ceiba.repository.AppParametrosRepository;
import com.ceiba.repository.RegistroRepository;
import com.ceiba.repository.VehiculoRepository;
import com.ceiba.service.AppParametrosService;
import com.ceiba.service.AppParametrosServiceImpl;
import com.ceiba.service.RegistroService;
import com.ceiba.service.RegistroServiceImpl;
import com.ceiba.service.VehiculoService;
import com.ceiba.service.VehiculoServiceImpl;
import com.ceiba.testdatabuilder.AppParametrosTestDataBuilder;
import com.ceiba.testdatabuilder.RegistroTestDataBuilder;
import com.ceiba.testdatabuilder.VehiculoTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistroController.class)
public class RegistroControllerTest {
	
	@TestConfiguration
	static class VehiculoServiceImplTestContextConfiguration {

		@Bean
		public VehiculoService vehiculoService() {
			return new VehiculoServiceImpl();
		}
	}
	
	@TestConfiguration
	static class RegistroServiceImplTestContextConfiguration {

		@Bean
		public RegistroService registroService() {
			return new RegistroServiceImpl();
		}
	}
	
	@TestConfiguration
	static class AppParametroImplTestContextConfiguration {

		@Bean
		public AppParametrosService appParametro() {
			return new AppParametrosServiceImpl();
		}
	}

	@Autowired
	private MockMvc mockMvcc;

	@Autowired
	private WebApplicationContext wacc;

	@Autowired
	private RegistroService registroService;

	@Autowired
	private VehiculoService vehiculoService;
	
	@Autowired
	private AppParametrosService appParametrosService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	private VehiculoRepository vehiculoRepository;
	
	@MockBean
	private RegistroRepository registroRepository;
	
	@MockBean
	private AppParametrosRepository AppParametrosRepository;

	@Before
	public void setup() throws Exception {
		this.mockMvcc = MockMvcBuilders.webAppContextSetup(this.wacc).build();
		Vehiculo vehiculo = new VehiculoTestDataBuilder().withPlaca("NCY505").build();
		List<Vehiculo> listaVehiculos = new ArrayList<Vehiculo>();
		listaVehiculos.add(vehiculo);
		Mockito.when(vehiculoRepository.findOne(vehiculo.getIdVehiculo())).thenReturn(vehiculo);
		
	}

	@Test
	public void recuperarRegistrosTest() throws Exception {
		// Arrange
		final String url = "/registro-service/registros";
		// Act
		MvcResult mvcResult = this.mockMvcc.perform(get(url)).andDo(print()).andExpect(status().isOk()).andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void recuperarRegistroByIdTest() throws Exception {
		// Arrange
		final String url = "/registro-service/registrobyid/{id}";
		Registro registro = new RegistroTestDataBuilder().withFechaingreso(null).withFechasalida(null).build();
		Mockito.when(registroRepository.findOne(registro.getIdVehiculo())).thenReturn(registro);
		// Act
		MvcResult mvcResult = this.mockMvcc.perform(get(url, "1")).andDo(print()).andExpect(status().isOk())
				.andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	
	@Test
	public void recuperarRegistrosByIdVehiculoTest() throws Exception {
		// Arrange
		final String url = "/registro-service/registrosbyidVehiculo/{idVehiculo}";
		// Act
		MvcResult mvcResult = this.mockMvcc.perform(get(url, "1")).andDo(print()).andExpect(status().isOk())
				.andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void pagarTicketCarroTest() throws Exception {
		// Arrange
		final String url = "/registro-service/pagar-ticket/{placa}/{idRegistro}";
		Vehiculo vehiculo = new VehiculoTestDataBuilder().withPlaca("NCY505").build();
		Mockito.when(vehiculoRepository.findByplaca("NCY505")).thenReturn(vehiculo);
		Registro registro = new RegistroTestDataBuilder().withIdRegistro("123").build();
		Mockito.when(registroService.getRegistroByidVehiculoAndEstado(vehiculo.getIdVehiculo(), "activo")).thenReturn(registro);
		Mockito.when(registroService.getRegistroByidVehiculoAndEstadoAndIdRegistro(vehiculo.getIdVehiculo(), "activo",registro.getIdRegistro())).thenReturn(registro);
		
		// Act
		MvcResult mvcResult = this.mockMvcc.perform(get(url, "NCY505", "123")).andDo(print()).andExpect(status().isOk())
				.andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void pagarTicketMotoTest() throws Exception {
		// Arrange
		final String url = "/registro-service/pagar-ticket/{placa}/{idRegistro}";
		Vehiculo vehiculo = new VehiculoTestDataBuilder().withPlaca("NCY505").withTipoVehiculo("moto").withCilindraje(600).build();
		Mockito.when(vehiculoRepository.findByplaca("NCY505")).thenReturn(vehiculo);
		Registro registro = new RegistroTestDataBuilder().withIdRegistro("123").build();
		Mockito.when(registroService.getRegistroByidVehiculoAndEstado(vehiculo.getIdVehiculo(), "activo")).thenReturn(registro);
		Mockito.when(registroService.getRegistroByidVehiculoAndEstadoAndIdRegistro(vehiculo.getIdVehiculo(), "activo",registro.getIdRegistro())).thenReturn(registro);
		AppParametro appParametro = new AppParametrosTestDataBuilder().withIdParametros("1").withParametro("CilindrajeMaxMoto").withValor("500").build();
		AppParametro appParametroC = new AppParametrosTestDataBuilder().withIdParametros("1").withParametro("ValorCilindrajeMax").withValor("2000").build();
		Mockito.when(appParametrosService.getParametroByParametro("CilindrajeMaxMoto")).thenReturn(appParametro);
		Mockito.when(appParametrosService.getParametroByParametro("ValorCilindrajeMax")).thenReturn(appParametroC);		
		// Act
		MvcResult mvcResult = this.mockMvcc.perform(get(url, "NCY505","123")).andDo(print()).andExpect(status().isOk())
				.andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void pagarTicketVehiculoYaPagoTest() throws Exception {
		// Arrange
		final String url = "/registro-service/pagar-ticket/{placa}/{idRegistro}";
		Vehiculo vehiculo = new VehiculoTestDataBuilder().withPlaca("NCY505").withActivo(0).build();
		Mockito.when(vehiculoRepository.findByplaca("NCY505")).thenReturn(vehiculo);
		Registro registro = new RegistroTestDataBuilder().withIdRegistro("123").build();
		Mockito.when(registroService.getRegistroByidVehiculoAndEstado(vehiculo.getIdVehiculo(), "activo")).thenReturn(registro);
		Mockito.when(registroService.getRegistroByidVehiculoAndEstadoAndIdRegistro(vehiculo.getIdVehiculo(), "activo",registro.getIdRegistro())).thenReturn(registro);
		
		// Act
		MvcResult mvcResult = this.mockMvcc.perform(get(url, "NCY505", "123")).andDo(print()).andExpect(status().isOk())
				.andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void pagarTicketVehiculoSinRegistrosTest() throws Exception {
		// Arrange
		final String url = "/registro-service/pagar-ticket/{placa}/{idRegistro}";
		Vehiculo vehiculo = new VehiculoTestDataBuilder().withPlaca("NCY505").withActivo(1).build();
		Mockito.when(vehiculoRepository.findByplaca("NCY505")).thenReturn(vehiculo);
		Registro registro = new RegistroTestDataBuilder().withIdRegistro("123").build();
		Mockito.when(registroService.getRegistroByidVehiculoAndEstado(vehiculo.getIdVehiculo(), "activo")).thenReturn(null);
		Mockito.when(registroService.getRegistroByidVehiculoAndEstadoAndIdRegistro(vehiculo.getIdVehiculo(), "activo",registro.getIdRegistro())).thenReturn(null);
		
		// Act
		MvcResult mvcResult = this.mockMvcc.perform(get(url, "NCY505", "123")).andDo(print()).andExpect(status().isOk())
				.andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void guardarRegistroTest() throws Exception {
		// Arrange
		final String url = "/registro-service/guardar-registro";
		Registro registro = new RegistroTestDataBuilder().build();
		// Act
		MvcResult mvcResult = (MvcResult) this.mockMvcc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registro)))
				.andExpect(status().isOk()).andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void recuperarRegistrosVehiculoConsultaTest() throws Exception {
		// Arrange
		final String url = "/registro-service/vehiculos-consulta/{placa}";
		Vehiculo vehiculo = new VehiculoTestDataBuilder().withPlaca("NCY505").withIdVehiculo("123").build();
		Mockito.when(vehiculoService.getVehiculoByPlaca("NCY505")).thenReturn(vehiculo);
		Registro registro = new RegistroTestDataBuilder().withIdVehiculo("123").build();
		List<Registro>registros = new ArrayList<>();
		registros.add(registro);
		Mockito.when(registroService.getRegistrosByIdVehiculo(vehiculo.getIdVehiculo())).thenReturn(registros);
		
		// Act
		MvcResult mvcResult = this.mockMvcc.perform(get(url, "NCY505")).andDo(print()).andExpect(status().isOk())
				.andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	
}
