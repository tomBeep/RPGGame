package items;

import player.Player;

/**
 * A Simple mana boosting Item that boosts maximum mana when equipped
 * 
 * @author Thomas Edwards
 *
 */
public class SimpleMPItem extends SimpleAbstractItem implements EquipableItem {

	private int manaIncrease;

	public SimpleMPItem(String name, String description, int price, Rarity rarity, int manaIncrease) {
		super(name, description, price, rarity);
		this.manaIncrease = manaIncrease;
	}

	@Override
	public void applyEffect(Player p) {
		p.getMana().increaseMaxAmount(manaIncrease);
	}

	@Override
	public void removeEffect(Player p) {
		p.getMana().decreaseMaxAmount(manaIncrease);
	}

}
