import javax.swing.*;
import java.awt.event.*;
public class Minesweeper {

	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private JButton button;
	private ActionListener Mine;
	
	Minesweeper(){
	}
	
	public void Sweep(){
		setUp();
	}
	
	private void setUp(){
		frame = new JFrame("MineSweeper");
		frame.setVisible(true);
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		label = new JLabel("Minsweeper");
		label.setBounds(200,0,100,20);
		panel.add(label);
		
		Mine = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(panel, "This is a mine");
			}
		};
		
		for(int i = 0; i < 100; i++){
			button = new JButton(" ");
			button.addActionListener(Mine);
			button.setBounds(0+(i*45),45,45,45);
			panel.add(button);
		}
		
		frame.add(panel);
	}
	
}
