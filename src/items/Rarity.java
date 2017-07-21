package items;

import java.awt.Color;

public enum Rarity {
	BRONZE, SILVER, GOLD, EPIC, QUEST;

	private Color getColor() {
		switch (this) {
		case BRONZE:
			return new Color(205, 127, 50);
		case SILVER:
			return new Color(192, 192, 192);
		case GOLD:
			return new Color(255, 215, 0);
		case EPIC:
			return new Color(148, 0, 211);// Purple
		case QUEST:
			return new Color(0, 0, 255);// QUEST Color is blue
		}
		throw new Error("This rarity is not associated with a color");
	}
}
