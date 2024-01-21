
import java.awt.*;

public class CommonWall {

	// declare variables
	public static final int width = 22;
	public static final int length = 21;
	int x, y;
	TankClient tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] {
				tk.getImage(CommonWall.class.getResource("Images/commonWall.gif")), };
	}

	// constructor
	public CommonWall(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	// draw the wall image to the game frame
	public void draw(Graphics g) {
		g.drawImage(wallImags[0], x, y, null);
	}

	// rectangular bounderies
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}
}
