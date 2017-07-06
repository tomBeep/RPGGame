package conversation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ConversationCreator {
	ConversationSegment root;
	ConversationSegment current;
	JPanel panel;

	// TODO select/unselct different segments using the mouse/keys
	// TODO allow you to create an option which links to an already added Segment
	// TODO label Segments with their depth
	// TODO LOADING Segments and SAVING under different names
	// Go back and make methods private/public/package in all classes

	public ConversationCreator() {
		startGUI();
	}

	public void startGUI() {
		JButton b1 = new JButton("Create new Root Segment");
		b1.addActionListener((e) -> createNewSegment());

		JButton b2 = new JButton("Save Conversation");
		b2.addActionListener((e) -> saveConversation());

		JButton b3 = new JButton("Edit current Segment");
		b3.addActionListener((e) -> editCurrentSegment());

		JButton b4 = new JButton("Load Conversation");
		b4.addActionListener((e) -> loadConversation());

		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.clearRect(0, 0, 3000, 3000);
				drawTree(g);
			}
		};
		panel.setPreferredSize(new Dimension(1000, 750));

		JComponent buttons = new JPanel();
		buttons.add(b1);
		buttons.add(b3);
		buttons.add(b2);
		buttons.add(b4);

		JFrame frame = new JFrame();
		frame.add(panel, BorderLayout.PAGE_START);
		frame.add(buttons, BorderLayout.PAGE_END);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	public void loadConversation() {
		root = ConversationFileReader.loadFile("TEST1.conv");
		panel.repaint();
	}

	public void moveCurrentSegment() {
		panel.repaint();
	}

	public void editCurrentSegment() {
		editingPanel();
		panel.repaint();
	}

	public void createNewSegment() {
		root = new ConversationSegment();
		current = root;
		editingPanel();
		panel.repaint();
	}

	public void saveConversation() {
		if (root == null)
			return;
		ConversationFileReader.saveConversation(root, "TEST1.conv");
	}

	public void drawTree(Graphics g) {
		g.setColor(Color.BLACK);
		drawNode(g, root, 0, 10);
	}

	public void drawNode(Graphics g, ConversationSegment current, int depth, int x) {
		if (current == null)
			return;
		int y = 10 + depth * 80;
		if (current == this.current) {
			g.setColor(Color.green);
			g.fillOval(x - 4, y - 4, 68, 68);
			g.setColor(Color.black);
		}
		if (current.getLines().size() == 0) {
			g.setColor(Color.red);
			g.fillOval(x + 10, y + 10, 40, 40);
			g.setColor(Color.black);
		} else {
			g.fillOval(x, y, 60, 60);
		}

		int childX = x;
		for (int i = 0; i < 4; i++) {
			Option o = current.getOptions()[i];
			if (o == null)
				return;
			g.drawLine(x + 30, y + 30, childX + 30, y + 110);
			drawNode(g, o.getNext(), depth + 1, childX);
			childX += 80;
		}
	}

	public void editingPanel() {
		if (current == null)
			return;
		JFrame frame = new JFrame();
		GridLayout layout = new GridLayout(0, 2);
		JPanel options = new JPanel();
		layout.setHgap(10);
		layout.setVgap(10);
		options.setLayout(layout);

		JTextArea lines = new JTextArea();
		lines.setText(getSegmentText());
		options.add(new JLabel("Lines"));
		options.add(lines);

		options.add(new JLabel("Options"));
		JComboBox<Option> optionBox = new JComboBox<Option>();
		for (int i = 0; i < 4; i++)
			if (current.getOptions()[i] != null)
				optionBox.addItem(current.getOptions()[i]);
		options.add(optionBox);

		JTextArea pic = new JTextArea();
		pic.setText(current.getPictureName());
		options.add(new JLabel("Picture"));
		options.add(pic);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener((e) -> {
			frame.dispose();
		});
		options.add(cancel);

		JButton update = new JButton("Update");
		update.addActionListener((e) -> {
			List<Option> optionList = new ArrayList<Option>();
			for (int i = 0; i < optionBox.getItemCount(); i++)
				optionList.add(optionBox.getItemAt(i));
			updateCurrentSegment(lines.getText(), pic.getText(), optionList);
			frame.dispose();
		});
		options.add(update);

		JButton addOption = new JButton("Add Option");
		addOption.addActionListener((e) -> {
			if (optionBox.getItemCount() >= 4)
				return;// can't add more than 4 options to a segment
			newOption(optionBox);
		});
		options.add(addOption);

		JButton deleteOption = new JButton("Delete Option");
		deleteOption.addActionListener((e) -> {
			if (optionBox.getItemCount() == 0)
				return;// can't add more than 4 options to a segment
			optionBox.removeItemAt(optionBox.getSelectedIndex());
		});
		options.add(deleteOption);

		frame.setSize(1000, 500);
		frame.add(options);
		frame.setTitle("Editing Segment");
		frame.setVisible(true);
	}

	public void newOption(JComboBox<Option> optionBox) {
		JFrame frame = new JFrame();
		frame.setLayout(new GridLayout(0, 2));
		frame.add(new JLabel("Option Text: "));
		JTextArea text = new JTextArea();
		frame.add(text);
		frame.add(new JLabel("Option Action"));
		JComboBox<ChoiceAction> choiceActions = new JComboBox<ChoiceAction>();
		frame.add(choiceActions);
		JButton add = new JButton("Add");
		add.addActionListener((e) -> {
			Option o = new Option(text.getText(), (ChoiceAction) choiceActions.getSelectedItem(),
					new ConversationSegment());
			optionBox.addItem(o);
			frame.dispose();
		});
		frame.add(add);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener((e) -> frame.dispose());
		frame.add(cancel);
		frame.setSize(500, 300);
		frame.setLocation(150, 100);
		frame.setTitle("New Option");
		frame.setVisible(true);
	}

	public String getSegmentText() {
		StringBuilder s = new StringBuilder();
		for (String st : current.getLines()) {
			s.append(st);
			s.append("\n");
		}
		return s.toString();
	}

	void updateCurrentSegment(String line, String pic, List<Option> optionList) {
		String[] lines = line.split("\n");
		current.clearLines();
		for (String s : lines) {
			current.addLine(s);
		}
		current.setPicture(pic);
		for (int i = 0; i < 4; i++) {
			if (i >= optionList.size())
				current.getOptions()[i] = null;
			else
				current.getOptions()[i] = optionList.get(i);
		}
		panel.repaint();
	}

	public static void main(String[] args) {
		new ConversationCreator();
	}

}
