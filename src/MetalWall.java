
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class MetalWall {

	// declare variables
	public static final int width = 36;
	public static final int length = 37;
	private int x, y;
	TankClient tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("Images/metalWall.gif")), };
	}

	// create constructor
	public MetalWall(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	// draw the metal wall to the game frame
	public void draw(Graphics g) {
		g.drawImage(wallImags[0], x, y, null);
	}

	// rectangular boundaries of the image
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}
}
