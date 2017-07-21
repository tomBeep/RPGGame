package items;

import player.Player;

/**
 * A Simple mana boosting Item that boosts maximum mana when equipped
 * @author Thomas Edwards
 *
 */
public class SimpleMPItem extends SimpleAbstractItem implements EquipableItem {

	private int effectAmount;

	public SimpleMPItem(String name, String description, int price, Rarity rarity, int effectAmount) {
		super(name, description, price, rarity);
		this.effectAmount = effectAmount;
	}

	@Override
	public void applyEffect(Player p) {
		p.getMana().increaseMaxAmount(effectAmount);
	}

	@Override
	public void removeEffect(Player p) {
		p.getMana().decreaseMaxAmount(effectAmount);
	}

}
