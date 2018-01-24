package com.ceiba.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the app_log database table.
 * 
 */
@Entity
@Table(name="app_log")
@NamedQuery(name="AppLog.findAll", query="SELECT a FROM AppLog a")
public class AppLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_LOG")
	private String idLog;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Fecha")
	private Date fecha;

	@Column(name="Servicio")
	private String servicio;


	public AppLog() {
		// Constructor vacio.
	}

	public String getIdLog() {
		return this.idLog;
	}

	public void setIdLog(String idLog) {
		this.idLog = idLog;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getServicio() {
		return this.servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

}