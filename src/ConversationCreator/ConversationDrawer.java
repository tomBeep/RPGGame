package ConversationCreator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;

import conversation.ConversationSegment;
import conversation.Option;

/**
 * Class responsible for drawing conversations onto a graphics panel.
 * Also contains the method getSegment() which can take a mouse click x,y and provide the segment that was drawn there
 * 
 * @author Thomas Edwards
 *
 */
public class ConversationDrawer {
	private Graphics2D g;
	private ConversationSegment root;
	private HashMap<Rectangle, ConversationSegment> mouseMap;// rectangle represents the segment
	private int leafX;
	private final int rectWidth = 160, rectHeight = 70, leafDX = rectWidth + 20, leafDY = rectHeight + 20;

	public ConversationDrawer(Graphics g, ConversationSegment root) {
		this.g = (Graphics2D) g;
		this.root = root;
	}

	/**
	 * @param x
	 * @param y
	 * @return the segment at location x,y or null if the location contains no segment.
	 */
	public ConversationSegment getSegment(int x, int y) {
		for (Rectangle r : mouseMap.keySet()) {
			if (r.contains(x, y)) {
				return mouseMap.get(r);
			}
		}
		return null;
	}

	/**
	 * Recurssivly draws the conversation loaded into this object.
	 * 
	 * @param current
	 *            the current segment selected (will be drawn in blue)
	 */
	public void draw(ConversationSegment current) {
		mouseMap = new HashMap<>();
		leafX = 20;
		drawSegment(50, 50, root, current);
	}

	/**
	 * Recurssivley draws out all the segments and lines.
	 * 
	 * @param x
	 * @param y
	 * @param s
	 * @param current
	 * @return the x position of where this segment was drawn.
	 */
	private int drawSegment(int x, int y, ConversationSegment s, ConversationSegment current) {
		if (s.getOptions() == null || s.getOptions()[0] == null) {// if segment is a leaf
			x = leafX;
			leafX += leafDX;
		} else {// if segment is not a leaf.
			int childWidths = 0, counter = 0;
			int[] childPoints = new int[4];// records the x positions of each child.
			for (int i = 0; i < 4; i++) {
				Option o = s.getOptions()[i];
				if (o != null) {
					ConversationSegment c = o.getNext();
					if (c != null) {
						int childX = drawSegment(x, y + leafDY, c, current);
						childWidths += childX;// sums up the childX positions
						childPoints[i] = childX;
						counter++;
					}
				}
			}
			x = (int) (childWidths / counter);// bases this x as average of child x

			// now draw Lines to Children.
			drawChildLines(x, y, counter, childPoints);
		}
		if (s == current)
			g.setColor(Color.blue);// selected Segment is blue
		else
			g.setColor(Color.black);

		// draws the rectangle representing the segment.
		g.drawRect(x, y, rectWidth, rectHeight);
		// draws the little OptionRectangles
		drawOptionRects(x, y, s);
		// draws a summary String
		drawSummary(x, y, s);

		// records the clickable area of this segment
		Rectangle area = new Rectangle(x, y, rectWidth, rectHeight);
		mouseMap.put(area, s);

		return x;
	}

	private void drawChildLines(int x, int y, int counter, int[] childPoints) {
		g.setColor(Color.red);
		counter--;
		while (counter >= 0) {
			int childX = childPoints[counter];
			int childY = y + leafDY;
			g.drawLine(20 + x + counter * 40, y + 62, childX, childY);
			g.fillOval(childX - 4, childY - 4, 8, 8);
			counter--;
		}
	}

	private void drawSummary(int x, int y, ConversationSegment s) {
		g.setFont(new Font("Courier", 0, 12));
		String summary = s.getSummary();
		if (summary != null) {
			int end1, end2, end3;
			end1 = summary.length() < 22 ? summary.length() : 22;
			end2 = summary.length() < 44 ? summary.length() : 44;
			end3 = summary.length() < 66 ? summary.length() : 66;
			g.drawString(summary.substring(0, end1), x + 2, y + 16);
			g.drawString(summary.substring(end1, end2), x + 2, y + 36);
			g.drawString(summary.substring(end2, end3), x + 2, y + 52);
		}
	}

	private void drawOptionRects(int x, int y, ConversationSegment s) {
		for (int i = 0; i < 4; i++) {
			if (s.getOptions()[i] == null)
				g.drawRect(x + i * 40, y + 55, 40, 15);
			else
				g.fillRect(x + i * 40 + 1, y + 56, 38, 13);
		}
	}

}
