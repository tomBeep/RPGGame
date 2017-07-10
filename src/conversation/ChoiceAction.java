package conversation;

/**
 * Potentially needs to be serializable some how.
 * 
 * @author tomo1_000
 *
 */
@FunctionalInterface
interface ChoiceAction {
	/**
	 * This method should do the corresponding action associated with making the choice. Alot of choices will have no
	 * lasting consequences and thus doAction() can simply be null.
	 */
	public void doAction();

}
