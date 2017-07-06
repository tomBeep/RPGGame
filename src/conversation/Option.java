package conversation;

/**
 * An Option within a conversation segment
 * 
 * @author Thomas Edwards
 *
 */
public class Option {
	private ChoiceAction action;
	private String text;
	private ConversationSegment next;

	/**
	 * @param optionText
	 * @param action
	 *            null if no action to be performed for making this choice.
	 * @param next
	 *            the next segment in this conversation
	 */
	public Option(String optionText, ChoiceAction action, ConversationSegment next) {
		this.text = optionText;
		this.action = action;
		this.next = next;
	}

	public void setNext(ConversationSegment next) {
		this.next = next;
	}

	public String getText() {
		return text;
	}

	public ConversationSegment getNext() {
		return next;
	}

	/**
	 * Applies the action (if any) associated with this choice and returns the next segment or Null if end of convo
	 * 
	 * @return the next Conversation Segment
	 */
	public ConversationSegment apply() {
		if (action != null)
			action.doAction();
		return next;
	}

	@Override
	public String toString() {
		return this.text;
	}

}
