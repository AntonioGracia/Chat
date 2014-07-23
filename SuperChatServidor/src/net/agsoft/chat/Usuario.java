package net.agsoft.chat;

import java.io.Serializable;

public class Usuario implements Serializable {

	public int ID = 0;
	private String nombreUsuario = "" ;
	private String contrasena = "";
	private String nombrepc = "";
	private String ip = "";
	
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getNombrepc() {
		return nombrepc;
	}
	public void setNombrepc(String nombrepc) {
		this.nombrepc = nombrepc;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}


	
	
	
}
