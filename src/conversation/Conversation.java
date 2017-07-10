package conversation;

import java.io.Serializable;

public class Conversation implements Serializable {

	private static final long serialVersionUID = 7049718688891317600L;

	private ConversationSegment root;

	public Conversation(ConversationSegment root) {
		this.root = root;
	}

	public ConversationSegment getRoot() {
		return root;
	}

}
