package player;

import backpack.BackPack;
import game.Location;

/**
 * This is a player object, a player object contains a backpack which is where all of the player's items are stored. A
 * player should also have a location, health, attack, magic attack and potentially other things.
 * 
 * @author Thomas Edwards
 *
 */
public class Player {

	private Health health;
	private Mana mana;
	private BackPack backpack;
	private Location location;

	public Player() {
		// TODO Auto-generated constructor stub
	}

	public Health getHealth() {
		return health;
	}

	public Mana getMana() {
		return mana;
	}

	public BackPack getBackPack() {
		return backpack;
	}

	public Location getLocation() {
		return location;
	}
}
