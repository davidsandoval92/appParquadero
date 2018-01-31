package com.ceiba.helpers;

import java.util.Calendar;
import java.util.Date;

import com.ceiba.model.Vehiculo;

public class ParqueaderoHelper {

	public boolean validarTipoVehiculo(Vehiculo vehiculo) {
		boolean flag = false;
		if (vehiculo.getTipoVehiculo().equals("carro") || vehiculo.getTipoVehiculo().equals("moto"))
			flag = true;
		else
			flag = false;
		return flag;
	}

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

	public int procesarCobro(Date fechaIngreso, Date fechaSalida, String tipoVehiculo, int cilindraje) {

		int precioPagar = 0;
		final int valorHoraMoto = 500;
		final int valorDiaMoto = 600;
		final int valorHoraCarro = 1000;
		final int valorDiaCarro = 8000;

		int diferencia = (int) ((fechaSalida.getTime() - fechaIngreso.getTime()) / 1000);

		int dias = 0;
		int horas = 0;

		if (diferencia > 32400) {
			dias = (int) Math.floor((double) diferencia / 32400);
			diferencia = diferencia - (dias * 32400);
		}
		if (diferencia > 3600) {
			horas = (int) Math.floor((double) diferencia / 3600);
			diferencia = diferencia - (horas * 3600);
		}

		if (tipoVehiculo.equals("moto")) {
			if (dias > 0) {
				precioPagar = (dias * valorDiaMoto);
			}
			if (horas > 0) {
				precioPagar = precioPagar + (horas * valorHoraMoto);
			}
			if (cilindraje > 500) {
				precioPagar = precioPagar + 2000;
			}
		} else {

			if (dias > 0) {
				precioPagar = (dias * valorDiaCarro);

			}
			if (horas > 0) {
				precioPagar = precioPagar + (horas * valorHoraCarro);
			}
		}
		return precioPagar;
	}

}