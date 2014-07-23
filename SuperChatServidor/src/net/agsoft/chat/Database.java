package net.agsoft.chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Database {
	private static Connection conexion;
	
	
	public Database(Connection conexion) {
		this.conexion = conexion;
	}
	
	
	public void nuevo(Usuario usuario) throws SQLException {
		PreparedStatement sentencia = null;
		String sentenciaSql = "INSERT INTO " + Constantes.TABLA + " ("  + Constantes.NOMBREPC + ", " + Constantes.IP + ", "
				+ Constantes.NOMBRE + ", " + Constantes.CONTRASENA + ") VALUES (?, ?, ?, ?)";
		sentencia = conexion.prepareStatement(sentenciaSql);
		sentencia.setString(1, usuario.getNombrepc());
		sentencia.setString(2, usuario.getIp());
		sentencia.setString(3, usuario.getNombreUsuario());
		sentencia.setString(4, usuario.getContrasena());
		sentencia.executeUpdate();
		
		if (sentencia != null)
			sentencia.close();
	}
	

	


	
	
	
	
}
