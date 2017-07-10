
package conversation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

/**
 * Creates a new Conversation GUI Frame which will display when the start() method is called. Advance the conversation
 * by using the space-bar or by calling the next() method, select options by pressing the number or letter corresponding
 * to the option. The conversation GUI contains a single root ConversationSegment which is the top of the conversation
 * segment tree.
 * 
 * 
 * @author Thomas Edwards
 *
 */
public class ConversationGUI implements Conversation {
	private ConversationSegment root;
	private JFrame frame;
	private JLabel picture;
	private JTextArea[] options = new JTextArea[4];
	private JTextArea text;
	private boolean optionsAvailable = false;// whether or not conversation options can be clicked

	private final int frameWidth = 1080, frameHeight = 720;// the width/height of the frame.

	/**
	 * @param root
	 *            the root of the conversation tree for this conversation
	 */
	public ConversationGUI(ConversationSegment root) {
		this.root = root;
	}

	/**
	 * @param filename
	 *            the root of the conversation tree for this conversation
	 */
	public ConversationGUI(String filename) {
		try {
			this.root = ConversationFileReader.loadFile(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		this.setupGUI();
		this.display();
	}

	@Override
	public void run() {
		this.start();
	}

	/**
	 * Ends and removes the conversation GUI. This method is called automatically if next() is called and no more
	 * conversation is available to be called.
	 */
	public void end() {
		frame.dispose();
	}

	/**
	 * Moves the conversation forward one line. If all the lines have been reached then displays the options, if options
	 * have already been displayed but not called, does nothing. If the conversation has no more lines to display and no
	 * more options then it is over and so calling this method will end the conversation.
	 * 
	 * Note. that this method is automatically called whenever spacebar is pressed inside the conversaton GUI.
	 */
	public void next() {
		if (optionsAvailable)
			return;
		String nextLine = root.getNextLine();
		if (nextLine == null && root.getOptions()[0] != null)
			fillOptions();
		else if (nextLine == null && root.getOptions()[0] == null)
			this.end();
		else
			text.setText(nextLine);
	}

	private JTextArea makeOption() {
		JTextArea option = new JTextArea();
		option.setFont(new Font("arial", 0, 24));
		option.setBackground(Color.black);
		option.setForeground(Color.white);
		option.setEditable(false);
		option.setFocusable(false);
		return option;
	}

	private void setupGUI() {
		JPanel picturePanel = new JPanel();
		picturePanel.setBackground(Color.WHITE);
		picturePanel.setBorder(new LineBorder(Color.BLACK, 2));
		picturePanel.setPreferredSize(new Dimension(800, 500));

		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new GridLayout(4, 1));
		options[0] = makeOption();
		options[1] = makeOption();
		options[2] = makeOption();
		options[3] = makeOption();
		optionPanel.add(options[0]);
		optionPanel.add(options[1]);
		optionPanel.add(options[2]);
		optionPanel.add(options[3]);

		text = new JTextArea();
		text.setWrapStyleWord(true);
		text.setLineWrap(true);
		text.setEditable(false);
		text.setFocusable(false);
		text.setFont(new Font("arial", 0, 24));
		text.setBackground(Color.black);
		text.setForeground(Color.yellow);

		try {
			// The default picture
			BufferedImage myPicture = ImageIO.read(new File("ConversationPictures/dragon1.png"));
			picture = new JLabel(new ImageIcon(myPicture));
			picture.setPreferredSize(new Dimension(800, 500));
			picturePanel.add(picture);
		} catch (IOException e) {
			e.printStackTrace();
		}

		frame = new JFrame();
		frame.add(picture, BorderLayout.PAGE_START);
		frame.add(text, BorderLayout.CENTER);
		frame.add(optionPanel, BorderLayout.PAGE_END);
		frame.setLocation(500, 100);
		frame.setBackground(Color.white);
		frame.setUndecorated(true);
		frame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				doKey(arg0);
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

		});
		frame.setSize(frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// TODO change to not close programme
		frame.setVisible(true);

	}

	/**
	 * Displays and starts the Conversation GUI's first segment. Draws the picture associated with the current
	 * Conversation Segment, if no picture is associated with the Segment, then the previous segment's picture will
	 * remain.
	 */
	private void display() {
		text.setText(root.getNextLine());
		BufferedImage img = root.getPicture();
		if (img != null) {
			picture.setIcon(new ImageIcon(img));
		}
		if (!optionsAvailable)
			this.removeOptions();
	}

	/**
	 * Displays the options available and removes the restriction
	 */
	private void fillOptions() {
		Option[] optionList = root.getOptions();
		for (int i = 0; i < 4; i++) {
			if (optionList[i] == null)
				break;
			options[i].setText((i + 1) + ") " + optionList[i].getText());
		}
		optionsAvailable = true;
	}

	private void removeOptions() {
		for (int i = 0; i < 4; i++) {
			options[i].setText("");
		}
	}

	private void doKey(KeyEvent e) {
		if (!optionsAvailable && e.getKeyCode() == KeyEvent.VK_SPACE) {
			next();
		} else if (optionsAvailable) {
			if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_1)
				chooseOption(0);
			else if (e.getKeyCode() == KeyEvent.VK_B || e.getKeyCode() == KeyEvent.VK_2)
				chooseOption(1);
			else if (e.getKeyCode() == KeyEvent.VK_C || e.getKeyCode() == KeyEvent.VK_3)
				chooseOption(2);
			else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_4)
				chooseOption(3);
		}
	}

	/**
	 * Selects the specified Option and moves the conversation forward.
	 * 
	 * @param optionIndex
	 *            the index of the option to choose (Should be 0,1,2 or 3)
	 */
	private void chooseOption(int optionIndex) {
		if (optionIndex < 4 && optionIndex >= 0 && root.checkValidOption(optionIndex)) {
			optionsAvailable = false;
			root = root.doOption(optionIndex);
			if (root == null) {
				this.end();
				return;
			}
			this.display();
		}
	}

	// TODO remove, only for testing purposes
	public static void main(String[] args) {
		ConversationSegment root = new ConversationSegment();
		root.addLine("Thomas the dragon is in a very bad situation, he is being attacked by another, bigger dragon.");
		root.addLine("What should Thomas do?");
		ConversationSegment option1 = new ConversationSegment();
		option1.addLine("Thomas does nothing and is brutally murdered, then eaten");
		option1.addLine("Game Over");
		option1.setPicture("dragon1.png");
		root.addOption("Nothing", null, option1);
		ConversationSegment option2 = new ConversationSegment();
		option2.addLine("Thomas runs away.....");
		root.addOption("Run away", null, option2);
		ConversationSegment option3 = new ConversationSegment();
		option3.addLine(
				"Thomas fights back, and manages to overpower the bigger dragon using his superior intelligence");
		root.addOption("FIGHT!", null, option3);
		root.setPicture("dragon2.png");

		option3.addOption("Leave", null, null);// should end conversation right after option is selected

		ConversationSegment option12 = new ConversationSegment();
		option12.addLine("Thomas Spits on the corpse then leaves");// should end conversation after displaying a single
																	// line
		option3.addOption("Spit on corpse", null, option12);

		ConversationFileReader.saveConversation(root, "TEST1");
		Conversation i = new ConversationGUI("TEST1");
		Thread t = new Thread(i);
		t.start();
	}

}
