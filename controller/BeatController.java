package controller;

import model.BeatModelInterface;
import view.BeatBPMView;

public class BeatController implements ControllerInterface {
	BeatModelInterface model;
	BeatBPMView view;

	public BeatController(BeatModelInterface model){
		this.model = model;
		this.view = new BeatBPMView(this, model);
		view.creatView();
		view.createControls();
		view.disableStopMenuItem();
		view.enableStartMenuItem();
		model.initialize();
	}

	@Override
	public void start() {
		model.on();
		view.disableStartMenuItem();
		view.enableStopMenuItem();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void increaseBPM() {
		// TODO Auto-generated method stub

	}

	@Override
	public void decreaseBPM() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBPM(int bpm) {
		// TODO Auto-generated method stub

	}
	
}
