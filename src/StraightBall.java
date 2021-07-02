import java.awt.Color;

// Straight ball is ball type 1

public class StraightBall extends Ball {

	private static Color color = new Color(0, 98, 51);

	public StraightBall() {
		super(color);
	}

	@Override
	public void bounceOnPaddle(GameObj paddle) {
		if ((this.getPx() > (paddle.getPx() + 25)) && (this.getPx() < (paddle.getPx() + 75))){
			// if the ball is touching the middle 50% send the ball straight up
			this.setVy(-5);
			this.setVx(0);
		} else {
			if (this.getVx() < 0) {
				// if the ball is moving left
				this.setVx(-3);
				this.setVy(-3);
			} else if (this.getVx() == 0) {
				// if the ball lands straight on the paddle
				if (this.getPx() < (paddle.getPx() + 25)){
					// if the ball lands on the left side
					this.setVx(-3);
					this.setVy(-3);
				} else {
					//if the ball lands on the right side
					this.setVx(3);
					this.setVy(-3);
				}
			}
			else {
				// if the ball is moving right
				this.setVx(3);
				this.setVy(-3);
			}
		}
	}
}
