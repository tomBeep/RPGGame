package game;

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
