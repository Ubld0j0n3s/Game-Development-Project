
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Home {

	// declare variables
	private int x, y;
	private TankClient tc;
	public static final int width = 43, length = 43;
	private boolean live = true;

	// Use toolkit to default in order to load the images
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] homeImags = null;
	static {
		// the image
		homeImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("Images/home.jpg")), };
	}

	// create constructor
	public Home(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	// if the game is over
	public void gameOver(Graphics g) {

		tc.tanks.clear();
		tc.metalWall.clear();
		tc.otherWall.clear();
		tc.bombTanks.clear();
		tc.theRiver.clear();
		tc.trees.clear();
		tc.bullets.clear();
		tc.homeTank.setLive(false);
		Color c = g.getColor();
		g.setColor(Color.green);
		Font f = g.getFont();
		g.setFont(new Font(" ", Font.PLAIN, 40));
		g.setFont(f);
		g.setColor(c);

	}

	// draw the home with commonWall
	public void draw(Graphics g) {

		if (live) {
			g.drawImage(homeImags[0], x, y, null);
			for (int i = 0; i < tc.homeWall.size(); i++) {
				CommonWall w = tc.homeWall.get(i);
				w.draw(g);
			}

		} else {
			gameOver(g);

		}
	}

	// getters and setters
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	// use rectangle represent the rectangular boundaries of home
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

}
