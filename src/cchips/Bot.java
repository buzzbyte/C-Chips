package cchips;

import jerklib.*;
import jerklib.events.IRCEvent.*;
import jerklib.events.*;
import jerklib.listeners.*;

public class Bot implements IRCEventListener {
	
	private ConnectionManager manager;
	public static final String version = "1.0";
	 
	public Bot()
	{
		manager = new ConnectionManager(new Profile(Boot.botname));
		Session session = manager.requestConnection(Boot.ircserver);
		session.addIRCEventListener(this);
	}
	
	@Override
	public void receiveEvent(IRCEvent e) {
		// TODO Auto-generated method stub
		if (e.getType() == Type.CONNECT_COMPLETE)
		{
			e.getSession().join(Boot.ircchannel);
		}
		else if (e.getType() == Type.CHANNEL_MESSAGE)
		{
			MessageEvent me = (MessageEvent) e;
			System.out.println("<" + me.getNick() + ">"+ ":" + me.getMessage());
		}
		else if (e.getType() == Type.JOIN_COMPLETE)
		{
			JoinCompleteEvent jce = (JoinCompleteEvent) e;
			if (Boot.botname == "C-Chips") {
				jce.getChannel().say("C-Chips, running version " + version);
			} else {
				jce.getChannel().say(Boot.botname + ", a C-Chips IRC bot running version " + version);
			}
		}
		else
		{
			System.out.println(e.getType() + " " + e.getRawEventData());
		}
	}
}
