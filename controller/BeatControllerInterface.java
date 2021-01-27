package controller;

public interface BeatControllerInterface  {
	void start();
	void stop();
	void increaseBPM();
	void decreaseBPM();
	void setBPM(int bpm);
	
}
