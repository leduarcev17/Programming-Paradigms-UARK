//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 3/10/26
// Assignment Description: The object of this assignment is to use inheritance
//                         to make our code cleaner and eassier to extend and mantain,
//                         secondly, we had to implement new sprites using all we have
//                         learned in previous assignments.
//************************************************************************  

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class MsPacman extends Sprite
{
    // Declaring Variables and Constants
	private final int MAX_DIRECTIONS = 4, NUM_IMAGES_PER_DIRECTION = 3;
    private static final int MSPACMAN_HEIGHT = 25, MSPACMAN_WIDTH = 25;

    private static BufferedImage images[][] = null;
    private int numFrame = 0, direction = 0;
    private int speed;

    // Constructor
	public MsPacman(int startX, int startY)
	{
		super(startX, startY, MSPACMAN_HEIGHT, MSPACMAN_WIDTH);
        initializeVars();
	}

    // Json Constructor
    public MsPacman(Json ob)
    {
        super((int)ob.getLong("x"), (int)ob.getLong("y"), MSPACMAN_HEIGHT, MSPACMAN_WIDTH);
        initializeVars();
    }

    private void initializeVars()
    {
        this.speed = 10;
        setPrevPosition();
        if(images == null)
        {
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
    }
    
    // Getters

    public int getSpeed()
    {
        return speed;
    }

    // Setters

    public void setSpeed(int varSpeed)
    {
        this.speed = varSpeed;
    }

    // Draw MsPacman on the screen 
    @Override
    public void drawYourself(Graphics g)
    {
        g.drawImage(images[direction][numFrame], this.getX(), this.getY() - View.getScrollPos(), this.getW(), this.getH(), null);
    }

    // MsPacman Movement Methods
    public void moveRight()
    {
        if(getX() > 756)
            setX(-5);
        else
        {
            direction = 2;
            updateImageNum();
            setX(x + getSpeed());
        }
    }

    public void moveLeft()
    {
        if(getX() < -10)
            setX(776);
        else
        {
            direction = 0;
            updateImageNum();
            setX(x - getSpeed());
        }
    }

    public void moveUp()
    {
        direction = 1;
        updateImageNum();
        setY(y - getSpeed());

        View.followMsPacman(getY());
    }

    public void moveDown()
    {
        direction = 3;
        updateImageNum();
        setY(y + getSpeed());

        View.followMsPacman(getY());
    }

    // MsPacman animation control
    private void updateImageNum()
    {
        if(++numFrame >= NUM_IMAGES_PER_DIRECTION)
            numFrame = 0;
    }

    @Override
    public boolean update()
    {
        return true;
    }

    @Override
    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }

    @Override
    public boolean isMsPacman()
    {
        return true;
    }

    @Override 
    public String toString()
    {
        return "MsPacman (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }
}

