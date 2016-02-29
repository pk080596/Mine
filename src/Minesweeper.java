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
				Object[] option = {"Restart?"};
				JPanel gameover = new JPanel();
				JLabel gg = new JLabel("Game Over");
				gameover.add(gg);
				int result = JOptionPane.showOptionDialog(null, gameover, "Game Over", JOptionPane.YES_OPTION, 
						JOptionPane.PLAIN_MESSAGE, null, option, null);
				if(result == JOptionPane.YES_OPTION){
					frame.setVisible(false);
					frame.dispose();
					Sweep();
				}
			}
		};

		// Action if mine is not clicked
		NotMine = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean surrounding;
				List<JToggleButton> surroundingButtons = new LinkedList<JToggleButton>();
				JToggleButton button = (JToggleButton) e.getSource();
				button.setFocusPainted(false);
				for (int i = 0; i < size; i++) {
					if (button.equals(allButtons.get(i))) {
						surrounding = isFreeNode(allMines, i);
						if (surrounding) {
							surroundingButtons = getSurroundingButtons(allButtons, i);
						}
						break;
					}
				}
				for(JToggleButton toggle : surroundingButtons){
					toggle.setSelected(true);
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

	private Boolean isFreeNode(Map<Integer, JToggleButton> mines, int mineIndex) {
		if (mineIndex % 10 == 9 || mineIndex % 10 == 0 || mineIndex < 9 || mineIndex > size - 9) {
			return false;
		}
		if (mines.containsKey(mineIndex - 11)) {
			return false;
		}
		if (mines.containsKey(mineIndex - 10)) {
			return false;
		}
		if (mines.containsKey(mineIndex - 9)) {
			return false;
		}
		if (mines.containsKey(mineIndex - 1)) {
			return false;
		}
		if (mines.containsKey(mineIndex + 1)) {
			return false;
		}
		if (mines.containsKey(mineIndex + 9)) {
			return false;
		}
		if (mines.containsKey(mineIndex + 10)) {
			return false;
		}
		if (mines.containsKey(mineIndex + 11)) {
			return false;
		}
		return true;
	}

	private List<JToggleButton> getSurroundingButtons(Map<Integer, JToggleButton> allButtons, int buttonIndex) {
		List<JToggleButton> surroundingList = new LinkedList<JToggleButton>();
		surroundingList.add(allButtons.get(buttonIndex - 11));
		surroundingList.add(allButtons.get(buttonIndex - 10));
		surroundingList.add(allButtons.get(buttonIndex - 9));
		surroundingList.add(allButtons.get(buttonIndex - 1));
		surroundingList.add(allButtons.get(buttonIndex + 1));
		surroundingList.add(allButtons.get(buttonIndex + 9));
		surroundingList.add(allButtons.get(buttonIndex + 10));
		surroundingList.add(allButtons.get(buttonIndex + 11));
		return surroundingList;
	}

}
