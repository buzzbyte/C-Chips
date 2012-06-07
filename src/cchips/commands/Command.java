package cchips.commands;

import cchips.Bot;
import jerklib.events.MessageEvent;


public abstract class Command implements Runnable {
	//TODO: Pass User to constructor, hence finish user tracking!
		protected String params;
		protected MessageEvent me;
		final protected String ReadMeURL = "ReadMe file is found at: http://github.com/buzzbyte/C-Chips#c-chips-irc-bot";
		
		public void init(String params, MessageEvent me, Bot bot) {
			this.params = params;
			this.me = me;
		}
		
		public void initPriv(String params, MessageEvent me, Bot bot) {
			this.params = params;
			this.me = me;
		}
		public void run() {
			if (me.getChannel() == null) {
				execPriv();
			} else {
				exec();
			}
		}
		
		public String getReadMeURL() {
			return ReadMeURL;
		}
		
		public abstract void exec();

		public abstract void execPriv();
}
