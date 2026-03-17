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

public class Tile extends Sprite
{

    private static BufferedImage image = null;
    private static final int TILE_HEIGHT = 40, TILE_WIDTH = 40;


    // Constructor
    public Tile(int tileX, int tileY)
    {
        super(tileX, tileY, TILE_HEIGHT, TILE_WIDTH);
        initializeVars();
    }

    // Json contructor
    public Tile(Json ob)
    {
        super((int)ob.getLong("x"), (int)ob.getLong("y"), TILE_HEIGHT, TILE_WIDTH);
        initializeVars();
    }

    private void initializeVars()
    {
        if (image == null)
        {
            image = View.loadImage("images/tile2.png");
        }
    }

    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }

    // Draw the Tile on the scren
    @Override
    public void drawYourself(Graphics g)
    {
        g.drawImage(image, this.getX(), this.getY() - View.getScrollPos(), this.getW(), this.getH(), null);
    }

    @Override
    public boolean update()
    {
        return true;
    }

    @Override
    public boolean isTile()
    {
        return true;
    }

    @Override 
    public String toString()
    {
        return "Tile (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }
}