package com.ceiba.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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

	public boolean validarDisponibilidadTipoVehiculo(int Cantidad, String tipovehiculo) {

		boolean flag = false;

		if (Cantidad < 20 && tipovehiculo.equals("carro")) {
			flag = true;
		} else if (Cantidad < 10 && tipovehiculo.equals("moto")) {
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

	public int procesarCobroCarro(Date fechaIngreso, Date fechaSalida) throws ParseException {

		int precioPagar = 0;
		final int valorHora = 1000;
		final int valorDia = 8000;
        
 
        int diferencia=(int) ((fechaSalida.getTime()-fechaIngreso.getTime())/1000);
 
        int dias=0;
        int horas=0;
        int minutos=0;
        if(diferencia>86400) {
            dias=(int)Math.floor(diferencia/86400);
            diferencia=diferencia-(dias*86400);
        }
        if(diferencia>3600) {
            horas=(int)Math.floor(diferencia/3600);
            diferencia=diferencia-(horas*3600);
        }
        if(diferencia>60) {
            minutos=(int)Math.floor(diferencia/60);
            diferencia=diferencia-(minutos*60);
        }
        System.out.println("Hay "+dias+" dias, "+horas+" horas, "+minutos+" minutos y "+diferencia+" segundos de diferencia");
    
        if(dias > 0){
        	precioPagar = (dias*valorDia)+(horas*valorHora);
        }else{
        	precioPagar = valorHora * horas;
        }

        return precioPagar;

	}
	
	public int procesarCobroMoto(Date fechaIngreso, Date fechaSalida) throws ParseException {

		int precioPagar = 0;
		final int valorHora = 500;
		final int valorDia = 600;
		final int cilidrajeMas = 2000;
        
 
        int diferencia=(int) ((fechaSalida.getTime()-fechaIngreso.getTime())/1000);
 
        int dias=0;
        int horas=0;
        int minutos=0;
        if(diferencia>86400) {
            dias=(int)Math.floor(diferencia/86400);
            diferencia=diferencia-(dias*86400);
        }
        if(diferencia>3600) {
            horas=(int)Math.floor(diferencia/3600);
            diferencia=diferencia-(horas*3600);
        }
        if(diferencia>60) {
            minutos=(int)Math.floor(diferencia/60);
            diferencia=diferencia-(minutos*60);
        }
        System.out.println("Hay "+dias+" dias, "+horas+" horas, "+minutos+" minutos y "+diferencia+" segundos de diferencia");
    
        if(dias > 0){
        	precioPagar = (dias*valorDia)+(horas*valorHora);
        }else{
        	precioPagar = valorHora * horas;
        }

        return precioPagar;

	}

}