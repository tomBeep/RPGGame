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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

/**
 * Creates a new Conversation GUI which is a JFrame, use addOption(), addTextLine() and addSegment() to add options/Text
 * One Conversation GUI should be used for an entire single Conversation.
 * 
 * @author Thomas Edwards
 *
 */
public class ConversationGUI {
	private ConversationSegment root;
	private JFrame frame;
	private JLabel picture;
	private JTextArea[] options = new JTextArea[4];
	private JTextArea text;
	private boolean optionsAvailable = false;//whether or not conversation options can be clicked
	
	private final int frameWidth = 1080, frameHeight = 720;

	public ConversationGUI(ConversationSegment root) {
		this.root = root;
		setupGUI();
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

	public void setupGUI() {
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
		text.setText("Hello, My name is Freddy and I am a big black dragon that breathes fire. "
				+ "You are a stupid little dragon who doesn't know shit... -Example of text-");

		try {
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
		// frame.setUndecorated(true);
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
	 * Displays and starts the Conversation GUI's first segment
	 */
	public void display() {
		text.setText(root.getNextLine());
		BufferedImage img = root.getPicture();
		if (img != null) {
			picture.setIcon(new ImageIcon(img));
		}
	}

	/**
	 * Moves the conversation forward one line. If all the lines have been reached then displays the options, if options
	 * have already been displayed, does nothing
	 */
	public void next() {
		if (optionsAvailable)
			return;
		String nextLine = root.getNextLine();
		if (nextLine == null)
			fillOptions();
		else
			text.setText(nextLine);
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

	private void chooseOption(int optionIndex) {
		root = root.doOption(optionIndex);
		optionsAvailable = false;
	}

	/**
	 * Hides the conversation GUI
	 */
	public void hide() {

	}

	// TODO remove, only for testing purposes
	public static void main(String[] args) {
		ConversationSegment q = new ConversationSegment();
		q.addLine("Thomas is in a very bad situation");
		q.addLine("What should Thomas do?");
		q.addOption("Nothing", null, null);
		q.addOption("Something", null, null);
		q.setPicture("dragon2.png");
		ConversationGUI i = new ConversationGUI(q);
		try {
			Thread.sleep(1000);
			i.display();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
}
