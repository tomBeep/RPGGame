package items;

import player.Player;

/**
 * A Simple HP boosting item, that boosts maximum HP when equipped.
 * 
 * @author Thomas Edwards
 *
 */
public class SimpleHPItem extends SimpleAbstractItem implements EquipableItem {

	private int effectAmount;

	public SimpleHPItem(String name, String description, int price, Rarity rarity, int effectAmount) {
		super(name, description, price, rarity);
		this.effectAmount = effectAmount;
	}

	@Override
	public void applyEffect(Player p) {
		p.getHealth().increaseMaxAmount(effectAmount);
	}

	@Override
	public void removeEffect(Player p) {
		p.getHealth().decreaseMaxAmount(effectAmount);
	}

}