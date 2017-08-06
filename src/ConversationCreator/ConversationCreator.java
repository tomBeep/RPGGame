package ConversationCreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import conversation.ChoiceAction;
import conversation.ConversationFileReader;
import conversation.ConversationSegment;
import conversation.Option;

public class ConversationCreator {
	ConversationSegment root;
	ConversationSegment current;
	JPanel panel;
	ConversationDrawer cd;

	private final int width = 1000, height = 750;

	// TODO allow you to create an option which links to an already added Segment

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
		panel.addMouseListener(new TomMouse());
		panel.setPreferredSize(new Dimension(this.width, this.height));

		JComponent buttons = new JPanel();
		buttons.add(b1);
		buttons.add(b2);
		buttons.add(b3);
		buttons.add(b4);
		buttons.add(b5);

		JFrame frame = new JFrame();
		panel.setFocusable(true);
		frame.add(panel, BorderLayout.PAGE_START);
		frame.add(buttons, BorderLayout.PAGE_END);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Conversation Editor");
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
				JOptionPane.showMessageDialog(panel, "File could not be found");
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
		frame.setLocation(300, 300);
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
		cd = new ConversationDrawer(g, root);
		cd.draw(current);
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
			if (updateCurrentSegment(lines.getText(), pic.getText(), optionList, frame))
				frame.dispose();
		});

		JButton addOption = new JButton("Add Option");
		addOption.addActionListener((e) -> {
			if (optionBox.getItemCount() >= 4)
				return;// can't add more than 4 options to a segment
			newOption(optionBox);
		});

		JButton editOption = new JButton("Edit Option");
		editOption.addActionListener((e) -> {
			if (optionBox.getItemCount() == 0)
				return;// can't add more than 4 options to a segment
			editOption((Option) optionBox.getSelectedItem());
		});

		JButton moveToOption = new JButton("UPDATE current Segment and move to Selected Option");
		moveToOption.addActionListener((e) -> {
			if (newRoot) {
				JOptionPane.showMessageDialog(frame, "Can't move to an option when this is a new root");
				return;
			}
			List<Option> optionList = new ArrayList<Option>();
			for (int i = 0; i < optionBox.getItemCount(); i++)
				optionList.add(optionBox.getItemAt(i));
			if (!updateCurrentSegment(lines.getText(), pic.getText(), optionList, frame))
				return;
			Option o = optionBox.getItemAt(optionBox.getSelectedIndex());
			if (o != null && o.getNext() != null)
				current = o.getNext();
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
		options.add(editOption);
		options.add(cancel);
		options.add(update);

		frame.setSize(1500, 500);
		frame.add(options);
		if (!newRoot)
			frame.setTitle("Segment Editor");
		else
			frame.setTitle("New Root Segment");
		frame.setVisible(true);
	}

	public void editOption(Option o) {
		JFrame frame = new JFrame();
		frame.setLayout(new GridLayout(0, 2));
		frame.add(new JLabel("Option Text: "));
		JTextArea text = new JTextArea();
		frame.add(text);
		text.setText(o.getText());
		frame.add(new JLabel("Option Action"));

		JComboBox<String> choiceActions = new JComboBox<String>();
		choiceActions.addItem(null);
		for (String s : ChoiceAction.choiceActions.keySet()) {
			choiceActions.addItem(s);
		}

		frame.add(choiceActions);
		JButton add = new JButton("Update");
		add.addActionListener((e) -> {
			o.setText(text.getText());
			if (choiceActions.getItemCount() != 0 && choiceActions.getSelectedItem() != null)
				o.setAction((String) choiceActions.getSelectedItem());
			else
				o.setAction(null);
			frame.dispose();
		});
		add.setBackground(Color.green);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener((e) -> frame.dispose());
		cancel.setBackground(Color.red);
		frame.add(cancel);
		frame.add(add);
		frame.setSize(500, 300);
		frame.setLocation(150, 100);
		frame.setTitle("New Option");
		frame.setVisible(true);
	}

	public void newOption(JComboBox<Option> optionBox) {
		JFrame frame = new JFrame();
		frame.setLayout(new GridLayout(0, 2));
		frame.add(new JLabel("Option Text: "));
		JTextArea text = new JTextArea();
		frame.add(text);
		frame.add(new JLabel("Option Action"));

		JComboBox<String> choiceActions = new JComboBox<String>();
		choiceActions.addItem(null);
		for (String s : ChoiceAction.choiceActions.keySet()) {
			choiceActions.addItem(s);
		}

		frame.add(choiceActions);
		JButton add = new JButton("Add");
		add.addActionListener((e) -> {
			Option o = new Option(text.getText(), (String) choiceActions.getSelectedItem(), new ConversationSegment());
			optionBox.addItem(o);
			frame.dispose();
		});
		add.setBackground(Color.green);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener((e) -> frame.dispose());
		cancel.setBackground(Color.red);
		frame.add(cancel);
		frame.add(add);
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

	boolean updateCurrentSegment(String line, String pic, List<Option> optionList, JFrame frame) {
		try {
			current.setPicture(pic);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Picture file associated with this segment could not be found");
			return false;
		}
		String[] lines = line.split("\n");
		current.clearLines();
		if (lines[0].length() != 0)
			for (String s : lines) {
				current.addLine(s);
			}
		for (int i = 0; i < 4; i++) {
			if (i >= optionList.size())
				current.getOptions()[i] = null;
			else
				current.getOptions()[i] = optionList.get(i);
		}
		panel.repaint();
		return true;
	}

	public static void main(String[] args) {
		new ConversationCreator();
	}

	private class TomMouse implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (root == null)
				return;
			ConversationSegment s = cd.getSegment(e.getX(), e.getY());
			if (s == null)
				return;
			current = s;
			if (SwingUtilities.isRightMouseButton(e))
				editCurrentSegment();
			else
				panel.repaint();
		}

	}
}
