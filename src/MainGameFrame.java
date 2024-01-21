
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class MainGameFrame {
    private JFrame mainGameFrame = new JFrame("Battle City");
    private JPanel mainGamePanel = new JPanel();
    private CardLayout cardLayout = new CardLayout();
    private ImageIcon image;
    private Clip clip;

    // main method
    public MainGameFrame() {
        mainGameFrame.setSize(800, 600);
        mainGameFrame.setLocationRelativeTo(mainGameFrame);
        mainGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background color before adding components
        mainGamePanel.setBackground(Color.BLACK);

        // Use CardLayout to switch between main game panel and instruction panel
        mainGameFrame.setLayout(cardLayout);

        mainGamePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add title image
        try {
            image = new ImageIcon(getClass().getResource("Title.png"));

            Image scaledImage = image.getImage().getScaledInstance(500, 400, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

            JLabel displayTitle = new JLabel(scaledImageIcon);
            mainGamePanel.add(displayTitle);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // Set gridwidth back to 1
            gbc.insets = new Insets(10, 0, 10, 0);
            mainGamePanel.add(displayTitle, gbc);
        } catch (Exception e) {
            System.out.println("Image can't be found");
        }

        // Add menu text buttons
        addMenuText("Play Game", 0, 1, mainGamePanel, GridBagConstraints.NORTH);
        addMenuText("Instruction", 0, 2, mainGamePanel, GridBagConstraints.NORTH);
        addMenuText("About", 0, 3, mainGamePanel, GridBagConstraints.NORTH);

        JPanel instructionPanel = new JPanel();
        instructionPanel.setBackground(Color.BLACK);
        instructionPanel.setLayout(new GridBagLayout());

        addBackButton("Back", 0, 0, instructionPanel, GridBagConstraints.NORTHWEST);

        // image to the instruction panel
        try {
            ImageIcon image1 = new ImageIcon(getClass().getResource("Title.png"));
            Image scaledImage = image1.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
            JLabel displayTitle = new JLabel(scaledImageIcon);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(10, 0, 10, 0);
            gbc.anchor = GridBagConstraints.NORTH; // Align to the top
            gbc.fill = GridBagConstraints.HORIZONTAL;
            instructionPanel.add(displayTitle, gbc);
        } catch (Exception e) {
            System.out.println("Image can't be found");
        }

        // Add instruction content
        JLabel instructionLabel = new JLabel();

        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        instructionLabel.setText("<html>Instruction<p><p>"
                + "Controls:<br>");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER; // Center the label
        gbc.insets = new Insets(0, 330, 0, 0);
        instructionPanel.add(instructionLabel, gbc);

        JLabel instruction2 = new JLabel();
        instruction2.setForeground(Color.WHITE);
        instruction2.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        instruction2.setText(
                "<html>For Player 1: W for move Up, S for move Down, A for move Left, D for move Right, Space for Shoot.<br>\r\n"
                        + "For Player 2: Arrow Up for move Up, Arrow Down for move Down, Arrow Left for move Left, Arrow Right for move Right,\r\n"
                        + "Enter for Shoot.</html>");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Center the label
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 100, 0, 50);
        instructionPanel.add(instruction2, gbc);

        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new GridBagLayout());
        aboutPanel.setBackground(Color.BLACK);

        // Add Back button
        addBackButton("Back", 0, 0, aboutPanel, GridBagConstraints.NORTHWEST);

        try {
            ImageIcon image2 = new ImageIcon(getClass().getResource("Title.png"));
            Image scaledImage = image2.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
            JLabel displayTitle = new JLabel(scaledImageIcon);
            gbc.gridx = 0;
            gbc.gridy = 0; // Set the y-coordinate to 1 to place it below the Back button
            gbc.gridwidth = 2; // Set gridwidth to make it span across both columns
            gbc.insets = new Insets(10, 0, 10, 0);
            gbc.anchor = GridBagConstraints.NORTH; // Align to the top
            gbc.fill = GridBagConstraints.HORIZONTAL;
            aboutPanel.add(displayTitle, gbc);
        } catch (Exception e) {
            System.out.println("Image can't be found");
        }

        // Add about content
        JLabel aboutLabel = new JLabel();
        aboutLabel.setForeground(Color.WHITE);
        aboutLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        aboutLabel.setText("About");

        gbc.gridx = 0;
        gbc.gridy = 1; // Set the y-coordinate to 2 to place it below the title image
        gbc.gridwidth = 1; // Set gridwidth to make it span across both columns
        gbc.insets = new Insets(5, 330, 5, 0);
        gbc.anchor = GridBagConstraints.CENTER; // Center the label
        aboutPanel.add(aboutLabel, gbc);

        JLabel about2 = new JLabel();
        about2.setForeground(Color.WHITE);
        about2.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        about2.setText("<html>Battle city is a classic retro video game that became popular for over three decades.<br>"
                + "Basically, the players character is a lone tank with primary goal of defending their base from enemy tank.<br>"
                + "As the player progress through the game, they will encounter various challenging levels and face off against agile enemy tanks<br>"
                + "Along the way, the player can collect power-ups, adding an exciting element of strategy to the gameplay.</html> ");

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Center the label
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 100, 0, 0);
        aboutPanel.add(about2, gbc);

        // back button
        addBackButton("Back", 0, 0, aboutPanel, GridBagConstraints.NORTHWEST);

        // Replace with the actual path to your music file
        playMusic("Sounds\\1 - Track 1.wav");

        mainGameFrame.add(mainGamePanel, "MainGamePanel");
        mainGameFrame.add(instructionPanel, "InstructionPanel");
        mainGameFrame.add(aboutPanel, "AboutPanel");

        // Show the main game panel initially
        cardLayout.show(mainGameFrame.getContentPane(), "MainGamePanel");

        mainGameFrame.setVisible(true);
    }

    private void addMenuText(String text, int x, int y, Container container, int anchor) {
        JButton menuText = new JButton(text);
        menuText.setBackground(Color.BLACK);
        menuText.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        menuText.setForeground(Color.WHITE); // Set text color to white
        menuText.addActionListener(createMenuActionListener(text));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.anchor = anchor;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.weightx = 1.0; // Make the button expand horizontally
        gbc.weighty = 1.0; // Make the button expand vertically

        container.add(menuText, gbc);
    }

    private void addBackButton(String text, int x, int y, Container container, int anchor) {
        JButton menuText = new JButton(text);
        menuText.setBackground(Color.BLACK);
        menuText.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        menuText.setForeground(Color.WHITE);// Set text color to white
        menuText.setBounds(10, 10, 80, 25);
        menuText.addActionListener(createBackActionListener(text));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.anchor = anchor;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.weightx = 1.0; // Make the button expand horizontally
        gbc.weighty = 1.0; // Make the button expand vertically

        container.add(menuText, gbc);
    }

    private ActionListener createBackActionListener(String text) {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Back")) {
                    cardLayout.show(mainGameFrame.getContentPane(), "MainGamePanel");
                } else {
                    // Handle other menu actions here
                }
            }
        };
    }

    private ActionListener createMenuActionListener(String menuText) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (menuText.equals("Play Game")) {
                    clip.stop();
                    new TankClient();
                    mainGameFrame.setVisible(false);
                } else if (menuText.equals("Instruction")) {
                    // Show the instruction panel
                    cardLayout.show(mainGameFrame.getContentPane(), "InstructionPanel");
                } else if (menuText.equals("About")) {
                    cardLayout.show(mainGameFrame.getContentPane(), "AboutPanel");
                } else {
                    // Handle other menu actions here
                }
            }
        };
    }

    @SuppressWarnings("unused")
    private ActionListener createInstructionActionListener() {
        return createMenuActionListener("Instruction");
    }

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

    public static void main(String[] args) {
        new MainGameFrame();
    }

}
