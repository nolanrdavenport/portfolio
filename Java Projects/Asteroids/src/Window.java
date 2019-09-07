import javax.swing.JFrame;

public class Window extends JFrame{
	int w, h;
	public Window(int w, int h) {
		this.w = w;
		this.h = h;
		
		setSize(w, h);
		setLocationRelativeTo(null);
		setVisible(true);
		setAlwaysOnTop(true);
		setAlwaysOnTop(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
}
