package com.ceiba.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ceiba.service.VehiculoService;

@RunWith(SpringRunner.class)
@WebMvcTest(VehiculoController.class)
public class VehiculoControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
 
    @MockBean
    private VehiculoService vehiculoService;
    
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void givenVehiculos_whenGetVehiculos_thenReturnJsonArray()
      throws Exception {
     
        MvcResult mvcResult = this.mockMvc.perform(get("/vehiculo-service/vehiculos"))
        	      .andDo(print()).andExpect(status().isOk())
        	      .andReturn();
        
        Assert.assertEquals(200, 
        	      mvcResult.getResponse().getStatus());
    }
}
