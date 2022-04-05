package game.brickBreaker;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	public final static int WIDTH = 700;
	public final static int HEIGHT = 600;

	GameFrame(String title) {
		frameSetup(title);
		this.add(new GameLogic());
		this.setVisible(true);
	}
	
	private void frameSetup(String title) {
		this.setTitle(title);
		this.setSize(700, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
