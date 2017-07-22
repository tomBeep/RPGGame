package items;

import player.Player;

/**
 * A Simple HP boosting item, that boosts maximum HP when equipped.
 * 
 * @author Thomas Edwards
 *
 */
public class SimpleHPItem extends SimpleAbstractItem implements EquipableItem {

	private int healthIncrease;

	public SimpleHPItem(String name, String description, int price, Rarity rarity, int healthIncrease) {
		super(name, description, price, rarity);
		this.healthIncrease = healthIncrease;
	}

	@Override
	public void applyEffect(Player p) {
		p.getHealth().increaseMaxAmount(healthIncrease);
	}

	@Override
	public void removeEffect(Player p) {
		p.getHealth().decreaseMaxAmount(healthIncrease);
	}

}