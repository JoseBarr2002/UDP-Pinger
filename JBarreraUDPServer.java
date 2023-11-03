
/******************************************************************************************************
 ** Jose Barrera																					 **
 ** COSC 4360																						 **
 ** 10-30-2023																					  	 **
 ** Purpose: Modify the UDP Server code so that the server receives a PING from the client, and then ** 
 ** echo the given statement back to the client. (echo is the pong).								 **
 *****************************************************************************************************/

import java.net.*;
import java.io.*;
import java.util.Arrays;

/**
 * Class that acts as a UDP Server.
 */
public class JBarreraUDPServer {

	/**
	 * Entrance to the program.
	 * 
	 * @param args Not used.
	 */
	public static void main(String[] args) {

		try {
			DatagramSocket serverSocket = new DatagramSocket(10999); // creates a datagram socket and binds it to port
																		// 10999
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];

			while (true) {

				System.out.println("UDP Server waiting for client on port " + serverSocket.getLocalPort() + "...");

				// Receiving data from the Client
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				String receivedSentence = new String(receivePacket.getData()).trim();// add .trim to ignore space in
																						// front or back (face palm)

				// PING
				System.out.println(
						"\n" + "*************************************************************************************");
				System.out.println("RECEIVED: from IPAddress " +
						IPAddress + " and from port " + port + " the data: " + receivedSentence);
				System.out.println(
						"*************************************************************************************" + "\n");
				// Sending data to the Client
				sendData = receivedSentence.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);

				Arrays.fill(receiveData, (byte) 0);
				Arrays.fill(sendData, (byte) 0);
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
