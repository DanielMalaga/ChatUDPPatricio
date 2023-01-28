package chatUDP;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chatUDP.Cliente.Lector;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class ClienteGráfico extends JFrame {

	
	public static int puerto = 6789;
	public static String ip = "localhost";
	public static DatagramSocket socketUDP;
	private JPanel contentPane;
	private static JTextField escribir;
	public static JTextArea leer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					
					try {
						socketUDP = new DatagramSocket();
					} catch (SocketException e1) {
						e1.printStackTrace();
					}
					
					ClienteGráfico frame = new ClienteGráfico();
					(new Lector()).start();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClienteGráfico() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 555);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		escribir = new JTextField();
		escribir.setBounds(51, 485, 361, 20);
		contentPane.add(escribir);
		escribir.setColumns(10);
		
		
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				enviarMensaje();
			}
		});
		btnNewButton.setBounds(422, 484, 89, 23);
		contentPane.add(btnNewButton);
		
		escribir.addKeyListener
	      (new KeyAdapter() {
	         public void keyPressed(KeyEvent e) {
	           int key = e.getKeyCode();
	           if (key == KeyEvent.VK_ENTER) {
	        	   enviarMensaje();
	              }
	           }
	         }
	      );

		leer = new JTextArea();
		leer.setEditable(false);
		leer.setBounds(34, 21, 471, 436);
		contentPane.add(leer);
	}
	
	public static void enviarMensaje()
	{
		String env=escribir.getText();
		try {

			byte[] mensaje = env.getBytes();
			InetAddress hostServidor;
			
			hostServidor = InetAddress.getByName(ClienteGráfico.ip);
		
			DatagramPacket peticion = new DatagramPacket(mensaje, mensaje.length, hostServidor, puerto);
			
			ClienteGráfico.socketUDP.send(peticion);
			escribir.setText("");
			
		
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static class Lector extends Thread {

		public void run() {
			while (true)
			{
				
				
				try {
					
					//System.out.println("Escuchando");
					byte[] bufer = new byte[1000];
					DatagramPacket respuesta = new DatagramPacket(bufer, bufer.length);
	
					ClienteGráfico.socketUDP.receive(respuesta);
	
					// Enviamos la respuesta del servidor a la salida estandar
					String txt=leer.getText();
					String nuevoTexto=new String(respuesta.getData());
					
					txt=txt.concat("\n"+nuevoTexto);
					
					
					leer.setText(txt);
					
					
					System.out.println("Respuesta: " + new String(respuesta.getData()));
					
					
	
					// Cerramos el socket
					//socketUDP.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR");
					
					e.printStackTrace();
				}
			}
		}

	}
}
