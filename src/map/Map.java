package map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Added a single comment
 * 
 * @author Thomas Edwards
 *
 */
public class Map {

	// The map itself
	private ArrayList<Tile> tileMap;

	// width of map in pixels
	private int width;

	// height of map in pixels
	private int height;

	// Each tile will always be 32x32 pixels
	public final int TileSize = 32;

	public JPanel drawingPanel;

	// The actual map image
	BufferedImage img;

	public Map(BufferedImage img) throws IOException {
		this.img = img;
		this.tileMap = new ArrayList<Tile>();
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.loadMap(this.img);
		this.setupTestGUI();
	}

	public void setupTestGUI() {
		JFrame frame = new JFrame();// all java swing desktop GUI's must have a JFrame

		// this is our main drawingPanel
		drawingPanel = new JPanel() {
			@Override
			// this is the redraw method, it is called everytime drawingPanel.repaint() is called
			public void paintComponent(Graphics g) {
				g.fillRect(50, 50, 400, 400);// example

				// here we can call the render method, or whatever drawing method you want
				// render(g,bla,bla,bla,bla);
			}
		};

		frame.add(drawingPanel);// if we are only adding a single thing to our frame, we can do it like this.

		// means that the programme will end when the frame is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(1000, 600);// sets the size of the frame
		frame.setVisible(true);// shows the frame

	}

	public void render(Graphics g, int regionX, int regionY, int regionW, int regionH) {
		for (Tile t : this.tileMap) {
			// if (t.inside(regionX, regionY) || t.inside(regionX + regionW, regionY + regionH)) {
			t.render(g);
			// }

		}
	}

	public void loadMap(BufferedImage img) {

		for (int y = 0; y < this.height; y = +32) {
			for (int x = 0; x < this.width; x = +32) {
				Tile newT = new Tile(x, y, img.getSubimage(x, y, TileSize, TileSize));
				this.tileMap.add(newT);

			}
		}

	}

	/**
	 * FOR TEST PURPOSES ONLY
	 * This will start the map programme with the buffered image called grass.png
	 */
	public static void main(String[] args) {
		try {
			new Map((ImageIO.read(new File("MapTextures/simpleMapV3.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
