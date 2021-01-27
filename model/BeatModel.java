package model;
import view.*;

import java.util.ArrayList;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.*;

public class BeatModel implements BeatModelInterface, MetaEventListener {
	Sequencer sequencer ;

	ArrayList<BeatObserver> beatObservers = new ArrayList<>();
	ArrayList<BPMObserver> bmpObservers = new ArrayList<BPMObserver>();

	int bmp = 90;
	Sequence sequence;
	Track track;

	@Override
	public void meta(MetaMessage message) {
		if (message.getType() == 47) {
			beatEvent();
			sequencer.start();
			setBPM(getBPM());
		}
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
		notifyBMPObservers();

	}

	@Override
	public int getBPM() {
		return bmp;
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
		bmpObservers.add(o);

	}

	@Override
	public void removeObserver(BPMObserver o) {
	int index = bmpObservers.indexOf(o);
	bmpObservers.remove(index);

	}

	void beatEvent(){
		notifyBeatObservers();
	}

	public void notifyBeatObservers(){
		for (int i = 0; i< beatObservers.size(); i++){
			BeatObserver observer = (BeatObserver)beatObservers.get(i);
			observer.updateBeat();
		}
	}
	
	public void notifyBMPObservers(){
		for (int i = 0; i< bmpObservers.size(); i++){
			BPMObserver observer = (BPMObserver)bmpObservers.get(i);
			observer.updateBMP();
		}
	}

	public void setUpMidi(){
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addMetaEventListener(this);
			sequence = new Sequence(Sequence.PPQ, 4);
			sequencer.setTempoInBPM(getBPM());
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);

		}catch (Exception ex){
			ex.printStackTrace();
		}

	}

	public void buildTrackAndStart(){
		int [] trackList = {35, 0, 46, 0};

		sequence.deleteTrack(null);
		track = sequence.createTrack();

		makeTracks(trackList);
		track.add(makeEvent(192, 9, 1, 0, 4));
		try {
			sequencer.setSequence(sequence);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void makeTracks(int [] list){
		for (int i = 0; i<list.length; i++){
			int key = list[i];

			if (key != 0){
				track.add(makeEvent(144, 9, key, 100, i));
				track.add(makeEvent(128, 9, key, 100, i+1));
			}
		}
	}

	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick){
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return event;
	}

}
