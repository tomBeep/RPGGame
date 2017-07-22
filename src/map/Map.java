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
	private ArrayList<ArrayList<Tile>> tileMap;

	// width of map in pixels
	private int width;

	// height of map in pixels
	private int height;

	// Each tile will always be 32x32 pixels
	public final int TileSize = 32;

	public JPanel drawingPanel;

	// The map background
	BufferedImage imgback;

	// The map objects e.g. trees
	BufferedImage imgobj;

	// The maps collision blocks(not to be rendered)
	BufferedImage imgcol;

	public Map(BufferedImage imgback, BufferedImage imgobj, BufferedImage imgcol) throws IOException {
		this.imgback = imgback;
		this.imgobj = imgobj;
		this.imgcol = imgcol;
		this.tileMap = new ArrayList<ArrayList<Tile>>(2);
		this.width = imgback.getWidth();
		this.height = imgback.getHeight();
		this.loadMap(this.imgback, this.imgobj, this.imgcol);
		this.setupTestGUI();
	}

	public void render(Graphics g, int regionX, int regionY, int regionW, int regionH) {
		
			for (int i = 0; i < 2; i++) {
				for(Tile t:tileMap.get(i)) {
					t.render(g);
				}
			}
			//need to add rendering for a camera view

		
	}

	public void setupTestGUI() {
		JFrame frame = new JFrame();// all java swing desktop GUI's must have a JFrame

		// this is our main drawingPanel
		drawingPanel = new JPanel() {
			@Override
			// this is the redraw method, it is called everytime drawingPanel.repaint() is
			// called
			public void paintComponent(Graphics g) {
				render(g, 0, 0, 0, 0);
				// here we can call the render method, or whatever drawing method you want
				// render(g,bla,bla,bla,bla);
			}
		};

		frame.add(drawingPanel);// if we are only adding a single thing to our frame, we can do it like this.

		// means that the programme will end when the frame is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(640, 900);// sets the size of the frame
		frame.setVisible(true);// shows the frame

	}

	public void loadMap(BufferedImage imgback, BufferedImage imgobj, BufferedImage imgcol) {
		tileMap.add(new ArrayList<Tile>());
		tileMap.add(new ArrayList<Tile>());
		tileMap.add(new ArrayList<Tile>());
		
        
		for (int y = 0; y < this.height; y = y + 32) {
			for (int x = 0; x < this.width; x = x + 32) {
				Tile newBackTile = new Tile(x, y, imgback.getSubimage(x, y, TileSize, TileSize));
				this.tileMap.get(0).add(newBackTile);
				
				Tile newObjTile = new Tile(x, y, imgobj.getSubimage(x, y, TileSize, TileSize));
				this.tileMap.get(1).add(newObjTile);
				
				Tile newColTile = new Tile(x, y, imgback.getSubimage(x, y, TileSize, TileSize));
				this.tileMap.get(2).add(newColTile);
				System.out.println(x);

			}
			System.out.println(y);
		}

	}

	/**
	 * FOR TEST PURPOSES ONLY This will start the map programme with the buffered
	 * image called grass.png
	 */
	public static void main(String[] args) {
		try {
			BufferedImage back = ImageIO.read(new File("MapTextures/simplemapback.png"));
			BufferedImage obj = ImageIO.read(new File("MapTextures/simplemapobj.png"));
			BufferedImage col = ImageIO.read(new File("MapTextures/simplemapcol.png"));
			new Map(back,obj,col);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
