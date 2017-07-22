package items;

import player.Player;

/**
 * Means that the item can be equipped.
 * 
 * @author Thomas Edwards
 *
 */
public interface EquipableItem extends Item {

	public void applyEffect(Player p);

	public void removeEffect(Player p);

}
