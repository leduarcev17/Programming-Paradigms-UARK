//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 2/24/26
// Assignment Description: The objective of this assignment is to extend the
//                         previous assignment by adding MsPacman to our map, adding
//                         collision detection and fixing collisions.
//************************************************************************   

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import javax.print.attribute.standard.Destination;

public class MsPacman
{
    // Declaring Variables and Constants
	private final int MAX_DIRECTIONS = 4, NUM_IMAGES_PER_DIRECTION = 3;
    private static final int MSPACMAN_HEIGHT = 25, MSPACMAN_WIDTH = 25;
    private static final int START_X = 50, START_Y = 50;

    private static BufferedImage images[][];
    private int numFrame = 0, direction = 0;
    private int x, y, w, h, speed, prevX, prevY;

    // Constructor
	public MsPacman()
	{
		initializeVars(START_X, START_Y);
	}

    private void initializeVars(int varX, int varY)
    {
        this.x = varX;
        this.y = varY;
        this.w = MSPACMAN_WIDTH;
        this.h = MSPACMAN_HEIGHT;
        this.speed = 10;
        this.prevX = x;
        this.prevY = y;
        
        images = new BufferedImage[MAX_DIRECTIONS][NUM_IMAGES_PER_DIRECTION];
        int imageNum = 1;
        for (int i = 0; i < MAX_DIRECTIONS; i++)
        {
            for (int j = 0; j < NUM_IMAGES_PER_DIRECTION; j++)
            {
                images[i][j] = View.loadImage("images/mspacman" + imageNum + ".png");
                imageNum++;
            }
        }
    }
    
    // Getters
    public int getMsPacmanX()
    {
        return x;
    }

    public int getMsPacmanY()
    {
        return y;
    }

    public int getMsPacmanW()
    {
        return w;
    }

    public int getMsPacmanH()
    {
        return h;
    }

    public int getMsPacmanSpeed()
    {
        return speed;
    }

    public int getMsPacmanPrevRight()
    {
        return prevX + w;
    }

    public int getMsPacmanPrevLeft()
    {
        return prevX;
    }

    public int getMsPacmanPrevTop()
    {
        return prevY;
    }

    public int getMsPacmanPrevBottom()
    {
        return prevY + h;
    }

    public int getMsPacmanRight()
    {
        return x + w;
    }

    public int getMsPacmanLeft()
    {
        return x;
    }

    public int getMsPacmanTop()
    {
        return y;
    }

    public int getMsPacmanBottom()
    {
        return y + h;
    }

    // Setters
    public void setMsPacmanX(int varX)
    {
        this.x = varX;
    }

    public void setMsPacmanY(int varY)
    {
        this.y = varY;
    }

    public void setMsPacmanSpeed(int varSpeed)
    {
        this.speed = varSpeed;
    }

    public void setPreviousPosition()
    {
        this.prevX = x;
        this.prevY = y;
    }

    // Draw MsPacman on the screen 
    public void drawYourself(Graphics g)
    {
        g.drawImage(images[direction][numFrame], this.getMsPacmanX(), this.getMsPacmanY() - View.getScrollPos(), this.getMsPacmanW(), this.getMsPacmanH(), null);
    }

    // MsPacman Movement Methods
    public void moveRight()
    {
        if(getMsPacmanX() > 756)
            setMsPacmanX(-5);
        else
        {
            direction = 2;
            updateImageNum();
            setMsPacmanX(x + getMsPacmanSpeed());
        }
    }

    public void moveLeft()
    {
        if(getMsPacmanX() < -10)
            setMsPacmanX(776);
        else
        {
            direction = 0;
            updateImageNum();
            setMsPacmanX(x - getMsPacmanSpeed());
        }
    }

    public void moveUp()
    {
        direction = 1;
        updateImageNum();
        setMsPacmanY(y - getMsPacmanSpeed());

        View.followMsPacman(getMsPacmanY());
    }

    public void moveDown()
    {
        direction = 3;
        updateImageNum();
        setMsPacmanY(y + getMsPacmanSpeed());

        View.followMsPacman(getMsPacmanY());
    }

    // MsPacman animation control
    private void updateImageNum()
    {
        if(++numFrame >= NUM_IMAGES_PER_DIRECTION)
            numFrame = 0;
    }

    // Fixing Collisions
    public void fixCollision(Tile t)
    {
        if(getMsPacmanPrevRight() <= t.getTileLeft() && getMsPacmanRight() > t.getTileLeft())
        {
            x = t.getTileLeft() - w - 1;
            return;
        }

        if(getMsPacmanPrevLeft() >= t.getTileRight() && getMsPacmanLeft() < t.getTileRight())
        {
            x = t.getTileRight() + 1;
            return;
        }

        if(getMsPacmanPrevBottom() <= t.getTileTop() && getMsPacmanBottom() > t.getTileTop())
        {
            y = t.getTileTop() - h - 1;
            return;
        }

        if(getMsPacmanPrevTop() >= t.getTileBottom() && getMsPacmanTop() < t.getTileBottom())
        {
            y = t.getTileBottom() + 1;
            return;
        }
    }
}

