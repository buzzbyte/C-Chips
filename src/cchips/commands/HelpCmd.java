package cchips.commands;


import cchips.*;

public class HelpCmd extends Command {

	public void exec() {
		if (params.equalsIgnoreCase("ReadMe")) {
			TextBuffer.addAndDisplay("ReadMe file is found at: http://github.com/buzzbyte/C-Chips#c-chips-irc-bot", me);
		} else if (params.equalsIgnoreCase("")) {
			TextBuffer.addAndDisplay("Help is found at: http://www.tinkernut.com/wiki/page/C-Chips", me);
		}
	}

	public void execPriv() {
		if (params == " ReadMe") {
			me.getSession().sayPrivate(me.getNick(), getReadMeURL());
		} else if (params == "") {
			me.getSession().sayPrivate(me.getNick(), "Help is found at: http://www.tinkernut.com/wiki/page/C-Chips");
		}
	}
}
