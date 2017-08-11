package conversation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
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
public class ConversationSegment implements Serializable {


	private static final long serialVersionUID = -7265774393992482071L;
	/**
	 * The lines of text displayed prior to the choice
	 */
	private List<String> lines = new ArrayList<String>();
	private Option[] options = new Option[4];
	private int currentLine = 0;// the current line of text that you are on
	private String pictureName;
	private transient BufferedImage picture;

	public ConversationSegment doOption(int optionIndex) {
		return options[optionIndex].doOption();
	}

	/**
	 * @param index
	 * @return true if the option at that index was not null, otherwise false.
	 */
	public boolean checkValidOption(int index) {
		return options[index] != null;
	}

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
	public void addOption(String optionText, String action, ConversationSegment next) {
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

	/**
	 * Adds a line of text to the display, text is added in order
	 * 
	 * @param line
	 */
	public void addLine(String line) {
		lines.add(line);
	}

	public BufferedImage getPicture() {
		return picture;
	}

	public Option[] getOptions() {
		return options;
	}

	/**
	 * @return the next line of text
	 */
	public String getNextLine() {
		if (currentLine >= lines.size())
			return null;
		return lines.get(currentLine++);
	}

	/**
	 * @return all the lines of text.
	 */
	public List<String> getLines() {
		return lines;
	}

	public String getPictureName() {
		return this.pictureName;
	}

	public void clearLines() {
		lines = new ArrayList<String>();
	}

	public void setPicture(String pictureFileName) throws IOException {
		pictureName = pictureFileName;
		if (pictureFileName == null || pictureFileName.equals("")) {
			picture = null;
			return;
		}
		if (pictureFileName.startsWith("ConversationPictures/")) {
			picture = ImageIO.read(new File(pictureFileName));
			return;
		}
		picture = ImageIO.read(new File("ConversationPictures/" + pictureFileName));

	}

	public String getSummary() {
		if (lines.size() >= 1)
			return lines.get(0);
		return null;
	}

}
