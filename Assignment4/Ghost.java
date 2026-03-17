//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 3/10/26
// Assignment Description: The object of this assignment is to use inheritance
//                         to make our code cleaner and eassier to extend and mantain,
//                         secondly, we had to implement new sprites using all we have
//                         learned in previous assignments.
//************************************************************************  

import java.awt.image.BufferedImage;
import javax.swing.Timer;
import java.awt.Graphics;
import java.util.Random;

public class Ghost extends Sprite{
    // Declare variabes and Constants
    private static final int GHOST_HEIGHT = 25, GHOST_WIDTH = 25;
    private static final int GHOST_COUNT = 5, MAX_DIRECTIONS = 4, NUM_IMAGES_PER_DIRECTION = 2;
    private static final int STATE_DURATION = 1000;
    private static BufferedImage images[][][] = null;

    private Random random;
    private int speed, direction, ghostNum;
    private int numFrame = 0;
    private boolean eaten, readyToRemove;

    // Constructor
    public Ghost(int startX, int startY, int ghostNum)
    {
        super(startX, startY, GHOST_HEIGHT, GHOST_WIDTH);
        initializeVars(ghostNum);
    }

    // Json Constructor
    public Ghost(Json ob)
    {
        super((int)ob.getLong("x"), (int)ob.getLong("y"), GHOST_HEIGHT, GHOST_WIDTH);
        initializeVars((int)ob.getLong("ghostNum"));
    }

    private void initializeVars(int ghostNum)
    {
        this.speed = 10;
        this.eaten = false;
        this.ghostNum = ghostNum;
        this.readyToRemove = false;
        String ghost_names[] = {"blinky", "inky", "pinky", "sue", "ghost"};
        random = new Random();
        getRandomDirection();
        setPrevPosition();
        
        // Load Ghost images
        if(images == null)
        {
            images = new BufferedImage[GHOST_COUNT][MAX_DIRECTIONS][NUM_IMAGES_PER_DIRECTION];

            for(int i = 0; i < GHOST_COUNT; i++)
            {
                int imageNum = 1;
                String filename = "images/" + ghost_names[i];

                for(int j = 0; j < MAX_DIRECTIONS; j++)
                {
                    for(int k = 0; k < NUM_IMAGES_PER_DIRECTION; k++)
                    {
                        images[i][j][k] = View.loadImage(filename + imageNum + ".png");
                        imageNum++;
                    }
                }
            }
        }
    }

    // Getters
    public int getSpeed()
    {
        return this.speed;
    }

    public int getGhostNum()
    {
        return this.ghostNum;
    }

    public void getRandomDirection()
    {
        int nextDir;
        do { 

            nextDir = random.nextInt(4);

        } while (direction == nextDir);

        this.direction = nextDir;
    }

    // Setters
    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public void setGhostNum(int ghostNum)
    {
        this.ghostNum = ghostNum;
    }

    // Draw Ghost on the screen
    @Override
    public void drawYourself(Graphics g)
    {
        g.drawImage(images[ghostNum][direction][numFrame], this.getX(), this.getY() - View.getScrollPos(), this.getW(), this.getH(), null);
    }

    // Ghost Movement Control
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
    }

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

    public void moveDown()
    {
        direction = 3;
        updateImageNum();
        setY(y + getSpeed());
    }

    // Ghost animation control
    private void updateImageNum()
    {
        if(++numFrame >= NUM_IMAGES_PER_DIRECTION)
            numFrame = 0;
    }

    public void eatenAnimation()
    {
        this.eaten = true;
        setBlue();

        Timer timer = new Timer(STATE_DURATION, e ->{
            setWhite();

            Timer timer2 = new Timer(STATE_DURATION, e2 -> {
                setEyes();

                Timer timer3 = new Timer(STATE_DURATION, e3 ->{
                    readyToRemove = true;
                });
                timer3.setRepeats(false);
                timer3.start();
            });
            timer2.setRepeats(false);
            timer2.start();
        });

        timer.setRepeats(false);
        timer.start();
    }

    private void setBlue()
    {
        direction = 0;
        ghostNum = 4;
    }

    private void setWhite()
    {
        direction = 1;
    }

    private void setEyes()
    {
        direction = 2;
    }

    public boolean isEaten()
    {
        return eaten;
    }

    @Override
    public boolean isGhost()
    {
        return true;
    }

    @Override
    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("ghostNum", ghostNum);
        return ob;
    }

    @Override
    public boolean update()
    {
        if(readyToRemove == true)
            return false;
        if(eaten == false)
        {
            switch (direction)
            {
                case 0:
                    moveLeft();
                    return true;
                case 1:
                    moveUp();
                    return true;
                case 2:
                    moveRight();
                    return true;
                case 3:
                    moveDown();
                    return true;
            }
        }
        // Eaten animation
        else {
            
            updateImageNum();
        }
        return true;
    }

    @Override 
    public String toString()
    {
        return "Ghost (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }
}
