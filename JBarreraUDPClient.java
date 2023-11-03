
/****************************************************************************************************
 ** Jose Barrera																			       **
 ** COSC 4360																					   **
 ** 10-30-2023																					   **
 ** Purpose: Modify the UDP Client code so that the client is able to send a string of sentences   ** 
 ** in the command line. This is then sent the server, and returned from the server. Output should **
 ** be the length of the statement in bits, the RTT in ms, and the throughput in Mbps.			   ** 
 ****************************************************************************************************/

import java.net.*;
import java.io.*;

/**
 * Client class used to send a message to the Server.
 */
public class JBarreraUDPClient {

	/**
	 * Entrance to the program.
	 * 
	 * @param args Used to read in text within quotes.
	 */
	public static void main(String[] args) {

		// Fields
		/** The name of the UDPServer. */
		String serverName = "localhost";
		// String serverName = "192.168.1.152";
		/** Tne port number for the UDPServer. */
		int port = 10999;
		/** The text inputted by the user. */
		String statement = String.join(" ", args);
		// String statement = args[0];

		// Note: The Datagram Socket closes after the try.
		try (DatagramSocket clientSocket = new DatagramSocket()) {
			InetAddress IPAddress = InetAddress.getByName(serverName);

			// Start time
			long startTime = System.currentTimeMillis();

			// Sending the data to the Server.
			byte[] sendData = statement.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			clientSocket.send(sendPacket);

			// Receiving data from the Server.
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);

			// End time
			long endTime = System.currentTimeMillis();

			// RTT
			long longRTTms = endTime - startTime;

			// Convert RTT to seconds for throughput calculation
			double RTTSeconds = longRTTms / 1000.0; // the math was not mathing

			String receivedStatement = new String(receivePacket.getData()).trim(); // add .trim

			// Print out whether or not the data is recieved or not.
			if (!statement.equals(receivedStatement)) {
				System.out.println(
						"\n" + "*************************************************************************************");
				System.out.println("Received data does not match sent data.");
				System.out.println(
						"*************************************************************************************" + "\n");
			} else {
				int statementLength = statement.length() * 8;
				double throughput = (statementLength / (RTTSeconds / 2) / 1000000.0);
				System.out.println(
						"\n" + "*************************************************************************************");
				System.out.println("Statement Length: " + statementLength + " bits");
				System.out.println("RTT: " + longRTTms + "ms");
				System.out.printf("Throughput: %.2f Mbps%n", throughput);
				System.out.println(
						"*************************************************************************************" + "\n");
			}

			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
