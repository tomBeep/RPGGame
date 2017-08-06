package conversation;

import java.util.HashMap;

/**
 * Potentially needs to be serializable some how.
 * 
 * @author tomo1_000
 *
 */
@FunctionalInterface
interface ChoiceAction {
	
	static HashMap<String,ChoiceAction> choiceActions = new HashMap<String,ChoiceAction>(){{
		put("Gain 10 Evil Points",null);
		put("Gain 20 Evil Points",null);
		put("Gain 10 Good Points",null);
		put("Gain 20 Good Points",null);
		put("Gain 10 Gold",null);
		put("Gain 100 Gold",null);
		put("Lose 10 Gold",null);
		put("Lose 100 Gold",null);
	}};
	
	/**
	 * This method should do the corresponding action associated with making the choice. Alot of choices will have no
	 * lasting consequences and thus doAction() can simply be null.
	 */
	public void doAction();

}
