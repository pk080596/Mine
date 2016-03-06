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

public class Button {

	private int size;
	private int length;
	private int numMine;
	private int flipped = 0;
	private ActionListener Mine;
	private ActionListener NotMine;

	Map<Integer, JToggleButton> allButtons = new HashMap<Integer, JToggleButton>();
	Map<Integer, JToggleButton> allMines = new HashMap<Integer, JToggleButton>();
	Map<JToggleButton, Integer> buttons = new HashMap<JToggleButton, Integer>();
	Map<Button, JToggleButton> buttonIndex = new HashMap<Button, JToggleButton>();
	Map<JToggleButton, Boolean> buttonStatus = new HashMap<JToggleButton, Boolean>();
	List<Integer> mines = new LinkedList<Integer>();
	Minesweeper restart = new Minesweeper();

	public Button(int length, int numMine, List<Integer> mines) {
		this.numMine = numMine;
		this.mines = mines;
		this.length = length;
		this.size = length * length;
	}

	public void createButton(JFrame frame, JPanel panel) {
		// If mine is clicked
		Mine = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton button = (JToggleButton) e.getSource();
				if (checkFlag(button)) {
					button.setSelected(false);
				} else {
					try {
						BufferedImage bomb = ImageIO.read(new File("c:\\workspace\\Mine\\src\\mine.png"));
						Image scaledBomb = bomb.getScaledInstance(button.getWidth() - 10, button.getHeight() - 10,
								Image.SCALE_SMOOTH);
						button.setIcon(new ImageIcon(scaledBomb));
					} catch (IOException ex) {
					}
					Object[] option = { "Restart" };
					JPanel gameover = new JPanel();
					JLabel lose = new JLabel("You lose");
					gameover.add(lose);
					int result = JOptionPane.showOptionDialog(null, gameover, "Game Over", JOptionPane.YES_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, option, null);
					if (result == JOptionPane.YES_OPTION) {
						frame.setVisible(false);
						frame.dispose();
						restart.setup();
					}
				}
				button.setFocusPainted(false);
			}
		};

		// Action if mine is not clicked
		NotMine = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JToggleButton button = (JToggleButton) e.getSource();

				if (checkFlag(button)) {
					button.setSelected(false);
				} else if (!button.isSelected()) {
					button.setSelected(true);
				} else {
					int surrounding;
					List<JToggleButton> surroundingButtons = new LinkedList<JToggleButton>();
					int index = getButtonIndex(button);

					surrounding = countMines(button);
					if (surrounding == 0) {
						surroundingButtons = getSurroundingButtons(allButtons, index, button);
					}
					if (!surroundingButtons.isEmpty()) {
						spreadOut(surroundingButtons);
					} else {
						if(surrounding > 0)
						button.setText(String.valueOf(surrounding));
					}
					button.setFocusPainted(false);
					flipped++;

					if (flipped == size - numMine) {
						Object[] option = { "Restart" };
						JPanel gameover = new JPanel();
						JLabel gg = new JLabel("You Won!");
						gameover.add(gg);
						int result = JOptionPane.showOptionDialog(null, gameover, "Win", JOptionPane.YES_OPTION,
								JOptionPane.PLAIN_MESSAGE, null, option, null);
						if (result == JOptionPane.YES_OPTION) {
							frame.setVisible(false);
							frame.dispose();
							flipped = 0;
							restart.setup();
						}
					}
				}
			}
		};

		int iterate = 0;

		// Create buttons
		for (int i = 0; i < size; i++) {
			JToggleButton button;
			button = new JToggleButton("");
			setFlag(button, false);
			button.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e) && SwingUtilities.isLeftMouseButton(e)
							&& button.isSelected()) {
						

					} else if (SwingUtilities.isRightMouseButton(e) && !button.isSelected()) {
						if (!checkFlag(button)) {
							try {
								BufferedImage flag = ImageIO.read(new File("c:\\workspace\\Mine\\src\\flag.png"));
								Image scaledFlag = flag.getScaledInstance(button.getWidth() - 10,
										button.getHeight() - 10, Image.SCALE_SMOOTH);
								button.setIcon(new ImageIcon(scaledFlag));
							} catch (IOException ex) {

							}
							setFlag(button, true);
						} else {
							button.setIcon(null);
							setFlag(button, false);
						}
					}
				}
			});
			if (i == mines.get(iterate) && iterate < numMine) {
				button.addActionListener(Mine);
				allMines.put(i, button);
				if (iterate != numMine - 1) {
					iterate++;
				}
			} else {
				button.addActionListener(NotMine);
			}

			// Action if mine is clicked
			button.setFocusPainted(false);
			panel.add(button);
			allButtons.put(i, button);
			buttons.put(button, i);
		}
	}

	public void setFlag(JToggleButton button, boolean status) {
		buttonStatus.put(button, status);
	}

	public boolean checkFlag(JToggleButton button) {
		return buttonStatus.get(button);
	}

	private int countMines(JToggleButton button) {
		int mineIndex = getButtonIndex(button);
		int mine = 0;
		int horizontal = 0;
		int vertical = 0;

		if (mineIndex % length == length - 1) {
			horizontal = 2;
		} else if (mineIndex % length == 0) {
			horizontal = 1;
		}
		if (mineIndex <= length - 1) {
			vertical = 1;
		} else if (mineIndex >= size - length) {
			vertical = 2;
		}

		if (!(vertical == 1) && !(horizontal == 1) && allMines.containsKey(mineIndex - length + 1)) {
			mine++;
		}
		if (!(vertical == 1) && allMines.containsKey(mineIndex - length)) {
			mine++;
		}
		if (!(vertical == 1) && !(horizontal == 2) && allMines.containsKey(mineIndex - length - 1)) {
			mine++;
		}
		if (!(horizontal == 1) && allMines.containsKey(mineIndex - 1)) {
			mine++;
		}
		if (!(horizontal == 2) && allMines.containsKey(mineIndex + 1)) {
			mine++;
		}
		if (!(vertical == 2) && !(horizontal == 1) && allMines.containsKey(mineIndex + length - 1)) {
			mine++;
		}
		if (!(vertical == 2) && allMines.containsKey(mineIndex + length)) {
			mine++;
		}
		if (!(vertical == 2) && !(horizontal == 2) && allMines.containsKey(mineIndex + length + 1)) {
			mine++;
		}
		return mine;
	}

	private List<JToggleButton> getSurroundingButtons(Map<Integer, JToggleButton> allButtons, int buttonIndex,
			JToggleButton button) {
		List<JToggleButton> surroundingList = new LinkedList<JToggleButton>();
		int horizontal = 0;
		int vertical = 0;

		if (buttonIndex % length == length - 1) {
			horizontal = 2;
		} else if (buttonIndex % length == 0) {
			horizontal = 1;
		}
		if (buttonIndex <= length - 1) {
			vertical = 1;
		} else if (buttonIndex >= size - length) {
			vertical = 2;
		}

		JToggleButton button_11N = allButtons.get(buttonIndex - length + 1);
		JToggleButton button_10N = allButtons.get(buttonIndex - length);
		JToggleButton button_9N = allButtons.get(buttonIndex - length - 1);
		JToggleButton button_1N = allButtons.get(buttonIndex - 1);
		JToggleButton button_1 = allButtons.get(buttonIndex + 1);
		JToggleButton button_9 = allButtons.get(buttonIndex + length - 1);
		JToggleButton button_10 = allButtons.get(buttonIndex + length);
		JToggleButton button_11 = allButtons.get(buttonIndex + length + 1);

		if (!(vertical == 1) && !(horizontal == 1) && !button_11N.isSelected()) {
			surroundingList.add(button_11N);
		}
		if (!(vertical == 1) && !button_10N.isSelected()) {
			surroundingList.add(button_10N);
		}
		if (!(vertical == 1) && !(horizontal == 2) && !button_9N.isSelected()) {
			surroundingList.add(button_9N);
		}
		if (!(horizontal == 1) && !button_1N.isSelected()) {
			surroundingList.add(button_1N);
		}
		if (!(horizontal == 2) && !button_1.isSelected()) {
			surroundingList.add(button_1);
		}
		if (!(vertical == 2) && !(horizontal == 1) && !button_9.isSelected()) {
			surroundingList.add(button_9);
		}
		if (!(vertical == 2) && !button_10.isSelected()) {
			surroundingList.add(button_10);
		}
		if (!(vertical == 2) && !(horizontal == 2) && !button_11.isSelected()) {
			surroundingList.add(button_11);
		}
		return surroundingList;
	}

	private void spreadOut(List<JToggleButton> surroundingButtons) {

		for (JToggleButton toggle : surroundingButtons) {
			List<JToggleButton> spread = new LinkedList<JToggleButton>();
			if (!toggle.isSelected() && !checkFlag(toggle)) {
				toggle.setSelected(true);
				flipped++;
				int index = getButtonIndex(toggle);
				int mineNumber = countMines(toggle);
				if (mineNumber == 0) {
					spread = getSurroundingButtons(allButtons, index, toggle);
					spreadOut(spread);
				} else {
					if(mineNumber > 0)
					toggle.setText(String.valueOf(mineNumber));
				}
			}
			toggle.setFocusPainted(false);
		}
	}

	private int getButtonIndex(JToggleButton button) {
		return buttons.get(button);
	}

	private JToggleButton getJButton(Button button) {
		return buttonIndex.get(button);
	}
}
