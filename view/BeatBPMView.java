package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ControllerInterface;
import model.BeatModelInterface;

public class BeatBPMView implements ActionListener, BeatObserver, BPMObserver {
	BeatModelInterface model;
	ControllerInterface controller;

	JFrame viewFrame;
	JPanel viewPanel;
	BeatBar beatBar;
	JLabel bpmOutputLabel;
	JFrame controlFrame;
	JPanel controlPanel;
	JLabel bpmLabel;
	JTextField bpmTextField;
	JButton setBPMButton;
	JButton increaseBPMButton;
	JButton decreaseBPMButton; 
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem startMenuItem;
	JMenuItem stopMenuItem;

	public BeatBPMView(ControllerInterface controller, BeatModelInterface model){
		this.model = model;
		this.controller = controller;
		model.registerObserver((BeatObserver)this);
		model.registerObserver((BPMObserver)this);
	}

	public void createView(){
		//We create all the swing components here
		viewPanel = new JPanel(new GridLayout(1, 2));
		viewFrame = new JFrame("View");
		viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewFrame.setSize(new Dimension(100, 80));
		bpmOutputLabel = new JLabel("offline", SwingConstants.CENTER);
		beatBar = new BeatBar();
		beatBar.setValue(0);
		JPanel bmpJPanel = new JPanel(new GridLayout(2, 1));
		bmpJPanel.add(beatBar);
		bmpJPanel.add(bpmOutputLabel);
		viewPanel.add(bmpJPanel);
		viewFrame.getContentPane().add(viewPanel, BorderLayout.CENTER);
		viewFrame.pack();
		viewFrame.setVisible(true);

	}

public void createControls(){
	//We'll create all swing components here
	JFrame.setDefaultLookAndFeelDecorated(true);
	controlFrame = new JFrame("Control");
	controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	controlFrame.setSize(new Dimension(100, 80));
	
	controlPanel = new JPanel(new GridLayout(1, 2));

	menuBar = new JMenuBar();
	menu = new JMenu("BeatBPM Control");
	startMenuItem = new JMenuItem("Start");
	menu.add(startMenuItem);
	startMenuItem.addActionListener(event->controller.start());

	stopMenuItem = new JMenuItem("Stop");
	menu.add(stopMenuItem);
	stopMenuItem.addActionListener(event->controller.stop());
	
	JMenuItem exit = new JMenuItem("Quit");
	exit.addActionListener(event->System.exit(0));

	menu.add(exit);
	menuBar.add(menu);
	controlFrame.setJMenuBar(menuBar);

	bpmTextField = new JTextField(2);
	bpmLabel = new JLabel("Enter BPM: ", SwingConstants.RIGHT);
	setBPMButton = new JButton("Set");
	setBPMButton.setSize(new Dimension(10, 40));
	increaseBPMButton = new JButton("Inc>>");
	decreaseBPMButton = new JButton("Dec<<");
	setBPMButton.addActionListener(this);
	increaseBPMButton.addActionListener(this);
	decreaseBPMButton.addActionListener(this);

	JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

	buttonPanel.add(decreaseBPMButton);
	buttonPanel.add(increaseBPMButton);

	JPanel enterPanel = new JPanel(new GridLayout(1, 2));
	enterPanel.add(bpmLabel);
	enterPanel.add(bpmTextField);
	JPanel insideControlPanel = new JPanel(new GridLayout(3,1));
	insideControlPanel.add(enterPanel);
	insideControlPanel.add(setBPMButton);
	insideControlPanel.add(buttonPanel);
	controlPanel.add(insideControlPanel);

	bpmLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	bpmOutputLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


	controlFrame.getRootPane().setDefaultButton(setBPMButton);
	controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);

	controlFrame.pack();
	controlFrame.setVisible(true);

}

public void enableStopMenuItem(){
	stopMenuItem.setEnabled(true);
}

public void disableStopMenuItem(){
	stopMenuItem.setEnabled(false);
}

public void enableStartMenuItem(){
	startMenuItem.setEnabled(true);
}

public void disableStartMenuItem(){
	startMenuItem.setEnabled(false);
}

	@Override 
	public void updateBPM() {
		if (model !=null){
			int bpm = model.getBPM();
			if (bpm == 0){
				if (bpmOutputLabel !=null){
					bpmOutputLabel.setText("Offline");
				}
			}else {
				if (bpmOutputLabel !=null){
					bpmOutputLabel.setText("Current BPM: " + model.getBPM());
				}
			}
		}
		
	}

		
	@Override
	public void updateBeat() {
		if (beatBar !=null){
			beatBar.setValue(100);
		}
		

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == setBPMButton){
			int bpm = 90;
			String bpmText = bpmTextField.getText();
			if (bpmText ==null || bpmText.contentEquals("")){
				bpm = 90;
			}else {
				bpm = Integer.parseInt(bpmTextField.getText());
			}
		 	controller.setBPM(bpm);

		}else if (event.getSource() == increaseBPMButton){
			controller.increaseBPM();
		}else if (event.getSource() == decreaseBPMButton){
			controller.decreaseBPM();
		}

	}

	
}
