/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;

/**
 * A basic game object starting in the upper left corner of the game court. It is displayed as a
 * square of a specified color.
 */
public class Paddle extends GameObj {
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 10;
    public static final int PADDLE_INITIAL_POS_X = (GameCourt.COURT_WIDTH/2) - (PADDLE_WIDTH/2);
    public static final int PADDLE_INITIAL_POS_Y = GameCourt.COURT_HEIGHT - PADDLE_HEIGHT - 10;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    /**
    * Note that, because we don't need to do anything special when constructing a paddle, we simply
    * use the superclass constructor called with the correct parameters.
    */
    public Paddle() {
        super(INIT_VEL_X, INIT_VEL_Y, PADDLE_INITIAL_POS_X, 
        		PADDLE_INITIAL_POS_Y , PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(GameCourt.LIGHT_NAVY_BLUE);
        g.fillRect(this.getPx(), this.getPy(), 25 , this.getHeight());
        g.setColor(GameCourt.LIGHT_BLUE);
        g.fillRect(this.getPx() + 25, this.getPy(), 75, this.getHeight());
        g.setColor(GameCourt.LIGHT_NAVY_BLUE);
        g.fillRect(this.getPx() + 75, this.getPy(), 25, this.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(this.getPx(), this.getPy(), PADDLE_WIDTH, PADDLE_HEIGHT);
    }
}