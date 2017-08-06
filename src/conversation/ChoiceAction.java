package conversation;

import java.util.HashMap;
import game.Main;

/**
 * Potentially needs to be serializable some how.
 * 
 * @author tomo1_000
 *
 */
@FunctionalInterface
public interface ChoiceAction {

	static HashMap<String, ChoiceAction> choiceActions = new HashMap<String, ChoiceAction>() {
		{
			put("Gain 10 Evil Points", () -> Main.player.setEvilPoints(Main.player.getEvilPoints() + 10));
			put("Gain 20 Evil Points", () -> Main.player.setEvilPoints(Main.player.getEvilPoints() + 20));
			put("Gain 10 Good Points", () -> Main.player.setGoodPoints(Main.player.getGoodPoints() + 10));
			put("Gain 20 Good Points", () -> Main.player.setGoodPoints(Main.player.getGoodPoints() + 20));
			put("Gain 10 Gold", () -> Main.player.setGold(Main.player.getGold() + 10));
			put("Gain 100 Gold", () -> Main.player.setGold(Main.player.getGold() + 100));
			put("Lose 10 Gold", () -> Main.player.setGold(Main.player.getGold() - 10));
			put("Lose 100 Gold", () -> Main.player.setGold(Main.player.getGold() - 100));
		}
	};

	/**
	 * This method should do the corresponding action associated with making the choice. Alot of choices will have no
	 * lasting consequences and thus doAction() can simply be null.
	 */
	public void doAction();

}
