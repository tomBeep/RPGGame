package items;

import java.util.ArrayList;
import java.util.List;
import player.Player;

/**
 * BackPack class which contains a list of equipedItems and a List of Items in
 * the bag
 *
 * @author Thomas Edwards
 *
 */
public class BackPack {
	private List<Item> itemBag;
	private List<Item> equippedItems;

	private int packSize = 20;
	private int equippedItemSize = 6;

	public BackPack() {
		itemBag = new ArrayList<>();
		equippedItems = new ArrayList<>();
	}

	/**
	 * Picks up the specified Item.
	 *
	 * @param i
	 * @throws BackPackException
	 *             if the itemBag is full.
	 */
	public void pickupItem(Item i) throws BackPackException {
		if (itemBag.size() >= packSize)
			throw new BackPackException("Can't pick up items once backpack is full");
		itemBag.add(i);
	}

	/**
	 * Equips the item and applys its effects to the player
	 *
	 * @param p
	 * @param i
	 * @throws BackPackException
	 *             if the max number of equipable items has been reached already
	 */
	public void equipItem(Player p, Item i) throws BackPackException {
		if (equippedItems.size() >= equippedItemSize)
			throw new BackPackException("Can't equip more than 6 items");
		itemBag.remove(i);
		equippedItems.add(i);
		i.applyEffect(p);
	}

	/**
	 * Unequips the item and removes the effects that this item provided to the
	 * player.
	 *
	 * @param p
	 *            the player whom this item is being unequipped by
	 * @param i
	 * @throws BackPackException
	 *             if the itemBag is full, then you cannot unequip anything.
	 */
	public void unequipItem(Player p, Item i) throws BackPackException {
		if (itemBag.size() >= packSize)
			throw new BackPackException("Can't unequip item if bag is full");
		equippedItems.remove(i);
		itemBag.add(i);
		i.removeEffect(p);
	}

	/**
	 * Destroys an item which is not currently equipped.
	 *
	 * @param i
	 *            the item to be destroyed, items which are currently equipped
	 *            cannot be destroyed.
	 */
	public void destroyItem(Item i) {
		itemBag.remove(i);
	}

	private class BackPackException extends Exception {
		public BackPackException(String s) {
			super(s);
		}
	}

}