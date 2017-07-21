package items;

import player.Player;

/**
 * A simple One-Time use HealthPot which restores the given amount of Health.
 * 
 * @author Thomas Edwards
 *
 */
public class SimpleHealthPot extends SimpleAbstractItem implements UsableItem {

	private int healthRestored;

	public SimpleHealthPot(String name, String description, int price, Rarity rarity, int healthRestored) {
		super(name, description, price, rarity);
		this.healthRestored = healthRestored;
	}

	@Override
	public void use(Player p) {
		p.getBackPack().destroyItem(this);
		p.getHealth().increaseCurrentAmount(healthRestored);
	}

}
