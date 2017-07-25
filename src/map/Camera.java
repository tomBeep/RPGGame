package map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Camera {
	private int pxwidth;
	private int pxheight;
	private Map map;
	private int positionX;
	private int positionY;
	private ArrayList<ArrayList<Tile>> tileMap;
	public JPanel drawingPanel;

	public Camera(int width, int height, int posX, int posY, Map m) throws Exception {
		if (width % 2 > 0 || height % 2 > 0) {
			throw new CameraException("Please choose a width/height that is a factor of 32");
		}

		this.pxwidth = width;
		this.pxheight = height;
		this.positionX = posX;
		this.positionY = posY;
		this.map = m;
		tileMap = this.map.getTileMap();
		this.setupTestGUI();

	}

	public void moveCamera(int mx, int my) throws Exception {
		int tempX = this.positionX + mx;
		int tempY = this.positionY + my;
		if (tempX < 0 || tempY < 0) {
			throw new CameraException("cannot move camera off the map, X: " + tempX + "Y: " + tempY);
		}
		if (tempX > map.getWidth() || tempY > map.getHeight()) {
			throw new CameraException("Cannot move camera off the map, X: " + tempX + "Y: " + tempY);
		}
		this.positionX = tempX;
		this.positionY = tempY;

	}

	public void render(Graphics g) {
		Tile layerBackCloseCorner = this.map.findTile(0, this.positionX, this.positionY);
		int cornerIndex = this.tileMap.get(0).indexOf(layerBackCloseCorner);
		
		Tile layerBackFarCorner = this.map.findTile(0, this.positionX+pxwidth, this.positionY+pxheight);
		int farCornerIndex = this.tileMap.get(0).indexOf(layerBackFarCorner);
		//Tile layerObjCorner = this.tileMap.get(1).get(cornerIndex);
		//Tile layerColCorner = this.tileMap.get(2).get(cornerIndex);

		for (int i = 0; i < 2; i++) {
			for (int j = cornerIndex; j!=farCornerIndex; j++) {
				tileMap.get(i).get(j).render(g);
			}

		}
	}


		public void setupTestGUI() {
			JFrame frame = new JFrame();// all java swing desktop GUI's must have a JFrame

			// this is our main drawingPanel
			drawingPanel = new JPanel() {
				@Override
				// this is the redraw method, it is called everytime drawingPanel.repaint() is
				// called
				public void paintComponent(Graphics g) {
					render(g);
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
		/**
		 * FOR TEST PURPOSES ONLY This will start the map programme with the buffered
		 * image called grass.png
		 */
		public static void main(String[] args) throws Exception {
			try {
				BufferedImage back = ImageIO.read(new File("MapTextures/simplemapback.png"));
				BufferedImage obj = ImageIO.read(new File("MapTextures/simplemapobj.png"));
				BufferedImage col = ImageIO.read(new File("MapTextures/simplemapcol.png"));
				Map m = new Map(back, obj, col);
				Camera c = new Camera(100,100,0,0,m);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

}
