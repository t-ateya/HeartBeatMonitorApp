package model;
import view.*;

import java.util.ArrayList;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

public class Beat implements BeatInterface, MetaEventListener {
	Sequencer sequencer ;

	ArrayList<BeatObserver> beatObservers = new ArrayList<>();
	ArrayList<BPMObserver> bmpObservers = new ArrayList<BPMObserver>();

	int bmp = 90;
	Sequence sequence;
	Track track;

	@Override
	public void meta(MetaMessage meta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize() {
		setUpMidi();
		buildTrackAndStart();

	}

	@Override
	public void on() {
		System.out.println("Starting the sequencer");
		sequencer.start();
		setBPM(90);

	}

	@Override
	public void off() {
		setBPM(0);
		sequencer.stop();

	}

	@Override
	public void setBPM(int bpm) {
		this.bmp = bmp;
		sequencer.setTempoInBPM(getBPM());
		notifyBPMObservers();

	}

	@Override
	public int getBPM() {
		return bmp;
	}

	@Override
	public void registerObserver(BeatObserver o) {
		

	}

	@Override
	public void removeObserver(BeatObserver o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerObserver(BPMObserver o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeObserver(BPMObserver o) {
		// TODO Auto-generated method stub

	}

	void beatEvent(){
		notifyBeatObservers();
	}
	

	
}
