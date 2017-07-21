package conversation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ConversationFileReader {

	/**
	 * Loads a conversationSegment based on the given filename, will throw an exception if it can't find file or if
	 * something goes wrong with the loading.
	 * 
	 * @param filename
	 * @return the Conversation containing the root segment of this Conversation.
	 * @throws Exception
	 */
	public static ConversationSegment loadFile(String filename) throws Exception {
		// will add the ".conv" if it is not specified in the file name
		if (!filename.endsWith(".conv")) {
			filename += ".conv";
		}

		FileInputStream streamIn = new FileInputStream(filename);
		ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
		ConversationSegment c = (ConversationSegment) objectinputstream.readObject();
		loadPictures(c);
		objectinputstream.close();
		return c;
	}

	/**
	 * Because you cannot save buffered images, the buffered images must be loaded again (Using their filenames) when
	 * the conversation is loaded.
	 * 
	 * @param root
	 * @throws IOException
	 */
	private static void loadPictures(ConversationSegment root) throws IOException {
		root.setPicture(root.getPictureName());// loads the bufferedImage
		for (int i = 0; i < 4; i++) {
			if (root.getOptions()[i] != null && root.getOptions()[i].getNext() != null)
				loadPictures(root.getOptions()[i].getNext());
		}
	}

	/**
	 * Saves the conversation as the specified filename. Will always add a .conv to the end of the filename.
	 * 
	 * @param root
	 * @param filename
	 */
	public static void saveConversation(ConversationSegment root, String filename) {
		if (!filename.endsWith(".conv"))// will add .conv to end of file name if it is not specified
			filename += ".conv";
		try {
			FileOutputStream fout = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(root);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
