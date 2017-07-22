package map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

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

	// The actual map image
	BufferedImage img;

	public Map(BufferedImage img) throws IOException {
		this.img = img;
		this.tileMap = new ArrayList<Tile>();
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.loadMap(this.img);
	}

	public void render(Graphics g, int regionX, int regionY, int regionW, int regionH) {
		for (Tile t : this.tileMap) {
			//if (t.inside(regionX, regionY) || t.inside(regionX + regionW, regionY + regionH)) {
				t.render(g);
			//}

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

}
