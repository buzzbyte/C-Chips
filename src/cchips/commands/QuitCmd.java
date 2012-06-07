package cchips.commands;

import jerklib.events.MessageEvent;

import cchips.*;

public class QuitCmd extends Command {

	Bot bot;

	public void init(String params, MessageEvent me, Bot bot) {
		super.init(params, me, bot);
		this.bot = bot;
	}
	
	@Override
	public void exec() {
		// TODO Auto-generated method stub
		bot.con.quit();
		System.exit(1);
	}

	@Override
	public void execPriv() {
		// TODO Auto-generated method stub
		exec();
	}

}
