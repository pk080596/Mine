import javax.swing.*;

import java.util.*;

import java.awt.GridLayout;
import java.awt.event.*;

public class Minesweeper {

	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private JButton button;
	private ActionListener Mine;
	private ActionListener NotMine;

	Minesweeper() {
	}

	public void Sweep() {
		setUp();
	}

	private void setUp() {
		frame = new JFrame("MineSweeper");
		frame.setVisible(true);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setLayout(new GridLayout(10, 10));

		// Action if mine is clicked
		Mine = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(panel, "This is a mine");
			}
		};

		// Action if mine is not clicked
		NotMine = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(panel, "This is not a mine");
			}
		};

		// Generate mines
		List<Integer> mines = GenerateMines();
		int iterate = 0;

		// Create buttons
		for (int i = 0; i < 100; i++) {
			button = new JButton(" ");
			if (i == mines.get(iterate) && iterate < 10) {
				button.addActionListener(Mine);
				if (iterate != 9) {
					iterate++;
				}
			} else {
				button.addActionListener(NotMine);
			}
			panel.add(button);
		}

		frame.add(panel);
	}

	private List<Integer> GenerateMines() {
		Random random = new Random();
		List<Integer> mines = new LinkedList<Integer>();

		// Generate mines until 10 unique mines are generated
		while (mines.size() < 10) {
			int mine = random.nextInt(99);
			if (!mines.contains(mine)) {
				mines.add(mine);
			}
		}
		Collections.sort(mines);
		return mines;
	}

}
