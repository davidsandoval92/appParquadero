package com.ceiba.web;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.model.Registro;
import com.ceiba.repository.RegistroRepository;
import com.ceiba.testdatabuilder.RegistroTestDataBuilder;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RegistroRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private RegistroRepository registroRepositori;
	
	/**
	 * Test para probar busqueda (get) el repositorio extendido de registro.
	 */
	@Test
	public void validarBusquedaRegistroById() {
		// Arrange
		//Mockito.when(emailService.sendEmail(Mockito.anyString())).thenReturn("hola mundo");
		Registro registro = new RegistroTestDataBuilder().withIdRegistro("1").build();
		entityManager.persist(registro);
	    entityManager.flush();
		// Act
		Registro registroRecuperado = registroRepositori.findOne(registro.getIdRegistro());
		// Assert
		Assert.assertEquals("Valor recuperado es igual", registroRecuperado.getIdRegistro(), registro.getIdRegistro());
	}
	
	/**
	 * Test para probar guardar (Post) el repositorio extendido de registro.
	 */
	@Test
	public void validarGuardarRegistro() {
		// Arrange
		//Mockito.when(emailService.sendEmail(Mockito.anyString())).thenReturn("hola mundo");
		Registro registro = new RegistroTestDataBuilder().withIdRegistro("1").build();
		entityManager.persist(registro);
	    entityManager.flush();
		// Act
	    Registro registroGuardado = registroRepositori.save(registro);
		// Assert
		Assert.assertNotNull(registroGuardado);
	}
	
	/**
	 * Test para probar borrar (Delete) un registro del repositorio extendido de
	 * registro.
	 */
	@Test
	public void validarBorrarRegistroById() {
		// Arrange
		// Mockito.when(emailService.sendEmail(Mockito.anyString())).thenReturn("hola
		// mundo");
		boolean flag = false;
		Registro registro = new RegistroTestDataBuilder().withIdRegistro("1").build();
		entityManager.persist(registro);
		entityManager.flush();
		// Act
		try {
			registroRepositori.delete(registro.getIdRegistro());
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		// Assert
		Assert.assertTrue(flag);
	}

}
