/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;

/**
 * A basic game object starting in the upper left corner of the game court. It is displayed as a
 * circle of a specified color.
 */
public abstract class Ball extends GameObj {
    public static final int SIZE = 16;
    public static final int INIT_POS_X = (GameCourt.COURT_WIDTH/2) - (SIZE/2);
    public static final int INIT_POS_Y = Paddle.PADDLE_INITIAL_POS_Y - SIZE;
    public static final int INIT_VEL_X = 3;
    public static final int INIT_VEL_Y = 3;
    private final Color ballColor;
    private boolean ballLaunched;
    // NOTE: the sum of the abs value of ball's x, and y velocity should be 6 always

    public Ball(Color ballColor) {
        super(0, 0, INIT_POS_X, INIT_POS_Y, SIZE, SIZE);
        this.ballLaunched = false;
        this.ballColor = ballColor;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.ballColor);
        g.fillOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }
    
    public void launch() {
    	this.ballLaunched = true;
    	this.setVx(INIT_VEL_X);
    	this.setVy(-INIT_VEL_Y);
    }
    
    public boolean isLaunched() {
    	return this.ballLaunched;
    }
    
    // this method assumes that the ball intersects with the paddle
    public abstract void bounceOnPaddle(GameObj paddle) ;
    
}