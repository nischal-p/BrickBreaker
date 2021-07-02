/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Brick extends GameObj {
    public static final int SIZE = GameCourt.SCALING;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    private Color brickColor;

    public Brick(int init_x, int init_y, boolean breakable) {
        super(INIT_VEL_X, INIT_VEL_Y, init_x, init_y, SIZE, SIZE);
        this.brickColor = Color.BLACK;
    }

    @Override
    public void draw(Graphics g) {
    	g.setColor(this.brickColor);
        g.fillRect(this.getPx(), this.getPy(), SIZE, SIZE);
        g.setColor(GameCourt.GOOD_GREEN);
        g.drawRect(this.getPx(), this.getPy(), SIZE, SIZE);
    }
}

