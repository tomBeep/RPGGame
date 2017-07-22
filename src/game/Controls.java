package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import player.Player;

/**
 * At the moment just does movement controls and will simply move a player when a key is pressed and the move() function
 * called.
 * 
 * @author Thomas Edwards
 *
 */
public class Controls implements KeyListener {

	private boolean movingLeft, movingRight, movingUp, movingDown;
	private Location l;
	private final double speed = 0.6;// the speed
	private double dx, dy;

	public Controls(Player p) {
		this.l = p.getLocation();
	}

	/**
	 * Should be called ~25 times per second when updated
	 */
	public void move() {
		l.moveLocation(dx, dy);
	}

	private void update() {
		dx = dy = 0;
		if (movingLeft)
			dx = -speed;
		if (movingRight)
			dx = speed;
		if (movingUp)
			dy = -speed;
		if (movingDown)
			dy = speed;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyChar()) {
		case 'w':
			movingUp = true;
			break;
		case 'a':
			movingLeft = true;
			break;
		case 's':
			movingDown = true;
			break;
		case 'd':
			movingRight = true;
			break;
		}
		update();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch (arg0.getKeyChar()) {
		case 'w':
			movingUp = false;
			break;
		case 'a':
			movingLeft = false;
			break;
		case 's':
			movingDown = false;
			break;
		case 'd':
			movingRight = false;
			break;
		}
		update();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
