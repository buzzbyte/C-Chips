package cchips;

import jerklib.events.MessageEvent;

public class TextBuffer {
	//TODO: Overload display() function as to take a User, as to display private messages.

	private static String tBuffer = "";

	public static final void add(final String text) {
		tBuffer += text;
	}
	public static final void addAndDisplay(final String text, MessageEvent me) {
		tBuffer += text;
		display(me);
	}
	public static final void addArray(final String[] textArray) {
		for (int i = 0; i < textArray.length; i++) {
			tBuffer += textArray[i] + " ";
		}
	}
	public static final void display(final MessageEvent me) {
		me.getChannel().say(tBuffer);
		tBuffer = "";
	}
}