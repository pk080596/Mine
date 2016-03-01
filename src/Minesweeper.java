import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Minesweeper {

	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private ActionListener Mine;
	private ActionListener NotMine;
	private int size = 100;
	private int numberOfMines = 10;

	Minesweeper() {
	}

	public void Sweep() {
		setUp();
	}

	private void setUp() {
		Map<Integer, JToggleButton> allButtons = new HashMap<Integer, JToggleButton>();
		Map<Integer, JToggleButton> allMines = new HashMap<Integer, JToggleButton>();
		Map<JToggleButton, Integer> Buttons = new HashMap<JToggleButton, Integer>();

		frame = new JFrame("MineSweeper");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set grid structure
		panel = new JPanel();
		GridLayout grid = new GridLayout(10, 10, 0, 0);
		panel.setLayout(grid);

		// Generate mines
		List<Integer> mines = GenerateMines();
		int iterate = 0;

		// Action if mine is clicked
		Mine = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BufferedImage bomb = ImageIO.read(new File("c:\\workspace\\Mine\\src\\mine.png"));
					JToggleButton button = (JToggleButton) e.getSource();
					Image scaledBomb = bomb.getScaledInstance(button.getWidth() - 10, button.getHeight() - 10,
							Image.SCALE_SMOOTH);
					button.setIcon(new ImageIcon(scaledBomb));
					button.setFocusPainted(false);
				} catch (IOException ex) {
				}
				Object[] option = { "Restart?" };
				JPanel gameover = new JPanel();
				JLabel gg = new JLabel("Game Over");
				gameover.add(gg);
				int result = JOptionPane.showOptionDialog(null, gameover, "Game Over", JOptionPane.YES_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, option, null);
				if (result == JOptionPane.YES_OPTION) {
					frame.setVisible(false);
					frame.dispose();
					Sweep();
				}
			}
		};

		// Action if mine is not clicked
		NotMine = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int surrounding;
				List<JToggleButton> surroundingButtons = new LinkedList<JToggleButton>();
				JToggleButton button = (JToggleButton) e.getSource();
				button.setFocusPainted(false);
				int index = getButtonIndex(Buttons, button);

				surrounding = countMines(allMines, index);
				if (surrounding == 0) {
					surroundingButtons = getSurroundingButtons(allButtons, index, button);
				}
				if (!surroundingButtons.isEmpty()){
				spreadOut(allMines, Buttons, allButtons, surroundingButtons);
				}
				else {
					button.setText(String.valueOf(surrounding));
				}
			}
		};

		// Create buttons
		for (int i = 0; i < size; i++) {
			JToggleButton button;
			button = new JToggleButton("");
			if (i == mines.get(iterate) && iterate < numberOfMines) {
				button.addActionListener(Mine);
				allMines.put(i, button);
				if (iterate != numberOfMines - 1) {
					iterate++;
				}
			} else {
				button.addActionListener(NotMine);
			}
			panel.add(button);
			allButtons.put(i, button);
			Buttons.put(button, i);
		}

		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setSize(500, 500);
		frame.setResizable(false);

	}

	private List<Integer> GenerateMines() {
		Random random = new Random();
		List<Integer> mines = new LinkedList<Integer>();

		// Generate mines until 10 unique mines are generated
		while (mines.size() < numberOfMines) {
			int mine = random.nextInt(size - 1);
			if (!mines.contains(mine)) {
				mines.add(mine);
			}
		}
		Collections.sort(mines);
		return mines;
	}

	private int countMines(Map<Integer, JToggleButton> mines, int mineIndex) {
		int mine = 0;
		if (mines.containsKey(mineIndex - 11)) {
			mine++;
		}
		if (mines.containsKey(mineIndex - 10)) {
			mine++;
		}
		if (mines.containsKey(mineIndex - 9)) {
			mine++;
		}
		if (mines.containsKey(mineIndex - 1)) {
			mine++;
		}
		if (mines.containsKey(mineIndex + 1)) {
			mine++;
		}
		if (mines.containsKey(mineIndex + 9)) {
			mine++;
		}
		if (mines.containsKey(mineIndex + 10)) {
			mine++;
		}
		if (mines.containsKey(mineIndex + 11)) {
			mine++;
		}
		return mine;
	}

	private List<JToggleButton> getSurroundingButtons(Map<Integer, JToggleButton> allButtons, int buttonIndex,
			JToggleButton button) {
		List<JToggleButton> surroundingList = new LinkedList<JToggleButton>();
		int horizontal = 0;
		int vertical = 0;

		if (buttonIndex % 10 == 9) {
			horizontal = 2;
		} else if (buttonIndex % 10 == 0) {
			horizontal = 1;
		}
		if (buttonIndex <= 9) {
			vertical = 1;
		} else if (buttonIndex >= size - 10) {
			vertical = 2;
		}
		if (!(vertical == 1) && !(horizontal == 1) && !allButtons.get(buttonIndex - 11).isSelected()) {
			surroundingList.add(allButtons.get(buttonIndex - 11));
		}
		if (!(vertical == 1) && !allButtons.get(buttonIndex - 10).isSelected()) {
			surroundingList.add(allButtons.get(buttonIndex - 10));
		}
		if (!(vertical == 1) && !(horizontal == 2) && !allButtons.get(buttonIndex - 9).isSelected()) {
			surroundingList.add(allButtons.get(buttonIndex - 9));
		}
		if (!(horizontal == 1) && !allButtons.get(buttonIndex - 1).isSelected()) {
			surroundingList.add(allButtons.get(buttonIndex - 1));
		}
		if (!(horizontal == 2) && !allButtons.get(buttonIndex + 1).isSelected()) {
			surroundingList.add(allButtons.get(buttonIndex + 1));
		}
		if (!(vertical == 2) && !(horizontal == 1) && !allButtons.get(buttonIndex + 9).isSelected()) {
			surroundingList.add(allButtons.get(buttonIndex + 9));
		}
		if (!(vertical == 2) && !allButtons.get(buttonIndex + 10).isSelected()) {
			surroundingList.add(allButtons.get(buttonIndex + 10));
		}
		if (!(vertical == 2) && !(horizontal == 2) && !allButtons.get(buttonIndex + 11).isSelected()) {
			surroundingList.add(allButtons.get(buttonIndex + 11));
		}
		return surroundingList;
	}

	private void spreadOut(Map<Integer, JToggleButton> Bomb, Map<JToggleButton, Integer> Buttons,
			Map<Integer, JToggleButton> allButtons, List<JToggleButton> surroundingButtons) {

		for (JToggleButton toggle : surroundingButtons) {
			List<JToggleButton> spread = new LinkedList<JToggleButton>();
			toggle.setSelected(true);
			int index = getButtonIndex(Buttons, toggle);
			int mineNumber = countMines(Bomb, index);
			if (mineNumber == 0) {
				spread = getSurroundingButtons(allButtons, index, toggle);
				spreadOut(Bomb, Buttons, allButtons, spread);
			}
			else {
				toggle.setText(String.valueOf(mineNumber));
			}
		}
	}

	private int getButtonIndex(Map<JToggleButton, Integer> Buttons, JToggleButton button) {
		return Buttons.get(button);
	}

}
