package game;

/**
 * A Pixel location on the map.
 * 
 * @author Thomas Edwards
 *
 */
public class Location {

	private double x, y;

	public Location(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void moveLocation(double dx, double dy) {
		x += dx;
		y += dy;
	}

}
