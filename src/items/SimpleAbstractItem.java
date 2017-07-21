package items;

public abstract class SimpleAbstractItem implements Item {
	private String name, description;
	private int price;
	private Rarity rarity;

	public SimpleAbstractItem(String name, String description, int price, Rarity rarity) {
		this.name = name;
		this.description = description;
		this.price = price;
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
	public boolean equals(Object other) {
		if (!(other instanceof SimpleAbstractItem))
			return false;
		SimpleAbstractItem itm = (SimpleAbstractItem) other;
		if (itm.name.equals(this.name) && itm.rarity == this.rarity && this.price == itm.price)
			return true;
		return false;
	}
}
