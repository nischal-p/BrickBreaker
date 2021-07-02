
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	private Paddle paddle;
	private Ball ball;
	private List<Brick> brickList; // list of un-broken bricks
	private boolean needToChangeBall = false; 
	private int currentBallNo = 2;  // the game starts with Advanced Ball

	public boolean playing = false;
	private JLabel status;
	private JLabel tilesLeft; // displays the number of times remaining
	private JLabel currentBallType; //displays the current ball type being used

	// Game constants
	public static final int COURT_WIDTH = 525;
	public static final int COURT_HEIGHT = 375;
	public static final int SCALING = 25;
	public static final int PADDLE_VELOCITY = 8;
	public static final Color GOOD_GREEN = new Color(93, 187, 99);
	public static final Color LIGHT_BLUE = new Color(58, 190, 255);
	public static final Color LIGHT_NAVY_BLUE = new Color(60, 89, 255);
	public static final Color LIGHT_GRAY = new Color(184, 188, 134);
	public static final String EMPTY_SPACE_SYMBOL = "0";
	public static final String BREAKABLE_BRICK_SYMBOL = "#";
	public static final String UNBREAKABLE_BRICK_SYMBOL = "H";
	public static final String BRICK_MAP_1 = "files/map1.txt";
	public static final String BRICK_MAP_2 = "files/map2.txt";
	public static final String SAVED_BRICK_MAP = "files/savedMap.txt";

	public static final int INTERVAL = 20;

	public GameCourt(JLabel status, JLabel tilesLeft, JLabel currentBallType) {

		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(LIGHT_GRAY);

		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});

		timer.start();
		setFocusable(true);

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (!ball.isLaunched()) {
					ball.launch();
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					paddle.setVx(-PADDLE_VELOCITY);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					paddle.setVx(PADDLE_VELOCITY);
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					needToChangeBall = true;
					if (currentBallNo == 1) {
						currentBallNo = 2;
					} else {
						currentBallNo = 1;
					}
				}
			}

			public void keyReleased(KeyEvent e) {
				paddle.setVx(0);
				paddle.setVy(0);
			}
		});

		this.status = status;
		this.tilesLeft = tilesLeft;
		this.currentBallType = currentBallType;
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reload(int mapNo) {
		paddle = new Paddle();
		if (this.currentBallNo == 1) {
			ball = new StraightBall();
			currentBallType.setText("Straight Ball");
		} else {
			ball = new AdvancedBall();
			currentBallType.setText("Advanced Ball");
		}

		brickList = new LinkedList<Brick>();

		String[][] currentBrickGridLayout;
		if (mapNo == 1) {
			currentBrickGridLayout = BrickGridLayout.getBrickGridLayout(BRICK_MAP_1);
		} else if (mapNo == 2) {
			currentBrickGridLayout = BrickGridLayout.getBrickGridLayout(BRICK_MAP_2);
		} else if (mapNo == 3) {
			currentBrickGridLayout = BrickGridLayout.getRandomBrickLayout();
			;
		} else {
			currentBrickGridLayout = BrickGridLayout.getBrickGridLayout(SAVED_BRICK_MAP);
		}
		for (int i = 0; i < currentBrickGridLayout.length; i++) {
			for (int j = 0; j < currentBrickGridLayout[0].length; j++) {
				if (currentBrickGridLayout[i][j].equals(BREAKABLE_BRICK_SYMBOL)) {
					brickList.add(new Brick(j * SCALING, i * SCALING, true));
				} else if (currentBrickGridLayout[i][j].equals(UNBREAKABLE_BRICK_SYMBOL)) {
					brickList.add(new Brick(j * SCALING, i * SCALING, false));
				}
			}
		}

		playing = true;
		status.setText("Running...");
		tilesLeft.setText("Tiles Left: " + brickList.size());

		// to ensure this component has the keyboard focus
		requestFocusInWindow();
	}

	void tick() {
		
		this.changeTheActualBall();
		
		if (playing) {
			this.paddle.move();
			this.ball.move();

			// make the ball bounce off walls and the paddle...
			if (this.ball.intersects(this.paddle) && this.ball.isLaunched()) {
				this.ball.bounceOnPaddle(this.paddle);
			} else if (this.ball.hitWall() == Direction.DOWN) {
				this.playing = false;
				this.status.setText("You lose!");
			} else if (this.ball.hitWall() != null) {
				this.ball.bounce(this.ball.hitWall());
			} 
			
			ListIterator<Brick> iter = this.brickList.listIterator();
			Brick b;
			while(iter.hasNext()){
				b = iter.next();
			    if(this.ball.intersects(b)){
			        this.ball.bounce(this.ball.hitObj(b));
			        iter.remove();
			    }
			}
			tilesLeft.setText("Tiles Left: " + this.brickList.size());
			
			if (this.brickList.size() == 0) {
				status.setText("You Won!");
				this.playing = false;
			}

			// update the display
			repaint();
		}
	}

	public void saveGame() {
		if (this.playing) {
			String[][] brickGridMap = new String[BrickGridLayout.BRICK_GRID_HEIGHT][BrickGridLayout.BRICK_GRID_WIDTH];
			for (Brick b : brickList) {
				brickGridMap[b.getPy() / GameCourt.SCALING][b.getPx() / GameCourt.SCALING] = "#";
			}
			for (int i = 0; i < BrickGridLayout.BRICK_GRID_HEIGHT; i++) {
				for (int j = 0; j < BrickGridLayout.BRICK_GRID_WIDTH; j++) {
					if (brickGridMap[i][j] == null) {
						brickGridMap[i][j] = "0";
					}
				}
			}
			String currentLayoutInString = "";
			for (String[] line : brickGridMap) {
				for (String letter : line) {
					currentLayoutInString = currentLayoutInString + letter;
				}
				currentLayoutInString = currentLayoutInString + "\n";
			}
			BrickGridLayout.saveCurrentGridLayout(currentLayoutInString);

			requestFocusInWindow();
		} else {
			String message = "Cannot save game that's won/lost";
			this.status.setText("<html>" + message + "</html>");

		}

	}

	public void changeTheActualBall() {
		if (this.needToChangeBall) {
			int px = this.ball.getPx();
			int py = this.ball.getPy();
			int vx = this.ball.getVx();
			int vy = this.ball.getVy();
			if (this.ball instanceof AdvancedBall) {
				if (this.currentBallNo == 1) {
					this.ball = new StraightBall();
					this.ball.launch();
					this.currentBallType.setText("Straight Ball");
				}
			} else if (this.ball instanceof StraightBall) {
				if (this.currentBallNo == 2) {
					this.ball = new AdvancedBall();
					this.ball.launch();
					this.currentBallType.setText("Advanced Ball");
				}
			}
			this.ball.setPx(px);
			this.ball.setPy(py);
			this.ball.setVx(vx);
			this.ball.setVy(vy);
		}
		this.needToChangeBall = false;

	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paddle.draw(g);
		ball.draw(g);
		for (Brick b : this.brickList) {
			b.draw(g);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}