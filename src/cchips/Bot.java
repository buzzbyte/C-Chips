package cchips;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import cchips.commands.Command;

import jerklib.*;
import jerklib.events.IRCEvent.*;
import jerklib.events.*;
import jerklib.listeners.*;

public class Bot implements IRCEventListener {

	private ConnectionManager manager;
	public static final String version = "1.0";
	private BufferedWriter Writer;
	private BufferedReader Reader;
	public String botname = "C-Chips";
	public String ircserver = "irc.geekshed.net";
	public String ircchannel = "#tinkernut_test_room";
	private HashMap<String, Command> commandsMap;
	public String CmdChar = "!";
	public ConnectionManager con;

	public Bot()
	{
		commandsMap = new HashMap<String, Command>();
		File commandsDirectory = new File("src\\cchips\\commands\\");
		if (!commandsDirectory.exists()) {
			System.out.println("There are no commands installed. Create or get some commands and place them in src\\org\\tinkernut\\apririce\\commands\nExiting.");
			commandsDirectory.mkdirs();
			// Exist completely if doesn't exist
			System.exit(1);
		}
		// And if it does exist...
		int numberOfCommands = commandsDirectory.listFiles().length;
		// Each file into commandsFilesArray array
		File commandsFilesArray[] = new File("src\\cchips\\commands\\").listFiles();
		File mainfile = new File("src\\cchips\\");
		String className = "";
		String cmdfile = "";

		for (int i = 0; i < numberOfCommands; i++) {
			try {
				className = commandsFilesArray[i].getName().substring(0, commandsFilesArray[i].getName().indexOf("."));
				//cmdfile = mainfile.getName();
				if (className.equals("Command")) {
					continue;
				}

				// File string to Command type
				@SuppressWarnings("rawtypes")
				Class c = Class.forName("cchips.commands." + className);

				if (className.contains("Cmd")) {          
					commandsMap.put(className.substring(0, className.indexOf("Cmd")).toLowerCase(), (Command) c.newInstance());
				}

			} catch (ClassNotFoundException e) {
				System.out.println("Internal error; class " + className +" not found.");
				e.printStackTrace();
			} catch (InstantiationException e) {
				System.out.println("Internal error; class " + className +" not found.");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.out.println("Internal error; class " + className +" not found.");
				e.printStackTrace();
			} catch (ClassCastException e) {
				System.out.println(className + " does not extend Command. Skipping.");
				continue;
			}
		}

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
			if (me.getMessage().startsWith(CmdChar)) {
				String commandString = stripCommand(me.getMessage());
				if (commandsMap.containsKey(commandString)) {
					commandsMap.get(commandString).init(stripArguments(me.getMessage()), me, this);

					Thread thread = new Thread(commandsMap.get(commandString));
					thread.start();
				} else {
					me.getSession().sayPrivate(me.getNick(), "Not a valid command.");
				}
			}
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
	public static String stripCommand(String cmd) {
		if (cmd.contains(" ")) {
			return cmd.substring(1, cmd.indexOf(" "));
		}
		else {
			return cmd.substring(1);
		}
	}
	public static String stripArguments(String args) {
		if (args.contains(" ")) {
			return args.substring(args.indexOf(" ")+1);
		}
		else {
			return "";
		}
	}
}