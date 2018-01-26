package com.ceiba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.model.Registro;
import com.ceiba.repository.RegistroRepository;

@Service
public class RegistroServiceImpl implements RegistroService {

	@Autowired
	RegistroRepository registroRepository;
	
	@Override
	public Iterable<Registro> listAllRegistro() {
		return registroRepository.findAll();
	}

	@Override
	public Registro getRegistroById(String idRegistro) {
		return registroRepository.findOne(idRegistro);
	}

	@Override
	public List<Registro> getRegistrosByIdVehiculo(String idVehiculo) {
		return registroRepository.findByidVehiculo(idVehiculo);
	}

	@Override
	public Registro saveRegistro(Registro registro) {
		return registroRepository.save(registro);
	}

	@Override
	public void deleteRegistro(String idRegistro) {
		registroRepository.delete(idRegistro);
	}

	@Override
	public Registro getRegistroByidVehiculoAndEstado(String idVehiculo, String estado) {
		return registroRepository.findByidVehiculoAndEstado(idVehiculo, estado);
	}

}
