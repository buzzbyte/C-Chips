package cchips;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import jerklib.*;
import jerklib.events.IRCEvent.*;
import jerklib.events.*;
import jerklib.listeners.*;

public class Bot implements IRCEventListener {
	
	private ConnectionManager manager;
	public static final String version = "1.0";
	private BufferedWriter Writer;
	private BufferedReader Reader;
	public static String botname = "C-Chips";
	public static String ircserver = "irc.geekshed.net";
	public static String ircchannel = "#tinkernut_test_room";
	 
	public Bot()
	{
		File configFile = new File("config.txt");
		try {
			if (configFile.exists()) {
				Reader = new BufferedReader(new FileReader(configFile));
				ircserver = Reader.readLine();
				botname = Reader.readLine();
				ircchannel = Reader.readLine();
			} else {
				Reader = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Enter server to connect to: ");
				ircserver = Reader.readLine();
				System.out.print("Enter bot's name: ");
				botname = Reader.readLine();
				System.out.print("Enter channel to join: ");
				ircchannel = Reader.readLine();
				configFile.createNewFile();
				Writer = new BufferedWriter(new FileWriter(configFile));
				Writer.write(ircserver);
				Writer.newLine();
				Writer.write(botname);
				Writer.newLine();
				Writer.write(ircchannel);
				Writer.close();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		manager = new ConnectionManager(new Profile(botname));
		Session session = manager.requestConnection(ircserver);
		session.addIRCEventListener(this);
	}
	
	@Override
	public void receiveEvent(IRCEvent e) {
		// TODO Auto-generated method stub
		if (e.getType() == Type.CONNECT_COMPLETE)
		{
			e.getSession().join(ircchannel);
		}
		else if (e.getType() == Type.CHANNEL_MESSAGE)
		{
			MessageEvent me = (MessageEvent) e;
			System.out.println("<" + me.getNick() + ">"+ ":" + me.getMessage());
		}
		else if (e.getType() == Type.JOIN_COMPLETE)
		{
			JoinCompleteEvent jce = (JoinCompleteEvent) e;
			if (botname == "C-Chips") {
				jce.getChannel().say("Running version " + version);
			} else {
				jce.getChannel().say("C-Chips IRC bot running version " + version);
			}
		}
		else
		{
			System.out.println(e.getType() + " " + e.getRawEventData());
		}
	}
}
