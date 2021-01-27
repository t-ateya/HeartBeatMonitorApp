package view;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class BeatBar extends JProgressBar implements Runnable {

	//private static final long serialVersionUID = 2L;

	JProgressBar progressBar;
	Thread thread;

	public BeatBar(){
		thread = new Thread(this);
		setMaximum(100);
		thread.start();
	}

	@Override
	public void run() {
		for (;;){
			int value = getValue();
			value = (int)(value * .75);
			setValue(value);
			repaint();
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
}
