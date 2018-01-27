package com.ceiba.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ceiba.model.Registro;
import com.ceiba.service.RegistroService;
import com.ceiba.service.VehiculoService;
import com.ceiba.testdatabuilder.RegistroTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistroController.class)
public class RegistroControllerTest {

	@Autowired
	private MockMvc mockMvcc;

	@Autowired
	private WebApplicationContext wacc;

	@MockBean
	private RegistroService registroService;

	@MockBean
	private VehiculoService vehiculoService;
	
	@Autowired
	ObjectMapper objectMapper;

	@Before
	public void setup() throws Exception {
		this.mockMvcc = MockMvcBuilders.webAppContextSetup(this.wacc).build();
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
	public void eliminarRegistroTest() throws Exception {
		// Arrange
		final String url = "/registro-service/eliminar-registro/{id}";
		// Act
		MvcResult mvcResult = this.mockMvcc.perform(delete(url, "1")).andDo(print()).andExpect(status().isOk())
				.andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	
	@Test
	public void pagarTicketCarroTest() throws Exception {
		// Arrange
		final String url = "/registro-service/pagar-ticket/{placa}";
		// Act
		MvcResult mvcResult = this.mockMvcc.perform(get(url, "1")).andDo(print()).andExpect(status().isOk())
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
	public void actualizarRegistroTest() throws Exception {
		// Arrange
		final String url = "/registro-service/actualizar-registro";
		Registro registro = new RegistroTestDataBuilder().build();
		// Act
		MvcResult mvcResult = (MvcResult) this.mockMvcc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registro)))
				.andExpect(status().isOk()).andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
}
