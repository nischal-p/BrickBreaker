import java.awt.Color;

// Advanced Ball is ball type 2

public class AdvancedBall extends Ball {

	public AdvancedBall() {
		super(Color.RED);
	}

	@Override
	public void bounceOnPaddle(GameObj paddle) {
		if (this.getPx() < (paddle.getPx() + 25)) {
			// if the ball is touching the left 25%
			if (this.getVx() < 0) {
				// if the ball is moving left
				this.setVx(-4);
				this.setVy(-2);
			} else {
				// if the ball is moving right
				this.setVx(-2);
				this.setVy(-4);
			}
		} else if (this.getPx() < (paddle.getPx() + 75)) {
			// if the ball is touching the middle 50%
			if (this.getVx() < 0) {
				// if the ball is moving left
				this.setVx(-3);
				this.setVy(-3);
			} else {
				// if the ball is moving right
				this.setVx(3);
				this.setVy(-3);
			}
		} else {
			// if the ball is touching the right 25%
			if (this.getVx() < 0) {
				// if the ball is moving left
				this.setVx(2);
				this.setVy(-4);
			} else {
				// if the ball is moving right
				this.setVx(4);
				this.setVy(-2);
			}
		}
	}
}
