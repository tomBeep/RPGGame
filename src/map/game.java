package map;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class game extends BasicGame {

	private TiledMap map;

	static int TilePixelSize = 32;

	public game(String title) {
		super(title);
	}

	//draws all the graphics
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		this.map.render(0, 0);

	}

	//load all fonts and sounds
	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		try {
			map = new TiledMap("MapTextures/simpleMap.tmx");
		} catch (SlickException e) {
			e.printStackTrace();
			throw new SlickException("Map cannot be read");

		}

	}

	//game logic
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub

	}

	public int getTileID(int x, int y, int layerIndex) {
		return map.getTileId(x, y, layerIndex);
	}
	
	public org.newdawn.slick.tiled.TiledMap getTiledMap() {
		return this.map;
	}
	
	public static void main(String[] args) throws SlickException {
		
		AppGameContainer app = new  AppGameContainer(new game("First Slick2D"));
		app.setDisplayMode(300,300,false);
		app.start();
		
		
		
	}

}
