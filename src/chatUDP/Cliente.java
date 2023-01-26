package chatUDP;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Cliente {

	static int puerto = 6789;
	static String ip = "localhost";
	public static DatagramSocket socketUDP;

	public static void main(String args[]) {

		try {
			socketUDP = new DatagramSocket();
			(new Escritor()).start();
			(new Lector()).start();
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static class Lector extends Thread {

		public void run() {
			while (true)
			{
				
				try {
					byte[] bufer = new byte[1000];
					DatagramPacket respuesta = new DatagramPacket(bufer, bufer.length);
	
					Cliente.socketUDP.receive(respuesta);
	
					// Enviamos la respuesta del servidor a la salida estandar
					System.out.println("Respuesta: " + new String(respuesta.getData()));
	
					// Cerramos el socket
					//socketUDP.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public static class Escritor extends Thread {

		public void run() {
			while (true)
			{
					
				try {
	
					System.out.print("Escribe:");
					Scanner entrada = new Scanner(System.in);
	
					byte[] mensaje = entrada.next().getBytes();
					InetAddress hostServidor = InetAddress.getByName(Cliente.ip);
					DatagramPacket peticion = new DatagramPacket(mensaje, mensaje.length, hostServidor, puerto);
					Cliente.socketUDP.send(peticion);
	
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}}}
	
	
