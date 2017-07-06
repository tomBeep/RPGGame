package conversation;

@FunctionalInterface
interface ChoiceAction {
	/**
	 * This method should do the corresponding action associated with making the choice. Alot of choices will have no
	 * lasting consequences and thus doAction() can simply be null.
	 */
	public void doAction();


}
