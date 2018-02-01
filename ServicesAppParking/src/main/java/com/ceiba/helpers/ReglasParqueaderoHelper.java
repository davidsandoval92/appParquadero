package com.ceiba.helpers;

import java.util.Calendar;
import java.util.Date;

import com.ceiba.model.AppParametro;
import com.ceiba.model.Vehiculo;
import com.ceiba.service.AppParametrosService;

public class ReglasParqueaderoHelper {

	/**
	 * Metodo que valida la regla de negocio tipo de vehiculo permitidos en le
	 * parqueadero
	 * 
	 * @param vehiculo
	 * @return
	 */
	public boolean validarTipoVehiculo(Vehiculo vehiculo) {
		boolean flag = false;
		if (vehiculo.getTipoVehiculo().equals("carro") || vehiculo.getTipoVehiculo().equals("moto"))
			flag = true;
		else
			flag = false;
		return flag;
	}

	/**
	 * Metodo que valida la regla de negocio sobre la disponibilidad del
	 * parqueadero segun el tipo de vehiculo.
	 * 
	 * @param cantidad
	 * @param tipovehiculo
	 * @return
	 */
	public boolean validarDisponibilidadTipoVehiculo(int cantidad, String tipovehiculo) {

		boolean flag = false;

		if (cantidad < 20 && tipovehiculo.equals("carro")) {
			flag = true;
		} else if (cantidad < 10 && tipovehiculo.equals("moto")) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * Metodo que valida la regla de negocio sobre los dias permitidos para las
	 * placas que comienzan por la letra A.
	 * 
	 * @param placa
	 * @return
	 */
	public boolean validarPlacaLunesDomingos(String placa) {

		boolean flag = true;
		String letraUp = placa.toUpperCase().substring(0, 1);
		if (letraUp.equals("A")) {
			Calendar fechaPrestamo = Calendar.getInstance();
			fechaPrestamo.setTime(new Date());
			if (fechaPrestamo.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
					|| fechaPrestamo.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				flag = false;
			} else {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * Metodo que valida la regla de negocio sobre el cobro y valor de la
	 * fraccion segun el tipo de vehiculo y el cilindaje para motos.
	 * 
	 * @param fechaIngreso
	 * @param fechaSalida
	 * @param tipoVehiculo
	 * @param cilindraje
	 * @param appParametrosService
	 * @return
	 */
	public int procesarCobro(Date fechaIngreso, Date fechaSalida, String tipoVehiculo, int cilindraje,
			AppParametrosService appParametrosService) {

		int precioPagar = 0;
		int valorHoraMoto = 0;
		int valorDiaMoto = 0;
		int valorHoraCarro = 0;
		int valorDiaCarro = 0;
		int valorCilindajeMax = 0;
		int cilindrajeMaxMoto = 0;

		int diferencia = (int) ((fechaSalida.getTime() - fechaIngreso.getTime()) / 1000);

		int dias = 0;
		int horas = 0;

		if (diferencia > 32400) {
			dias = (int) Math.floor((double) diferencia / 32400);
			diferencia = diferencia - (dias * 32400);
		}
		if (diferencia > 3600) {
			horas = (int) Math.floor((double) diferencia / 3600);
		}

		if (tipoVehiculo.equals("moto")) {
			if (dias > 0) {
				AppParametro appParametro = appParametrosService.getParametroByParametro("ValorDiaMoto");
				valorDiaMoto = Integer.parseInt(appParametro.getValor());
				precioPagar = (dias * valorDiaMoto);
			}
			if (horas > 0) {
				AppParametro appParametro = appParametrosService.getParametroByParametro("ValorHoraMoto");
				valorHoraMoto = Integer.parseInt(appParametro.getValor());
				precioPagar = precioPagar + (horas * valorHoraMoto);
			}
			AppParametro appParametroC = appParametrosService.getParametroByParametro("CilindrajeMaxMoto");
			cilindrajeMaxMoto = Integer.parseInt(appParametroC.getValor());
			if (cilindraje > cilindrajeMaxMoto) {
				AppParametro appParametro = appParametrosService.getParametroByParametro("ValorCilindrajeMax");
				valorCilindajeMax = Integer.parseInt(appParametro.getValor());
				precioPagar = precioPagar + valorCilindajeMax;
			}
		} else {

			if (dias > 0) {
				AppParametro appParametro = appParametrosService.getParametroByParametro("ValorDiaCarro");
				valorDiaCarro = Integer.parseInt(appParametro.getValor());
				precioPagar = (dias * valorDiaCarro);

			}
			if (horas > 0) {
				AppParametro appParametro = appParametrosService.getParametroByParametro("ValorHoraCarro");
				valorHoraCarro = Integer.parseInt(appParametro.getValor());
				precioPagar = precioPagar + (horas * valorHoraCarro);
			}
		}
		return precioPagar;
	}

}