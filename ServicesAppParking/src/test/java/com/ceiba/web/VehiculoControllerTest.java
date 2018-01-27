package com.ceiba.web;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ceiba.model.Vehiculo;
import com.ceiba.service.VehiculoService;
import com.ceiba.testdatabuilder.VehiculoTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(VehiculoController.class)
public class VehiculoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@MockBean
	private VehiculoService vehiculoService;

	@Autowired
	VehiculoController vehiController;
	
	@Autowired
	ObjectMapper objectMapper;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void givenVehiculos_whenGetVehiculos_thenReturnJsonArray() throws Exception {
		// Arrange
		final String url = "/vehiculo-service/vehiculos";
		// Act
		MvcResult mvcResult = this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk()).andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void recuperarVehiculosTest() throws Exception {
		// Arrange
		final String url = "/vehiculo-service/vehiculo/{idVehiculo}";
		// Act
		MvcResult mvcResult = this.mockMvc.perform(get(url, "1")).andDo(print()).andExpect(status().isOk()).andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void recuperarVehiculoByPlacaTest() throws Exception {

		MvcResult mvcResult = this.mockMvc.perform(get("/vehiculo-service/vehiculobyplaca/{placa}", "NCY505"))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void guardarVehiculoTest() throws Exception {
		// Arrange
		final String url = "/vehiculo-service/guardar-vehiculo";
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		// Act
		MvcResult mvcResult = (MvcResult) this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehiculo)))
				.andExpect(status().isOk()).andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void eliminarVehiculoTest() throws Exception {

		MvcResult mvcResult = this.mockMvc.perform(delete("/vehiculo-service/eliminar-vehiculo/{id}", "1"))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void catidadCarrosActivosTest() throws Exception {
		// Arrange
		final String tipoVehiculo = "carro";
		// Act
		boolean flag = vehiController.catidadCarrosActivos(tipoVehiculo);
		// Assert
		Assert.assertFalse(flag);
	}
	
	@Test
	public void actualizarVehiculoTest() throws Exception {
		// Arrange
		final String url = "/vehiculo-service/actualizar-vehiculo";
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		// Act
		MvcResult mvcResult = (MvcResult) this.mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehiculo)))
				.andExpect(status().isOk()).andReturn();
		// Assert
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}

}
