package map;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Map {
	// ArrayList of MapTiles, each arrayList is a layer in the game map. e.g. first
	// layer is ground next might be a mountain.
	ArrayList<ArrayList<Integer>> map = new ArrayList<ArrayList<Integer>>();

	HashMap<Integer, BufferedImage> images = new HashMap<Integer, BufferedImage>();
	
	static int  TilePixelSize = 32;

	public Map() {
		this.fillMap();
		//This gets
		try {

			images.put(1, ImageIO.read(getClass().getResourceAsStream("/MapTextures/grass.jpg")));

		} catch (IOException e) {
			e.printStackTrace();
			throw new Error();

		}
	}

	public void fillMap() {
		map.add(new ArrayList<Integer>());
		for (int i = 0; i < 10; i++) {
			map.get(1).add(1);
		}
	}
	
	//x and y is where we start rendering from. Map is the list of tiles, width is the width of the map in tiles
	public void renderMap(int x, int y,ArrayList<ArrayList<Integer>>map,int width,Graphics g) {
		int tileRow = 1;
		int tileCol =1;
		
		for(int i=0;i<map.size();i++) {
			for(int j=0;j<map.get(j).size();j++) {
				int pixelPosX = x+(tileCol-1)*TilePixelSize;
				int pixelPosY = y+(tileRow-1)*TilePixelSize;
			
				g.drawImage(pixelPosX,pixelPosY,images.get(map.get(j).get(i)));
				tileCol+=1;
				if(tileCol > width) {
					tileCol=1;
					tileRow+=1;
				}
			}
			
		}
		
	}

}
