package conversation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

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

	private final int width = 1000, height = 750;

	// TODO select/unselct different segments using the mouse/keys
	// TODO allow you to create an option which links to an already added Segment
	// TODO label Segments with their depth
	// TODO draw Segments better.

	public ConversationCreator() {
		startGUI();
	}

	public void startGUI() {
		JButton b1 = new JButton("Create new Root Segment");
		b1.addActionListener((e) -> createNewSegment());

		JButton b2 = new JButton("Edit current Segment");
		b2.addActionListener((e) -> editCurrentSegment());

		JButton b3 = new JButton("Delete all Segments");
		b3.addActionListener((e) -> {
			root = current = null;
			panel.repaint();
		});

		JButton b4 = new JButton("Save Conversation");
		b4.addActionListener((e) -> saveConversation());

		JButton b5 = new JButton("Load Conversation");
		b5.addActionListener((e) -> loadConversation());

		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.clearRect(0, 0, 3000, 3000);
				drawTree(g);
			}
		};
		panel.setPreferredSize(new Dimension(this.width, this.height));

		JComponent buttons = new JPanel();
		buttons.add(b1);
		buttons.add(b2);
		buttons.add(b3);
		buttons.add(b4);
		buttons.add(b5);

		JFrame frame = new JFrame();
		frame.add(panel, BorderLayout.PAGE_START);
		frame.add(buttons, BorderLayout.PAGE_END);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	public void loadConversation() {

		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JTextArea text = new JTextArea();
		JButton load = new JButton("Load");
		load.addActionListener((e) -> {
			try {
				root = ConversationFileReader.loadFile(text.getText());
				current = root;
				this.panel.repaint();
				frame.dispose();
			} catch (Exception e1) {
				text.setText("Error, loading the file, could not find Filename: \"" + text.getText() + "\"");
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener((e) -> frame.dispose());
		text.setPreferredSize(new Dimension(400, 20));
		JLabel label = new JLabel("File Name: ");
		panel.add(label);
		panel.add(text);
		panel.add(load);
		panel.add(cancel);
		frame.add(panel);
		frame.pack();
		frame.setTitle("Load");
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void moveCurrentSegment() {
		panel.repaint();
	}

	public void editCurrentSegment() {
		editingPanel(false);
		panel.repaint();
	}

	public void createNewSegment() {
		root = new ConversationSegment();
		current = root;
		editingPanel(true);
		panel.repaint();
	}

	public void saveConversation() {
		if (root == null)
			return;
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JTextArea text = new JTextArea();
		JButton save = new JButton("Save");
		save.addActionListener((e) -> {
			ConversationFileReader.saveConversation(root, text.getText());
			frame.dispose();
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener((e) -> frame.dispose());
		text.setPreferredSize(new Dimension(400, 20));
		JLabel label = new JLabel("File Name: ");
		panel.add(label);
		panel.add(text);
		panel.add(save);
		panel.add(cancel);
		frame.add(panel);
		frame.pack();
		frame.setTitle("Save");
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private void drawTree(Graphics g) {
		if (current == null || root == null)
			return;
		g.setColor(Color.BLACK);
		Map<ConversationSegment, Box> boxes = new HashMap<>();
		Map<ConversationSegment, Integer> nodes = new HashMap<>();
		this.setNodes(boxes, nodes, root, 0);
		this.drawNodes(g, boxes, nodes);
		this.drawLines(g, boxes);
	}

	private void drawLines(Graphics g, Map<ConversationSegment, Box> lines) {
		for (Entry<ConversationSegment, Box> e : lines.entrySet()) {
			for (int i = 0; i < 4; i++) {
				Option o = e.getKey().getOptions()[i];
				if (o != null && o.getNext() != null) {
					ConversationSegment s = o.getNext();
					Box b = e.getValue();
					Box b2 = lines.get(s);
					g.drawLine(b.x, b.y, b2.x, b2.y);
				}
			}
		}
	}

	private void setNodes(Map<ConversationSegment, Box> boxes, Map<ConversationSegment, Integer> nodes,
			ConversationSegment current, int depth) {
		if (current == null)
			return;
		if (!nodes.containsKey(current)) {
			nodes.put(current, depth);
			for (int i = 0; i < 4; i++) {
				if (current.getOptions()[i] != null) {
					setNodes(boxes, nodes, current.getOptions()[i].getNext(), depth + 1);
				}
			}
		}
	}

	private void drawNodes(Graphics g, Map<ConversationSegment, Box> boxes, Map<ConversationSegment, Integer> nodes) {
		Map<Integer, Integer> depthMap = new HashMap<>();// depth -> x
		for (Entry<ConversationSegment, Integer> e : nodes.entrySet()) {
			Integer x = depthMap.get(e.getValue());
			if (x == null)
				x = 40;
			g.drawRect(x, e.getValue() * 80, 90, 40);
			boxes.put(e.getKey(), new Box(x, e.getValue() * 80));
			if (e.getKey() == current) {
				g.setColor(Color.RED);
				g.drawRect(x + 2, e.getValue() * 80 + 2, 86, 36);
				g.setColor(Color.BLACK);
			}
			depthMap.put(e.getValue(), x + 100);
		}
	}

	public void editingPanel(boolean newRoot) {
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
		options.add(new JLabel("Picture File Name"));
		options.add(pic);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener((e) -> {
			frame.dispose();
		});
		cancel.setBackground(Color.red);

		JButton update = new JButton("UPDATE Segment");
		update.setBackground(Color.GREEN);
		if (newRoot)
			update.setText("Add Root");
		update.addActionListener((e) -> {
			List<Option> optionList = new ArrayList<Option>();
			for (int i = 0; i < optionBox.getItemCount(); i++)
				optionList.add(optionBox.getItemAt(i));
			updateCurrentSegment(lines.getText(), pic.getText(), optionList);
			frame.dispose();
		});

		JButton addOption = new JButton("Add Option");
		addOption.addActionListener((e) -> {
			if (optionBox.getItemCount() >= 4)
				return;// can't add more than 4 options to a segment
			newOption(optionBox);
		});

		JButton moveToOption = new JButton("UPDATE current Segment and move to Selected Option");
		moveToOption.addActionListener((e) -> {
			List<Option> optionList = new ArrayList<Option>();
			for (int i = 0; i < optionBox.getItemCount(); i++)
				optionList.add(optionBox.getItemAt(i));
			updateCurrentSegment(lines.getText(), pic.getText(), optionList);
			current = optionBox.getItemAt(optionBox.getSelectedIndex()).getNext();
			frame.dispose();
			editingPanel(false);
			panel.repaint();
		});

		JButton deleteOption = new JButton("Delete Option");
		deleteOption.addActionListener((e) -> {
			if (optionBox.getItemCount() == 0)
				return;// can't add more than 4 options to a segment
			optionBox.removeItemAt(optionBox.getSelectedIndex());
		});

		options.add(deleteOption);
		options.add(addOption);
		options.add(moveToOption);
		options.add(cancel);
		options.add(update);

		frame.setSize(1500, 500);
		frame.add(options);
		if (!newRoot)
			frame.setTitle("Editing Segment");
		else
			frame.setTitle("New Root Segment");
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

	private class Box {
		int x, y;

		public Box(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) {
		new ConversationCreator();
	}

}
