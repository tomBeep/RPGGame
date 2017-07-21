package items;

import player.Player;

public interface EquipableItem extends Item {

	public void applyEffect(Player p);

	public void removeEffect(Player p);

}
