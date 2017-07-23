package map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author James Watt
 *
 */
public class Tile {
	private BufferedImage img;
	private int x;
	private int y;
	private boolean obstical = false;
	public final int size = 32;

	public Tile(int x, int y, BufferedImage img) {
		this.x = x;
		this.y = y;
		this.img = img;

	}

	public boolean inside(int px, int py) {
		if (px > this.x && px < (this.x + size)) {
			if (py > this.y && py < (this.y + size)) {
				return true;
			}

		}
		return false;

	}

	public void render(Graphics g) {
		g.drawImage(img, x, y, null);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
