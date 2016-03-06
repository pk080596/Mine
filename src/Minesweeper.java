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
	}

	public void setup() {
		initialize();
	}

	private void initialize() {
		initializeGame();
		frame = new JFrame("MineSweeper");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set grid structure
		panel = new JPanel();
		GridLayout grid = new GridLayout(length, length, 0, 0);
		panel.setPreferredSize(new Dimension(length * 50, length * 50));
		panel.setLayout(grid);

		// Generate mines
		List<Integer> mines = GenerateMines();
		Button button = new Button(length, numMine, mines);
		button.createButton(frame, panel);

		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.pack();
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
		label.setText("Enter length of minefield (2-10)");
		initialize.add(label);
		initialize.add(text);
		Object[] option = { "Ok" };

		initializeSize(initialize, text, option);
		if (size == 0) {
			System.exit(0);
		}

		JPanel initialize_2 = new JPanel();
		label.setText("Enter number of mines (1-99)");
		initialize_2.add(label);
		initialize_2.add(text);
		initializeMine(initialize_2, text, option);
		if (numMine == 0) {
			System.exit(0);
		}

	}

	private void initializeSize(JPanel panel, JTextField textfield, Object[] option) {
		textfield.setText(null);
		int ok = JOptionPane.showOptionDialog(null, panel, "Setup", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, option, null);

		if (ok == 0) {
			if (textfield.getText().isEmpty()) {
				initializeSize(panel, textfield, option);
			} else {
				length = Integer.valueOf(textfield.getText());
				if (length <= 10 && length > 1) {
					size = length * length;
				} else {
					initializeSize(panel, textfield, option);
				}
			}
		}
	}

	private void initializeMine(JPanel panel, JTextField textfield, Object[] option) {
		textfield.setText(null);
		int ok_2 = JOptionPane.showOptionDialog(null, panel, "Setup", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, option, null);

		if (ok_2 == 0) {
			if (textfield.getText().isEmpty()) {
				initializeMine(panel, textfield, option);
			} else {
				int mine = Integer.valueOf(textfield.getText());
				if (mine <= 99 && mine >= 1) {
					numMine = mine;
				} else {
					initializeMine(panel, textfield, option);
				}
			}
		}
	}

}
