
import javax.swing.JFrame;

public class Window extends JFrame{
	private int width, height;
	private String title;
	public Window(int width, int height, String title, Game game) {
		this.setSize(width, height);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.add(game);
	}
}
