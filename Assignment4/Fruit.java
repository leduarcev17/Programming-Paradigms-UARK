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
import java.util.Random;


public class Fruit extends Sprite {
    // Declaring variables and constants
    private static final int FRUIT_HEIGHT = 25, FRUIT_WIDTH = 25;
    private static final int FRUIT_COUNT = 7;
    private static BufferedImage images[] = null;
    private Random random;

    private int speed, direction;
    private int fruitNum;
    private boolean eaten;

    // Constructor
    public Fruit(int startX, int startY, int fruitNum)
    {
        super(startX, startY, FRUIT_HEIGHT, FRUIT_WIDTH);
        initializeVars(fruitNum);
    }

    // Json Constructor
    public Fruit(Json ob)
    {
        super((int)ob.getLong("x"), (int)ob.getLong("y"), FRUIT_HEIGHT, FRUIT_WIDTH);
        initializeVars((int)ob.getLong("fruitNum"));
    }

    private void initializeVars(int fruitNum)
    {
        this.speed = 8;
        this.fruitNum = fruitNum;
        this.eaten = false;
        random = new Random();
        getRandomDirection();
        setPrevPosition();

        // Load Fruit images
        if(images == null)
        {
            images = new BufferedImage[FRUIT_COUNT];
            int imageNum = 1;
            for(int i = 0; i < FRUIT_COUNT; i++)
            {
                images[i] = View.loadImage("images/fruit" +imageNum + ".png");
                imageNum++;
            }
        }
    }
    
    //Getters
    public int getSpeed()
    {
        return this.speed;
    }

    public int getFruitNum()
    {
        return this.fruitNum;
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

    public void setFruitNum(int fruitNum)
    {
        this.fruitNum = fruitNum;
    }

    public void Eaten()
    {
        this.eaten = true;
    }

    // Draw Fruit on the screen
    @Override
    public void drawYourself(Graphics g)
    {
        g.drawImage(images[this.getFruitNum()], this.getX(), this.getY() - View.getScrollPos(), this.getW(), this.getH(), null);
    }

    // Movement control
    public void moveRight()
    {
        if(getX() > 756)
            setX(-5);
        else
        {
            setX(x + getSpeed());
        }
    }

    public void moveLeft()
    {
        if(getX() < -10)
            setX(776);
        else
        {
            setX(x - getSpeed());
        }
    }

    public void moveUp()
    {
        setY(y - getSpeed());
    }

    public void moveDown()
    {
        setY(y + getSpeed());
    }


    @Override
    public boolean isFruit()
    {
        return true;
    }

    @Override
    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("fruitNum", fruitNum);
        return ob;
    }

    @Override
    public boolean update()
    {
        if(!eaten)
        {
            switch (direction)
            {
                case 0:
                    moveRight();
                    break;
                case 1:
                    moveUp();
                    break;
                case 2:
                    moveLeft();
                    break;
                case 3:
                    moveDown();
                    break;
            }
            return true;
        }
        return false;
    }

    @Override 
    public String toString()
    {
        return "Fruit (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }
}
