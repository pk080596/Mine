import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Minesweeper {

	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private int size;
	private int length;
	private int numMine;

	Minesweeper() {
		// constructor
	}

	public void Sweep() {
		setUp();
	}

	private void setUp() {
		frame = new JFrame("MineSweeper");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initializeGame();
		
		// Set grid structure
		panel = new JPanel();
		GridLayout grid = new GridLayout(length, length, 0, 0);
		panel.setLayout(grid);

		// Generate mines
		List<Integer> mines = GenerateMines();
		Button button = new Button(length, numMine, mines);
		button.createButton(frame, panel);

		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setSize(length * 50, length * 50 + 50);
		frame.setResizable(false);

	}

	private List<Integer> GenerateMines() {
		Random random = new Random();
		List<Integer> mines = new LinkedList<Integer>();

		// Generate mines until numMine unique mines are generated
		while (mines.size() < numMine) {
			int mine = random.nextInt(size - 1);
			if (!mines.contains(mine)) {
				mines.add(mine);
			}
		}
		Collections.sort(mines);
		return mines;
	}

	private void initializeGame() {
		JPanel initialize = new JPanel();
		JLabel label = new JLabel();
		JTextField text = new JTextField();
		text.setPreferredSize(new Dimension(130, 20));
		label.setText("Enter length of minefield (2-20)");
		initialize.add(label);
		initialize.add(text);

		Object[] option = { "Ok" };
		int ok = JOptionPane.showOptionDialog(null, initialize, "Setup", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, option, null);

		if (ok == 0) {
			length = Integer.valueOf(text.getText());
			if (length <= 20 && length >= 1) {
				size = length * length;
				JPanel initialize_2 = new JPanel();
				label.setText("Enter number of mines (1-99)");
				initialize_2.add(label);
				initialize_2.add(text);
				int ok_2 = JOptionPane.showOptionDialog(null, initialize_2, "Setup", JOptionPane.YES_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, option, null);
				if (ok_2 == 0) {
					int mine = Integer.valueOf(text.getText());
					if (mine <= 99 && mine >= 1) {
						numMine = mine;
					}
				} else {
					initializeGame();
				}
			} else {
				initializeGame();
			}
		}
	}

}
