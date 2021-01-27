package model;


import view.BPMObserver;
import view.BeatObserver;

public interface BeatInterface {
	void initialize();

	void on();

	void off();

	void setBPM(int bpm); // BMP ===Beats Per Minute

	int getBPM();

	void registerObserver(BeatObserver o);

	void removeObserver(BeatObserver o);

	void registerObserver(BPMObserver o);

	void removeObserver (BPMObserver O);

	


}
