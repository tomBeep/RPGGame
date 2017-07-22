package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class MainMenuGUI extends JFrame {
	private int width = 400, height = 600;
	private BufferedImage mainMenuImage;

	public MainMenuGUI() {
		super();
		try {
			mainMenuImage = ImageIO.read(new File("MainAssets/menuPic.png"));
		} catch (IOException e) {
		}
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		panel.setBorder(new LineBorder(Color.white, 2));
		panel.add(new JLabel(new ImageIcon(mainMenuImage)));
		panel.add(makeButtonPanel());
		this.add(panel);
		this.setSize(new Dimension(width, height));
		this.setLocation(new Point(1920 / 2 - width / 2, 1080 / 2 - height / 2));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(mainMenuImage);
		this.setTitle("Tom and James RPG");
		this.setUndecorated(true);
		this.setVisible(true);
	}

	private void startGame() {
		// TODO
	}

	private void loadGame() {
		// TODO
	}

	private void doOptions() {
		// TODO
	}

	private JPanel makeButtonPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setLayout(new GridLayout(4, 1));
		panel.add(makeButton("Start Game", (e) -> startGame()));
		panel.add(makeButton("Load Game", (e) -> loadGame()));
		panel.add(makeButton("Options", (e) -> doOptions()));
		panel.add(makeButton("Quit", (e) -> this.dispose()));

		return panel;
	}

	private JButton makeButton(String name, ActionListener a) {
		JButton button = new JButton(name);
		button.setBackground(Color.BLACK);
		button.setForeground(Color.yellow);
		button.addActionListener(a);
		button.setSize(new Dimension(100, 20));
		button.setFocusable(false);
		button.setFont(new Font("Arial", 1, 20));
		return button;
	}

	public static void main(String[] args) {
		new MainMenuGUI();
	}

}
