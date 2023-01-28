package chatUDP;

import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;

public class Servidor {
	
	static ArrayList<Integer> puertos;
	
	public static void main (String args[]) {
		
		
	    try {
	
	    	puertos=new ArrayList();
	    	
	    	DatagramSocket socketUDP = new DatagramSocket(6789);
	    	byte[] bufer = new byte[1000];
	      

	      while (true) {
	        // Construimos el DatagramPacket para recibir peticiones
	        DatagramPacket peticion =new DatagramPacket(bufer, bufer.length);

	        // Leemos una petición del DatagramSocket
	        socketUDP.receive(peticion);
	        

	        if(!puertos.contains(peticion.getPort()))
	        	puertos.add(peticion.getPort());
	        
	        
	        
	        /*
	        System.out.print("Datagrama recibido del host: "+peticion.getAddress());
	        System.out.println(" desde el puerto remoto: "+peticion.getPort());
	        */
	        
	        for (int i = 0; i < puertos.size(); i++) {
	        	DatagramPacket respuesta =new DatagramPacket(peticion.getData(), peticion.getLength(),peticion.getAddress(), puertos.get(i));
	        	socketUDP.send(respuesta);
			}
	        
	        for (Integer puerto : puertos) {
	        	//DatagramPacket respuesta =new DatagramPacket(peticion.getData(), peticion.getLength(),direccion, peticion.getPort());
	        	//socketUDP.send(respuesta);
	        	System.out.println(puerto);
	        }
	        
	        

	      
	        
	      }

	    } catch (SocketException e) {
	      System.out.println("Socket: " + e.getMessage());
	    } catch (IOException e) {
	      System.out.println("IO: " + e.getMessage());
	    }
	  }
	
	
	

}
