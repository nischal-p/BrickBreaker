/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
    	
    	final JFrame frame = new JFrame("Brick Breaker");
        frame.setLocation(300, 300);
        
        String text = "Hi, welcome to the ultimate brick breaker game!\n"
        		+ "The game is very easy to play. You have a ball, a paddle and the wall of bricks.\n"
        		+ "You want to break that wall using the ball and the paddle. The palddle moves left\n"
        		+ "and right with the LEFT and RIGHT arrow keys. The ball bounces on the  paddle, on\n"
        		+ "the wall, and against the bricks. You have two types of ball that you can switch between\n"
        		+ "between during a game - the green Straight ball and the red Advanced ball.\n"
        		+ "Switch between them using the UP arrow key during the game.\n"
        		+ "1) The Straight ball bounces: i) normally on the edges of the paddle and\n"
        		+ "ii) straght up if it lands on the middle of the paddle.\n"
        		+ "2) The Advance ball bounces: i) normally on the middle of the paddle and\n"
        		+ "ii) inclided either more upwards or more downwards than normal depending on\n"
        		+ "which direction it is coming from while landing on the edges of the paddle.\n"
        		+ "The aim of the game is to move paddle so that the ball bounces on the paddle,\n"
        		+ "bounces up and breaks the bricks and manages to not fall to the unknown abyss below.\n"
        		+ "If the ball touches the bottom of the window, you lose.\n"
        		+ "Play the pre-designed map1 and map2, or challenge yourself with a random wall layout.\n"
        		+ "You can also save brick layouts currently being played to come back to it and load it later on."
        		+ "\n"
        		+ "Break all the bricks to win!\n";
        JOptionPane.showMessageDialog(frame, text);


        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.WEST);
        final JLabel status = new JLabel("Running...");
        status_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        status_panel.setBackground(GameCourt.GOOD_GREEN);
        status_panel.add(status);
        
        final JPanel tiles_left_panel = new JPanel();
        tiles_left_panel.setLayout(new BoxLayout(tiles_left_panel, BoxLayout.Y_AXIS));
        frame.add(tiles_left_panel, BorderLayout.EAST);
        final JLabel tilesLeftLabel = new JLabel("<html>" + "Tiles Left: 0" + "</html>");
        final JLabel ballTypeHeader = new JLabel("Currently using: ");
        final JLabel currentBallType = new JLabel("Advanced Ball");
        tiles_left_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tiles_left_panel.setBackground(GameCourt.GOOD_GREEN);
        tiles_left_panel.add(tilesLeftLabel);
        tiles_left_panel.add(ballTypeHeader);
        tiles_left_panel.add(currentBallType);

        // Main playing area
        final GameCourt court = new GameCourt(status, tilesLeftLabel, currentBallType);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        control_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        control_panel.setBackground(GameCourt.GOOD_GREEN);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        
        final JButton playMap1 = new JButton("Play Map1");
        playMap1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reload(1);
            }
        });
        
        final JButton playMap2 = new JButton("Play Map2");
        playMap2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	court.reload(2);
            }
        });
        
        final JButton playRandomGrid = new JButton("Play Random Grid");
        playRandomGrid.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	court.reload(3);
            }
        });
        
        final JButton loadSavedGame = new JButton("Load Game");
        loadSavedGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	court.reload(4);
            }
        });
        
        final JButton saveGame = new JButton("Save Game");
        saveGame.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed (ActionEvent e) {
        		court.saveGame();
        	}
        });

        control_panel.add(playMap1);
        control_panel.add(playMap2);
        control_panel.add(playRandomGrid);
        control_panel.add(saveGame);
        control_panel.add(loadSavedGame);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reload(1);
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}