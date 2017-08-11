package map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Added a single comment
 * 
 * @author James Watt
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

	// The map background
	private BufferedImage imgback;

	// The map objects e.g. trees
	private BufferedImage imgobj;

	// The maps collision blocks(not to be rendered)
	private BufferedImage imgcol;

	public Map(BufferedImage imgback, BufferedImage imgobj, BufferedImage imgcol) throws IOException {
		this.imgback = imgback;
		this.imgobj = imgobj;
		this.imgcol = imgcol;
		this.tileMap = new ArrayList<ArrayList<Tile>>(2);
		this.width = imgback.getWidth();
		this.height = imgback.getHeight();
		this.loadMap(this.imgback, this.imgobj, this.imgcol);

	}

	public ArrayList<ArrayList<Tile>> getTileMap() {
		return tileMap;
	}

	public void setTileMap(ArrayList<ArrayList<Tile>> tileMap) {
		this.tileMap = tileMap;
	}

	public BufferedImage getImgback() {
		return imgback;
	}

	public void setImgback(BufferedImage imgback) {
		this.imgback = imgback;
	}

	public BufferedImage getImgobj() {
		return imgobj;
	}

	public void setImgobj(BufferedImage imgobj) {
		this.imgobj = imgobj;
	}

	public BufferedImage getImgcol() {
		return imgcol;
	}

	public void setImgcol(BufferedImage imgcol) {
		this.imgcol = imgcol;

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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Tile findTile(int layer, int x, int y) {
		for (int i = 0; i < this.getTileMap().size(); i++) {
			if (this.tileMap.get(layer).get(i).getX() == x && this.tileMap.get(layer).get(i).getY() == y) {
				return this.tileMap.get(layer).get(i);
			}
		}
		return null;
	}

}
