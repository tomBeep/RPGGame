package items;

import player.Player;

/**
 * Implies that this Item can be used, either as a one time or a many time effect
 * 
 * @author Thomas Edwards
 *
 */
public interface UsableItem extends Item {

	public void use(Player p);
}
