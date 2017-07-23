package map;

import java.awt.Graphics;
import java.util.ArrayList;

public class Camera {
	private int pxwidth;
	private int pxheight;
	private Map map;
	private int positionX;
	private int positionY;
	private ArrayList<ArrayList<Tile>> tileMap;

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

}
