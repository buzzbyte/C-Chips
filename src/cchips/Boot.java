package cchips;

import java.io.*;

public class Boot {

	/**
	 * @param args
	 */
	public static String botname = "C-Chips";
	public static String ircserver = "irc.geekshed.net";
	public static String ircchannel = "#tinkernut_test_room";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader reader;
		reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("Enter server to connect to: ");
			ircserver = reader.readLine();
			System.out.print("Enter bot's name: ");
			botname = reader.readLine();
			System.out.print("Enter channel to join: ");
			ircchannel = reader.readLine();
			new Bot();
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			System.out.println("An unexpected error occured:");
			ioe.printStackTrace();
		}
	}

}
