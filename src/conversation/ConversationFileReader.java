package conversation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

public class ConversationFileReader {

	public static ConversationSegment loadFile(String filename) {
		try {
			File file = new File(filename);
			Scanner sc = new Scanner(file);
			// reads all of a segments information
			sc.useDelimiter(":");
			String id = sc.next();
			String SegmentLines = sc.next();
			String[] options = sc.next().split("->");

			// now need to recreate the tree from each segment

			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Saves a conversation into a text file. Each Segment is written into the text file in the forms:
	 * :Segment.ToString() : AllofSegmentLines (each line separated by a new line character) :
	 * OptionText->ActionID->nextSegmentToString()->...Option2... (nextSegment will read 'NULL' if option does not lead
	 * to another segment)-->Option3 -> :
	 * 
	 * @param root
	 * @param filename
	 */
	public static void saveConversation(ConversationSegment root, String filename) {
		try {
			File file = new File(filename);
			FileWriter f = new FileWriter(file);
			Stack<ConversationSegment> stack = new Stack<>();
			HashSet<ConversationSegment> set = new HashSet<>();
			stack.push(root);
			while (!stack.isEmpty()) {
				ConversationSegment current = stack.pop();
				if (set.contains(current))
					continue;
				set.add(current);
				f.write(":" + current.toString() + ":");
				for (String s : current.getLines())
					f.write(s + "\n");
				f.write(":");
				for (int i = 0; i < 4; i++) {
					Option o = current.getOptions()[i];
					if (o != null) {
						if (o.getNext() != null) {
							f.write(o.getText() + "->" + getID(o.getAction()) + "->" + o.getNext().toString() + "->");
							stack.push(o.getNext());
						} else
							f.write(o.getText() + "->" + getID(o.getAction()) + "->" + "NULL" + "->");
					}
				}
			}
			f.close();
			System.out.println("Saved successfully in " + file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param a
	 *            the Choice action for this segment
	 * @return the ID if the choice Action, depending on the type of choice action, different choice actions return
	 *         different ID's
	 */
	public static String getID(ChoiceAction a) {
		return "0000";
	}
}
