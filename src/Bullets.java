
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bullets {
	public static int speedX = 12;
	public static int speedY = 12;

	public static final int width = 10;
	public static final int length = 10;

	private int x, y;
	Direction diretion;

	private boolean good;
	private boolean live = true;

	private TankClient tc;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] bulletImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {
		bulletImages = new Image[] {
				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletL.gif")),

				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletU.gif")),

				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletR.gif")),

				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletD.gif")),

		};

		imgs.put("L", bulletImages[0]);

		imgs.put("U", bulletImages[1]);

		imgs.put("R", bulletImages[2]);

		imgs.put("D", bulletImages[3]);

	}

	public Bullets(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.diretion = dir;
	}

	public Bullets(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, dir);
		this.good = good;
		this.tc = tc;
	}

	private void move() {

		switch (diretion) {
			case L:
				x -= speedX;
				break;

			case U:
				y -= speedY;
				break;

			case R:
				x += speedX;
				break;

			case D:
				y += speedY;
				break;

			case STOP:
				break;
		}

		// checking if the bullets is out of bound of the frame
		if (x < 0 || y < 0 || x > TankClient.Fram_width
				|| y > TankClient.Fram_length) {
			live = false;
		}
	}

	// draw the bullets to the game frame
	public void draw(Graphics g) {
		if (!live) {
			tc.bullets.remove(this);
			return;
		}

		// directions of the bullets
		switch (diretion) {
			case L:
				g.drawImage(imgs.get("L"), x, y, null);
				break;

			case U:
				g.drawImage(imgs.get("U"), x, y, null);
				break;

			case R:
				g.drawImage(imgs.get("R"), x, y, null);
				break;

			case D:
				g.drawImage(imgs.get("D"), x, y, null);
				break;

		}

		move();
	}

	public boolean isLive() {
		return live;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

	// if the bullets hit the enemy tanks
	public boolean hitTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			if (hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}

	// if the bullets hit the player tanks
	public boolean hitTank(Tank t) {

		// Checks if the bounding rectangle of the bullet intersects with the bounding
		// rectangle of
		// the tank. This indicates a collision between the bullet and the tank.
		if (this.live && this.getRect().intersects(t.getRect()) && t.isLive()
				&& this.good != t.isGood()) {

			BombTank e = new BombTank(t.getX(), t.getY(), tc);
			tc.bombTanks.add(e);
			if (t.isGood()) {
				t.setLife(t.getLife() - 50);
				if (t.getLife() <= 0)
					t.setLive(false);
			} else {
				t.setLive(false);
			}

			this.live = false;

			return true;
		}
		return false;
	}

	// if the bullet hit the wall
	public boolean hitWall(CommonWall w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			this.tc.otherWall.remove(w);
			this.tc.homeWall.remove(w);
			return true;
		}
		return false;
	}

	public boolean hitBullet(Bullets w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			this.tc.bullets.remove(w);
			return true;
		}
		return false;
	}

	public boolean hitWall(MetalWall w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}

	public boolean hitHome() {
		if (this.live && this.getRect().intersects(tc.home.getRect())) {
			this.live = false;
			this.tc.home.setLive(false);
			return true;
		}
		return false;
	}

	public boolean hitHome2() {
		if (this.live && this.getRect().intersects(tc.home2.getRect())) {
			this.live = false;
			this.tc.home2.setLive(false);
			return true;
		}
		return false;
	}
}
