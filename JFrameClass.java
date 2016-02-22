import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class JFrameClass extends JFrame {
	
	
	private SnakeCanvas c;

	
	public JFrameClass(){
		c = new SnakeCanvas();
		c.setSize(400,300);
		add(c);
		setVisible(true);
		c.setBackground(Color.BLACK);
		this.addKeyListener(c);
}
}