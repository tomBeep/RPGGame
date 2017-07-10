package conversation;

public interface Conversation extends Runnable {

	/**
	 * Starts the conversation and displays the first line of text in the conversation. The conversation is moved
	 * forward via spacebar/option number. The Conversation is run in a separate thread and window. So a new
	 * Conversation should always be made in a new thread.
	 */
	public void start();

}
