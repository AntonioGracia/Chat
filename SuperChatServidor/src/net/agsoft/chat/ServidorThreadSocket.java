
package net.agsoft.chat;

import java.io.IOException;

public class ServidorThreadSocket {

	public static final int PUERTO = 5000;
	
	public static void main(String args[]) {
		
		Servidor servidor = new Servidor(PUERTO);
		Cliente cliente = null;
		String ip = "";
		
		try {
			

			servidor.conectar();
		
			System.out.println("Conectado");
			while (servidor.estaConectado()) {
				cliente = new Cliente(servidor.escuchar(),servidor);
				cliente.setIp(cliente.socket.getInetAddress().getHostAddress());
				ip = cliente.getIp();	
				
				//if(servidor.existeIp(ip)){
				//	cliente.getSalida().println("/server " + ip);
				//	cliente.getSalida().println("/server Ya existe un equipo con esta direccion");
					
				//}
				//else{
				//	cliente.start();
				//}
				cliente.start();
				
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
