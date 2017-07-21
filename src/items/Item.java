package items;

import player.Player;

public interface Item {

	public String getName();

	public String getDescription();

	public int getSellPrice();

	public Rarity getRarity();

	public void applyEffect(Player p);

	public void removeEffect(Player p);

}