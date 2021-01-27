import controller.BeatController;
import controller.ControllerInterface;
import model.BeatModel;
import model.BeatModelInterface;

public class Main {
	public static void main(String [] args ){
		BeatModelInterface model = new BeatModel();
		ControllerInterface controller = new BeatController(model);
		
	}

}