package backpack;

import java.util.ArrayList;
import java.util.List;

import items.Item;
import items.UsableItem;
import items.EquipableItem;
import player.Player;

/**
 * BackPack class which contains a list of equipedItems and a list of un-equippedItems in
 * the pack.
 *
 * @author Thomas Edwards
 *
 */
public class BackPack {
	private List<Item> itemBag;
	private List<EquipableItem> equippedItems;

	private int packSize;// number of allowed unequiped items
	private int equippedItemsSize;// number of allow equipped items.

	/**
	 * Creates a new empty backpack of the default storage size 20 and able to equip 3 items.
	 */
	public BackPack() {
		packSize = 20;
		equippedItemsSize = 3;
		itemBag = new ArrayList<>();
		equippedItems = new ArrayList<>();
	}

	/**
	 * Creates a new empty backpack of the specified sizes.
	 */
	public BackPack(int storageSize, int equipSize) {
		packSize = storageSize;
		equippedItemsSize = equipSize;
		itemBag = new ArrayList<>();
		equippedItems = new ArrayList<>();
	}

	/**
	 * Creates a new Backpack of the specified size and moves all items in the old backPack to the new one. Does not
	 * un-equip/equip any items.
	 * 
	 * @throws BackPackException
	 *             if you try to move to a smaller bag while having more items than can fit in that smaller bag.
	 */
	public BackPack(int storageSize, int equipSize, BackPack oldPack) throws BackPackException {
		if (oldPack.itemBag.size() > storageSize || oldPack.equippedItems.size() > equipSize)
			throw new BackPackException("You have too many items in your bag to move to a smaller bag");
		packSize = storageSize;
		equippedItemsSize = equipSize;
		itemBag = new ArrayList<>();
		equippedItems = new ArrayList<>();
		for (EquipableItem i : oldPack.equippedItems)
			this.equippedItems.add(i);
		for (Item i : oldPack.itemBag)
			this.itemBag.add(i);
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
	 * Equips the item and apply its effects to the player
	 *
	 * @param p
	 * @param i
	 * @throws BackPackException
	 *             if the max number of equipable items has been reached already
	 */
	public void equipItem(Player p, EquipableItem i) throws BackPackException {
		if (equippedItems.size() >= equippedItemsSize)
			throw new BackPackException("Can't equip more than the specifed amount of items");
		itemBag.remove(i);
		equippedItems.add(i);
		i.applyEffect(p);
	}

	/**
	 * Uses the item.
	 * 
	 * @param p
	 *            the player who has the item and which the item should be used on
	 * @param item
	 */
	public void useItem(Player p, UsableItem item) {
		item.use(p);
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
	public void unequipItem(Player p, EquipableItem i) throws BackPackException {
		if (itemBag.size() >= packSize)
			throw new BackPackException("Can't unequip item if bag is full");
		equippedItems.remove(i);
		itemBag.add(i);
		i.removeEffect(p);
	}

	/**
	 * Destroys an item which is not currently equipped. After an Item is destroyed, it can no longer be gotten back.
	 *
	 * @param i
	 *            the item to be destroyed, items which are currently equipped
	 *            cannot be destroyed.
	 */
	public void destroyItem(Item i) {
		itemBag.remove(i);
	}

}