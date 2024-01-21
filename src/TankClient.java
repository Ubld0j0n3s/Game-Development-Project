
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TankClient extends Frame implements ActionListener {
	// declare variables
	private static final long serialVersionUID = 1L;
	public static final int Fram_width = 800;
	public static final int Fram_length = 600;
	public static boolean printable = true;
	private Clip loseClip;
	private Clip clip;
	private Clip heartClip;
	private Clip winClip;

	// All features of menu but initialize in null
	MenuBar jmb = null;
	Menu jm1 = null, jm2 = null, jm3 = null, jm4 = null;
	MenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null, jmi5 = null,
			jmi6 = null, jmi7 = null, jmi8 = null, jmi9 = null, jmi10 = null, jmi11 = null;

	Image screenImage = null;
	// The position of the player tanks
	Tank homeTank = new Tank(210, 560, true, Direction.STOP, this, 1);
	Tank homeTank2 = new Tank(480, 560, true, Direction.STOP, this, 2);
	Boolean Player2 = false;

	// The health power ups
	GetBlood blood = new GetBlood();

	// The position of the 2 home base
	Home home = new Home(273, 557, this);
	Home home2 = new Home(413, 557, this);

	// Initialize if win or lose the game
	Boolean win = false, lose = false;

	// Using Arraylist Data Structure
	List<Home> homes = new ArrayList<Home>();
	List<River> theRiver = new ArrayList<River>();
	List<Tank> tanks = new ArrayList<Tank>();
	List<BombTank> bombTanks = new ArrayList<BombTank>();
	List<Bullets> bullets = new ArrayList<Bullets>();
	List<Tree> trees = new ArrayList<Tree>();
	List<CommonWall> homeWall = new ArrayList<CommonWall>();
	List<CommonWall> otherWall = new ArrayList<CommonWall>();
	List<MetalWall> metalWall = new ArrayList<MetalWall>();

	// Main Method of tank client
	public TankClient() {

		// Set of the Menu bar
		jmb = new MenuBar();

		// Set of the Menu that the user can interacts
		jm1 = new Menu("Game");
		jm2 = new Menu("Setting");
		jm3 = new Menu("Help");
		jm4 = new Menu("Level");

		// The Font Style of Menu
		jm1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jm2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jm3.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jm4.setFont(new Font("Times New Roman", Font.BOLD, 15));

		// Set the Menu Items of Game
		jmi1 = new MenuItem("New Game");
		jmi10 = new MenuItem("MultiPlayer");
		jmi2 = new MenuItem("Exit");

		// Set the Menu Items of Settings
		jmi3 = new MenuItem("Pause");
		jmi4 = new MenuItem("Continue");
		jmi11 = new MenuItem("Volume");

		// Set the Menu Items of Help
		jmi5 = new MenuItem("Controls");

		// Set the Menu Items of levels or Mode
		jmi6 = new MenuItem("Easy");
		jmi7 = new MenuItem("Normal");
		jmi8 = new MenuItem("Classic");
		jmi9 = new MenuItem("Expert");

		// Set also the fonts of the menu items
		jmi1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jmi2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jmi3.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jmi4.setFont(new Font("Times New Roman", Font.BOLD, 15));
		jmi5.setFont(new Font("Times New Roman", Font.BOLD, 15));
		// Game
		jm1.add(jmi1);
		jm1.add(jmi10);
		jm1.add(jmi2);
		// Setting
		jm2.add(jmi3);
		jm2.add(jmi4);
		jm2.add(jmi11);
		// Help
		jm3.add(jmi5);
		// Levels
		jm4.add(jmi6);
		jm4.add(jmi7);
		jm4.add(jmi8);
		jm4.add(jmi9);

		// add to menubar
		jmb.add(jm1);
		jmb.add(jm2);
		jmb.add(jm4);
		jmb.add(jm3);

		// Add ActionListener to have functionality those menu features
		jmi1.addActionListener(this);
		jmi1.setActionCommand("NewGame");
		jmi10.addActionListener(this);
		jmi10.setActionCommand("Multiplayer");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Exit");

		jmi3.addActionListener(this);
		jmi3.setActionCommand("Pause");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("Continue");
		jmi5.addActionListener(this);
		jmi5.setActionCommand("Controls");
		jmi11.addActionListener(this);
		jmi11.setActionCommand("Volume");

		jmi6.addActionListener(this);
		jmi6.setActionCommand("Easy");
		jmi7.addActionListener(this);
		jmi7.setActionCommand("Normal");
		jmi8.addActionListener(this);
		jmi8.setActionCommand("Classic");
		jmi9.addActionListener(this);
		jmi9.setActionCommand("Expert");

		// add the Menu bar to the main frame
		this.setMenuBar(jmb);
		this.setVisible(true);

		// the wall to guard the homebase
		for (int i = 0; i < 10; i++) {
			if (i < 4) {
				homeWall.add(new CommonWall(250, 580 - 21 * i, this));
			} else if (i < 7) {
				homeWall.add(new CommonWall(272 + 22 * (i - 4), 517, this));
			} else {
				homeWall.add(new CommonWall(316, 538 + (i - 7) * 21, this));
			}
		}

		// the wall to guard the homebase2
		for (int i = 0; i < 10; i++) {
			if (i < 4) {
				homeWall.add(new CommonWall(390, 580 - 21 * i, this));
			} else if (i < 7) {
				homeWall.add(new CommonWall(411 + 22 * (i - 4), 517, this));
			} else {
				homeWall.add(new CommonWall(455, 538 + (i - 7) * 21, this));
			}
		}

		// the wall boundaries of the game
		for (int i = 0; i < 32; i++) {
			if (i < 16) {
				otherWall.add(new CommonWall(500 + 21 * i, 180, this));
				otherWall.add(new CommonWall(140, 400 + 21 * i, this));
				otherWall.add(new CommonWall(580, 400 + 21 * i, this));
			} else if (i < 32) {
				otherWall.add(new CommonWall(200 + 21 * (i - 16), 320, this));
				otherWall.add(new CommonWall(500 + 21 * (i - 16), 220, this));
				otherWall.add(new CommonWall(162, 400 + 21 * (i - 16), this));
				otherWall.add(new CommonWall(560, 400 + 21 * (i - 16), this));
			}
		}

		// also the metal wall
		for (int i = 0; i < 20; i++) {
			if (i < 10) {
				metalWall.add(new MetalWall(140 + 30 * i, 150, this));
				metalWall.add(new MetalWall(602, 400 + 20 * (i), this));
				metalWall.add(new MetalWall(100, 400 + 20 * (i), this));
			} else if (i < 20)
				metalWall.add(new MetalWall(140 + 30 * (i - 10), 180, this));
		}

		// also the trees or grass
		for (int i = 0; i < 4; i++) {
			if (i < 4) {
				trees.add(new Tree(0 + 30 * i, 360, this));
				trees.add(new Tree(220 + 30 * i, 360, this));
				trees.add(new Tree(440 + 30 * i, 360, this));
				trees.add(new Tree(660 + 30 * i, 360, this));
			}

		}

		// also the river
		theRiver.add(new River(85, 150, this));
		theRiver.add(new River(445, 150, this));

		// add the homebase 2
		homes.add(new Home(413, 557, this));
		homes.add(home2);

		// number or size of enemy tanks
		for (int i = 0; i < 20; i++) {
			if (i < 9)
				tanks.add(new Tank(150 + 70 * i, 40, false, Direction.D, this, 0));
			else if (i < 15)
				tanks.add(new Tank(700, 140 + 50 * (i - 6), false, Direction.D,
						this, 0));
			else
				tanks.add(new Tank(10, 50 * (i - 12), false, Direction.D,
						this, 0));
		}

		this.setSize(Fram_width, Fram_length);
		this.setLocation(280, 50);
		this.setTitle("Battle City");

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Replace with the actual path to your music file
		playMusic("Sounds\\4 - Track 4.wav");
		winMusic();
		heartMusic();
		// Replace with the actual path to your music file
		loseMusic("Sounds\\game-over-arcade-6435.wav");

		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.setVisible(true);

		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start();

	}

	// method to draw the off screen image
	public void update(Graphics g) {

		screenImage = this.createImage(Fram_width, Fram_length);

		Graphics gps = screenImage.getGraphics();
		Color c = gps.getColor();
		gps.setColor(Color.BLACK);
		gps.fillRect(0, 0, Fram_width, Fram_length);
		gps.setColor(c);
		framPaint(gps);
		g.drawImage(screenImage, 0, 0, null);
	}

	// method of draw all the characters, power ups, and notifacation
	public void framPaint(Graphics g) {

		Color c = g.getColor();
		g.setColor(Color.green);

		// it display the text with font style of tanks remaining and health
		Font f1 = g.getFont();
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		if (!Player2)
			g.drawString("Tanks Remaining: ", 200, 70);
		else
			g.drawString("Tanks Remaining: ", 100, 70);
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		if (!Player2)
			g.drawString("" + tanks.size(), 355, 70);
		else
			g.drawString("" + tanks.size(), 300, 70);
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		if (!Player2)
			g.drawString("Health: ", 580, 70);
		else
			g.drawString("Health: ", 380, 70);
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		if (!Player2)
			g.drawString("" + homeTank.getLife(), 650, 70);
		else
			g.drawString("Player1: " + homeTank.getLife() + " Player2:" +
					homeTank2.getLife(), 450, 70);
		g.setFont(f1);

		// these moments if the player wins the game
		if (!Player2) { // only 1 player
			if (tanks.size() == 0 && home.isLive() && homeTank.isLive() && lose == false) {
				if (winClip.isRunning()) {
					winClip.stop();
				}
				// after the game was win all the bricks will clear
				this.otherWall.clear();
				this.metalWall.clear();
				this.theRiver.clear();
				this.trees.clear();
				this.homeWall.clear();
				win = true;
				clip.stop();
				playwinMusic();
				// Option when the game will win
				printable = false;
				Object[] options = { "Confirm", "Cancel" };
				int response = JOptionPane.showOptionDialog(this, "Confirm to go next level",
						"Congratulations, You Won",
						JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						options, options[0]);
				if (response == 0) {
					printable = true;
					Object[] options1 = { "Normal", "Classic", "Expert" };
					int response1 = JOptionPane.showOptionDialog(this, "Choose a Level Mode",
							"Congratulations, You Won",
							JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							options1, options1[0]);
					if (response1 == 0) { // Normal
						printable = true;
						Tank.count = 20;
						Tank.speedX = 8;
						Tank.speedY = 8;
						Bullets.speedX = 10;
						Bullets.speedY = 10;
						this.dispose();
						new TankClient();
						System.out.println("TankClient instantiated Normal Mode");

					} else if (response1 == 1) {// Classic
						loseClip.stop();
						Tank.count = 10;
						Tank.speedX = 12;
						Tank.speedY = 12;
						Bullets.speedX = 16;
						Bullets.speedY = 16;
						this.dispose();
						new TankClient();
						System.out.println("TankClient instantiated Classic Mode");
					} else if (response1 == 2) {// Expert
						Tank.count = 10;
						Tank.speedX = 16;
						Tank.speedY = 16;
						Bullets.speedX = 18;
						Bullets.speedY = 18;
						this.dispose();
						new TankClient();
						System.out.println("TankClient instantiated Expert Mode");
					}
				} else {
					clip.stop();
					loseClip.stop();
					printable = true;
					this.setVisible(false);
					new Thread(new PaintThread()).start();
					new MainGameFrame();

				}
			}
			// The game is lose
			if (homeTank.isLive() == false && win == false) {
				if (loseClip.isRunning()) {
					loseClip.stop();
				}
				// after the game was lose all the bricks all the bricks will clear
				otherWall.clear();
				metalWall.clear();
				theRiver.clear();
				trees.clear();
				tanks.clear();
				homeWall.clear();
				lose = true;
				clip.stop();
				playloseMusic();
				// Option after the game is lose
				printable = false;
				Object[] options = { "Confirm", "Cancel" };
				int response = JOptionPane.showOptionDialog(this, "Confirm to start a new game?", "Game Over",
						JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						options, options[0]);
				if (response == 0) {

					printable = true;
					this.dispose();
					loseClip.stop();
					new TankClient();
				} else {
					// back to the main frame
					clip.stop();
					loseClip.stop();
					printable = true;
					this.setVisible(false);
					new Thread(new PaintThread()).start();
					new MainGameFrame();

				}
			}

		}
		// if the game is multiplayer and wins the game
		else {
			if (tanks.size() == 0 && home.isLive() && (homeTank.isLive() && homeTank2.isLive()) && lose == false) {
				if (winClip.isRunning()) {
					winClip.stop();
				}
				// after the game was win all the bricks will clear
				this.otherWall.clear();
				this.metalWall.clear();
				this.theRiver.clear();
				this.trees.clear();
				this.homeWall.clear();
				win = true;
				clip.stop();
				playwinMusic();
				// if the game is win
				printable = false;
				Object[] options = { "Confirm", "Cancel" };
				int response = JOptionPane.showOptionDialog(this, "Confirm to go next level",
						"Congratulations, You Won",
						JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						options, options[0]);
				if (response == 0) {
					printable = true;
					Object[] options1 = { "Normal", "Classic", "Expert" };
					int response1 = JOptionPane.showOptionDialog(this, "Choose a Level Mode",
							"Congratulations, You Won",
							JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							options1, options1[0]);
					if (response1 == 0) { // Normal
						printable = true;
						Tank.count = 12;
						Tank.speedX = 10;
						Tank.speedY = 10;
						Bullets.speedX = 12;
						Bullets.speedY = 12;
						this.dispose();
						TankClient player2add = new TankClient();
						player2add.Player2 = true;
						System.out.println("TankClient instantiated Normal Mode");

					} else if (response1 == 1) {// Classic
						Tank.count = 20;
						Tank.speedX = 14;
						Tank.speedY = 14;
						Bullets.speedX = 16;
						Bullets.speedY = 16;
						this.dispose();
						TankClient player2add = new TankClient();
						player2add.Player2 = true;
						System.out.println("TankClient instantiated Classic Mode");

					} else if (response1 == 2) {// Expert
						Tank.count = 20;
						Tank.speedX = 16;
						Tank.speedY = 16;
						Bullets.speedX = 18;
						Bullets.speedY = 18;
						this.dispose();
						TankClient player2add = new TankClient();
						player2add.Player2 = true;
						System.out.println("TankClient instantiated Expert Mode");
					}
				} else {
					// back to the main frame
					clip.stop();
					loseClip.stop();
					printable = true;
					this.setVisible(false);
					new Thread(new PaintThread()).start();
					new MainGameFrame();

				}
			}

			// the game is lose
			if (homeTank.isLive() == false || homeTank2.isLive() == false && win == false) {
				if (loseClip.isRunning()) {
					loseClip.stop();
				}
				// after the game is lose all the bricks will clear
				tanks.clear();
				bullets.clear();
				homeWall.clear();
				lose = true;
				clip.stop();
				playloseMusic();
				// option if the game is lose
				printable = false;
				Object[] options = { "Confirm", "Cancel" };
				int response = JOptionPane.showOptionDialog(this, "Confirm to start a new game?", "Game Over",
						JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						options, options[0]);
				if (response == 0) {

					printable = true;
					this.dispose();
					TankClient player2add = new TankClient();
					player2add.Player2 = true;
				} else {
					// back to the main frame
					clip.stop();
					loseClip.stop();
					printable = true;
					this.setVisible(false);
					new Thread(new PaintThread()).start();
					new MainGameFrame();
				}
			}
		}
		g.setColor(c);

		// implement those river adding in the main method to draw it in the frame
		for (int i = 0; i < theRiver.size(); i++) {
			River r = theRiver.get(i);
			r.draw(g);
		}

		// collision of the river to the player tanks
		for (int i = 0; i < theRiver.size(); i++) {
			River r = theRiver.get(i);
			homeTank.collideRiver(r);
			if (Player2)
				homeTank2.collideRiver(r);
			r.draw(g);
		}

		// implement those home adding in the main method to draw it in the frame
		for (int i = 0; i < homes.size(); i++) {
			Home h = homes.get(i);
			homeTank.collideHome(h);
			homeTank2.collideHome(h);
			h.draw(g);
		}

		home.draw(g);
		homeTank.draw(g);
		if (heartClip.isRunning()) {
			heartClip.stop();
		}

		// if the player eat the power ups and theres a music
		if (homeTank.eat(blood)) {
			playheartMusic();
		}

		if (Player2) {
			homeTank2.draw(g);
			if (homeTank2.eat(blood)) {
				playheartMusic();
			}
		}

		// implement those bullets adding in the main method to draw it in the frame
		for (int i = 0; i < bullets.size(); i++) {
			Bullets m = bullets.get(i);
			m.hitTanks(tanks);
			m.hitTank(homeTank);
			m.hitTank(homeTank2);
			m.hitHome();
			m.hitHome2();

			for (int j = 0; j < bullets.size(); j++) {
				if (i == j)
					continue;
				Bullets bts = bullets.get(j);
				m.hitBullet(bts);
			}

			for (int j = 0; j < metalWall.size(); j++) {
				MetalWall mw = metalWall.get(j);
				m.hitWall(mw);
			}

			for (int j = 0; j < otherWall.size(); j++) {
				CommonWall w = otherWall.get(j);
				m.hitWall(w);
			}

			for (int j = 0; j < homeWall.size(); j++) {
				CommonWall cw = homeWall.get(j);
				m.hitWall(cw);
			}
			m.draw(g);
		}

		// implement those tanks but theres collision of its bricks
		// adding in the main method to draw it in the frame
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);

			for (int j = 0; j < homeWall.size(); j++) {
				CommonWall cw = homeWall.get(j);
				t.collideWithWall(cw);
				cw.draw(g);
			}
			for (int j = 0; j < otherWall.size(); j++) {
				CommonWall cw = otherWall.get(j);
				t.collideWithWall(cw);
				cw.draw(g);
			}
			for (int j = 0; j < metalWall.size(); j++) {
				MetalWall mw = metalWall.get(j);
				t.collideWithWall(mw);
				mw.draw(g);
			}
			for (int j = 0; j < theRiver.size(); j++) {
				River r = theRiver.get(j);
				t.collideRiver(r);
				r.draw(g);
			}

			// collision of tanks to tanks
			t.collideWithTanks(tanks);
			t.collideHome(home);

			t.draw(g);
		}

		// draw the health power ups to the game
		blood.draw(g);

		// implement those bricks to draw to the frame
		for (int i = 0; i < trees.size(); i++) {
			Tree tr = trees.get(i);
			tr.draw(g);
		}

		for (int i = 0; i < bombTanks.size(); i++) {
			BombTank bt = bombTanks.get(i);
			bt.draw(g);
		}

		for (int i = 0; i < otherWall.size(); i++) {
			CommonWall cw = otherWall.get(i);
			cw.draw(g);
		}

		for (int i = 0; i < metalWall.size(); i++) {
			MetalWall mw = metalWall.get(i);
			mw.draw(g);
		}

		// also collision of tanks of all the bricks
		homeTank.collideWithTanks(tanks);
		homeTank.collideHome(home);
		if (Player2) {
			homeTank2.collideWithTanks(tanks);
			homeTank2.collideHome(home);
		}

		for (int i = 0; i < metalWall.size(); i++) {
			MetalWall w = metalWall.get(i);
			homeTank.collideWithWall(w);
			if (Player2)
				homeTank2.collideWithWall(w);
			w.draw(g);
		}

		for (int i = 0; i < otherWall.size(); i++) {
			CommonWall cw = otherWall.get(i);
			homeTank.collideWithWall(cw);
			if (Player2)
				homeTank2.collideWithWall(cw);
			cw.draw(g);
		}

		for (int i = 0; i < homeWall.size(); i++) {
			CommonWall w = homeWall.get(i);
			homeTank.collideWithWall(w);
			if (Player2)
				homeTank2.collideWithWall(w);
			w.draw(g);
		}

	}

	// the game is printable or display to the frame
	private class PaintThread implements Runnable {
		public void run() {
			while (printable) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			homeTank.keyReleased(e);
			homeTank2.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			homeTank.keyPressed(e);
			homeTank2.keyPressed(e);
		}

	}

	// set the volume of the music
	private void setVolume(Clip clip, float volume) {
		try {
			if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				float range = gainControl.getMaximum() - gainControl.getMinimum();

				// Ensure the volume is within a reasonable range (0.0 to 1.0)
				volume = Math.max(0.0f, Math.min(1.0f, volume));

				// Calculate the gain value within the range
				float gain = (range * volume) + gainControl.getMinimum();

				// Set the gain value, and stop playback if the volume is set to 0
				if (volume > 0.0f) {
					gainControl.setValue(gain);
					clip.start(); // Ensure playback is started
				} else {
					clip.stop();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// to specific what type of muscic to adjust
	private void showVolumeInputDialog(String title, Clip clip) {
		if (clip == null) {
			return;
		}

		JSlider volumeSlider = createSlider();
		volumeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				float volume = volumeSlider.getValue() / 20.0f;
				setVolume(clip, volume);
			}
		});

		JOptionPane.showOptionDialog(
				null,
				volumeSlider,
				title,
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				new Object[] {},
				null);
	}

	// Java Slider
	private JSlider createSlider() {
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		return slider;
	}

	// music background of playing the game
	private void playMusic(String filePath) {
		try {
			AudioInputStream intro = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(intro);
			clip.start();
		} catch (Exception e) {
			System.out.println("Error playing music: " + e.getMessage());
		}
	}

	// music background of winning the game
	private void winMusic() {
		try {
			File winMusicFile = new File(
					"Sounds\\mixkit-video-game-win-2016.wav");
			AudioInputStream intro = AudioSystem.getAudioInputStream(winMusicFile);
			winClip = AudioSystem.getClip();
			winClip.open(intro);
		} catch (Exception e) {
			System.out.println("Error playing music: " + e.getMessage());
		}
	}

	// music if the player get the power ups
	private void heartMusic() {
		try {
			File heartMusicFile = new File("Sounds\\SFX 4.wav");
			AudioInputStream intro = AudioSystem.getAudioInputStream(heartMusicFile);
			heartClip = AudioSystem.getClip();
			heartClip.open(intro);
		} catch (Exception e) {
			System.out.println("Error playing music: " + e.getMessage());
		}
	}

	// music background if the game is lose
	private void loseMusic(String filepath) {
		try {
			AudioInputStream intro = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
			loseClip = AudioSystem.getClip();
			loseClip.open(intro);
		} catch (Exception e) {
			System.out.println("Error playing music: " + e.getMessage());
		}
	}

	private void playwinMusic() {
		if (!winClip.isRunning()) {
			winClip.start();
		}
	}

	private void playheartMusic() {
		if (!heartClip.isRunning()) {
			heartClip.start();
		}
	}

	private void playloseMusic() {
		if (!loseClip.isRunning()) {
			loseClip.start();
		}
	}

	public void actionPerformed(ActionEvent e) {

		// Action Command if the user click the new game
		if (e.getActionCommand().equals("NewGame")) {
			clip.stop();
			printable = false;
			Object[] options = { "Confirm", "Cancel" };
			int response = JOptionPane.showOptionDialog(this, "Confirm to start a new game?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {

				printable = true;
				this.dispose();
				new TankClient();
			} else {
				printable = true;
				new Thread(new PaintThread()).start();
			}

			// Action Command if the user click the pause
		} else if (e.getActionCommand().endsWith("Pause")) {
			clip.stop();
			printable = false;

			// Action Command if the user click the Continue
		} else if (e.getActionCommand().equals("Continue")) {
			clip.start();
			if (!printable) {
				printable = true;
				new Thread(new PaintThread()).start();
			}

			// Action Command if the user click the Exit
		} else if (e.getActionCommand().equals("Exit")) {
			printable = false;
			Object[] options = { "Confirm", "Cancel" };
			int response = JOptionPane.showOptionDialog(this, "Confirm to exit?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				System.out.println("break down");
				System.exit(0);
			} else {
				printable = true;
				new Thread(new PaintThread()).start();

			}

			// Action Command if the user click the Multiplayer
		} else if (e.getActionCommand().equals("Multiplayer")) {
			clip.stop();
			printable = false;
			Object[] options = { "Confirm", "Cancel" };
			int response = JOptionPane.showOptionDialog(this, "Confirm to add player?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				printable = true;
				this.dispose();
				TankClient Player2add = new TankClient();
				Player2add.Player2 = true;
			} else {
				printable = true;
				new Thread(new PaintThread()).start();
			}

			// Action Command if the user click the Volume
		} else if (e.getActionCommand().equals("Volume")) {
			printable = false;
			showVolumeInputDialog("Volume", clip);
			this.setVisible(true);
			printable = true;
			new Thread(new PaintThread()).start();

			// Action Command if the user click the Controls
		} else if (e.getActionCommand().equals("Controls")) {
			printable = false;
			JOptionPane.showMessageDialog(null,
					" Controls: \n For Player One : \n [ W : Up ]  [ S : Down ] \n [ A : Left ]  [ D : Right ]  [ Space : fire ] \n \n For Player Two : \n [ UpArrow : Up ]  [ DownArrow : Down ] \n [ LeftArrow : Left ]  [ RightArrow : Right ]  [ Enter : fire ] \n \n For restart [ R ]     Enjoy The GAME !!",
					"Controls", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(true);
			printable = true;
			new Thread(new PaintThread()).start();

			// Action Command if the user click the easy
		} else if (e.getActionCommand().equals("Easy")) {
			for (int i = 0; i < 20; i++) {
				if (i < 9)
					tanks.add(new Tank(150 + 70 * i, 40, false, Direction.D, this, 0));
				else if (i < 15)
					tanks.add(new Tank(700, 140 + 50 * (i - 6), false, Direction.D,
							this, 0));
				else
					tanks
							.add(new Tank(10, 50 * (i - 12), false, Direction.D,
									this, 0));
			}
			clip.stop();
			Tank.count = 20;
			Tank.speedX = 6;
			Tank.speedY = 6;
			Bullets.speedX = 10;
			Bullets.speedY = 10;
			this.dispose();
			new TankClient();

			// Action Command if the user click the normal
		} else if (e.getActionCommand().equals("Normal")) {
			for (int i = 0; i < 20; i++) {
				if (i < 9)
					tanks.add(new Tank(150 + 70 * i, 40, false, Direction.D, this, 0));
				else if (i < 15)
					tanks.add(new Tank(700, 140 + 50 * (i - 6), false, Direction.D,
							this, 0));
				else
					tanks
							.add(new Tank(10, 50 * (i - 12), false, Direction.D,
									this, 0));
			}
			clip.stop();
			Tank.count = 20;
			Tank.speedX = 10;
			Tank.speedY = 10;
			Bullets.speedX = 12;
			Bullets.speedY = 12;
			this.dispose();
			new TankClient();

			// Action Command if the user click the Classic
		} else if (e.getActionCommand().equals("Classic")) {
			for (int i = 0; i < 10; i++) {
				if (i < 9)
					tanks.add(new Tank(150 + 70 * i, 40, false, Direction.D, this, 0));
				else if (i < 15)
					tanks.add(new Tank(700, 140 + 50 * (i - 6), false, Direction.D,
							this, 0));
				else
					tanks
							.add(new Tank(10, 50 * (i - 12), false, Direction.D,
									this, 0));
			}
			clip.stop();
			Tank.count = 10;
			Tank.speedX = 14;
			Tank.speedY = 14;
			Bullets.speedX = 16;
			Bullets.speedY = 16;
			this.dispose();
			new TankClient();

			// Action Command if the user click the Expert
		} else if (e.getActionCommand().equals("Expert")) {
			for (int i = 0; i < 10; i++) {
				if (i < 9)
					tanks.add(new Tank(150 + 70 * i, 40, false, Direction.D, this, 0));
				else if (i < 15)
					tanks.add(new Tank(700, 140 + 50 * (i - 6), false, Direction.D,
							this, 0));
				else
					tanks
							.add(new Tank(10, 50 * (i - 12), false, Direction.D,
									this, 0));
			}
			clip.stop();
			Tank.count = 10;
			Tank.speedX = 18;
			Tank.speedY = 18;
			Bullets.speedX = 18;
			Bullets.speedY = 18;
			this.dispose();
			new TankClient();
		}
	}
}
