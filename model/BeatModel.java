package model;
import view.*;

import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import javax.sound.sampled.Line;


public class BeatModel implements BeatModelInterface, Runnable {

	ArrayList<BeatObserver> beatObservers = new ArrayList<>();
	ArrayList<BPMObserver> bpmObservers = new ArrayList<BPMObserver>();

	int bpm = 90;
	Thread thread;
	
	boolean stop = false;
	Clip clip;

	@Override
	public void initialize() {
		
		try {
			File resource = new File("clap.wav");
			clip =(Clip)AudioSystem.getLine(new Line.Info(Clip.class));
			clip.open(AudioSystem.getAudioInputStream(resource));
		} catch (Exception e) {
			System.out.println("Error: Can't load Clip");
			System.out.println(e);
			
		}
		

	}


	@Override
	public void on() {
		bpm = 90;
		
		thread = new Thread((Runnable) this);
		stop = false;
		thread.start();

	}

	@Override
	public void off() {
		stopBeat();

	}

	@Override
	public void setBPM(int bpm) {
		this.bpm = bpm;
		notifyBMPObservers();

	}

	@Override
	public int getBPM() {
		return bpm;
	}

	@Override
	public void registerObserver(BeatObserver o) {
		beatObservers.add(o);

	}

	@Override
	public void removeObserver(BeatObserver o) {
		int i = beatObservers.indexOf(o);
		if (i>=0){
			beatObservers.remove(i);
		}

	}

	@Override
	public void registerObserver(BPMObserver o) {
		bpmObservers.add(o);

	}

	@Override
	public void removeObserver(BPMObserver o) {
		int i = bpmObservers.indexOf(o);
		if (i>=0){
			bpmObservers.remove(i);
		}

	}
	public void notifyBeatObservers(){
		for (int i = 0; i< beatObservers.size(); i++){
			BeatObserver observer = (BeatObserver)beatObservers.get(i);
			observer.updateBeat();
		}
	}
	
	public void notifyBMPObservers(){
		for (int i = 0; i< bpmObservers.size(); i++){
			BPMObserver observer = (BPMObserver)bpmObservers.get(i);
			observer.updateBMP();
		}
	}

	
	public void playBeat(){
		clip.setFramePosition(0);
		clip.start();
	}

	public void stopBeat(){
		clip.setFramePosition(0);
		clip.stop();
	}

	@Override
	public void run() {
		while (!stop){
			playBeat();
			notifyBMPObservers();
			try {
				Thread.sleep(60000/getBPM());
			} catch (Exception e) {}
		}

	}

}
