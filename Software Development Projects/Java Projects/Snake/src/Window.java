

import javax.swing.*;
public class Window extends JFrame{
	int w, h;
	public Window(int w, int h) {
		this.w = w;
		this.h = h;
		
		setSize(w, h);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
}
