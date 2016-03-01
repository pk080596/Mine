import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import java.awt.BorderLayout;
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
	private int size = 100;
	private int numMine = 10;

	Minesweeper() {
	}

	public void Sweep() {
		setUp();
	}

	private void setUp() {
		frame = new JFrame("MineSweeper");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set grid structure
		panel = new JPanel();
		GridLayout grid = new GridLayout(10, 10, 0, 0);
		panel.setLayout(grid);

		// Generate mines
		List<Integer> mines = GenerateMines();
		
		Button button = new Button(size, numMine, mines);
		button.createButton(frame, panel);

		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setSize(500, 500);
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

}
