package conversation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Contains a segment of conversation which is a number of text lines and up to 4 options. ALl conversations are made up
 * of multiple conversation segments. A conversation comes to an end when all lines have been read and there are no
 * options to choose from.
 * 
 * @author Thomas Edwards
 *
 */
public class ConversationSegment {
	/**
	 * The lines of text displayed prior to the choice
	 */
	private List<String> lines = new ArrayList<String>();
	private Option[] options = new Option[4];
	private int currentLine = 0;// the current line of text that you are on
	private String picture;

	/**
	 * MAX 4 OPTIONS per segment
	 * 
	 * @param optionText
	 *            the text which the option should convey
	 * @param action
	 *            the action to do, null if no action to be done by the choice
	 * @param next
	 *            the next segment to go to if this option is chosen
	 */
	public void addOption(String optionText, ChoiceAction action, ConversationSegment next) {
		if (options[3] != null)
			throw new Error("Can't have more than 4 conversation options in a segment");
		Option o = new Option(optionText, action, next);
		for (int i = 0; i < 4; i++) {
			if (options[i] == null) {
				options[i] = o;
				break;
			}
		}
	}

	public void setPicture(String pictureFileName) {
		this.picture = pictureFileName;
	}

	/**
	 * Adds a line of text to the display, text is added in order
	 * 
	 * @param line
	 */
	public void addLine(String line) {
		lines.add(line);
	}

	Option[] getOptions() {
		return options;
	}

	ConversationSegment doOption(String option) {
		for (Option o : options) {
			if (o.getText().equals(option)) {
				return o.apply();
			}
		}
		throw new Error("Could not find option of the specified name");
	}

	boolean checkValidOption(int index) {
		return options[index] != null;
	}

	ConversationSegment doOption(int optionIndex) {
		return options[optionIndex].apply();
	}

	/**
	 * @return the next line of text
	 */
	String getNextLine() {
		if (currentLine >= lines.size())
			return null;
		return lines.get(currentLine++);
	}

	BufferedImage getPicture() {
		if (picture == null)
			return null;
		try {
			return ImageIO.read(new File("ConversationPictures/" + picture));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
