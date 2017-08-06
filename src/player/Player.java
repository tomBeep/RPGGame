package player;

import backpack.BackPack;
import game.Location;
import map.Tile;

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
	private int gold, evilPoints, goodPoints;

	// ------------Getters and Setters------------
	// TODO check min/max values eg. gold should not be able to be set to negative, it should throw an excpetion if this
	// happens...

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getEvilPoints() {
		return evilPoints;
	}

	public void setEvilPoints(int evilPoints) {
		this.evilPoints = evilPoints;
	}

	public int getGoodPoints() {
		return goodPoints;
	}

	public void setGoodPoints(int goodPoints) {
		this.goodPoints = goodPoints;
	}

	public Player(Location startingPosition) {
		this.location = startingPosition;
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
