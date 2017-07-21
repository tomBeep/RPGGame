package items;

import player.Player;

public class SimpleHPItem implements Item {
	private String name, description;
	private int price;
	private int effectAmount;
	private Rarity rarity;

	public SimpleHPItem(String name, String description, int price, int effectAmount, Rarity rarity) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.effectAmount = effectAmount;
		this.rarity = rarity;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getSellPrice() {
		return price;
	}

	@Override
	public Rarity getRarity() {
		return rarity;
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