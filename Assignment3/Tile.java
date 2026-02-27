//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 2/24/26
// Assignment Description: The objective of this assignment is to extend the
//                         previous assignment by adding MsPacman to our map, adding
//                         collision detection and fixing collisions.
//************************************************************************  

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Tile {

    private static BufferedImage image = null;
    private int x, y, w, h;
    private static final int TILE_HEIGHT = 40, TILE_WIDTH = 40;


    // Constructor
    public Tile(int tileX, int tileY)
    {
        initializeVars(tileX, tileY);
    }

    // Json contructor
    public Tile(Json ob)
    {
        initializeVars((int)ob.getLong("x"), (int)ob.getLong("y"));
    }

    private void initializeVars(int varX, int varY)
    {
        this.x = varX;
        this.y = varY;
        this.w = TILE_WIDTH;
        this.h = TILE_HEIGHT;

        if (image == null)
        {
            image = View.loadImage("images/tile2.png");
        }
    }

    // Getters
    public int getTileX()
    {
        return x;
    }

    public int getTileY()
    {
        return y;
    }

    public int getTileW()
    {
        return w;
    }

    public int getTileH()
    {
        return h;
    }

    public int getTileRight()
    {
        return x + w;
    }

    public int getTileLeft()
    {
        return x;
    }

    public int getTileTop()
    {
        return y;
    }

    public int getTileBottom()
    {
        return y + h;
    }

    // Setters

    public void setTileX(int userX)
    {
        this.x = userX;
    }

    public void setTileY(int userY)
    {
        this.y = userY;
    }

    public void setTileW(int userW)
    {
        this.w = userW;
    }

    public void setTileH(int userH)
    {
        this.h = userH;
    }

    // Detect if clicking an existing tile
    public boolean tileDetection(int mouseX, int mouseY)
    {
        return mouseX >= x && mouseX <= x + w && 
               mouseY >= y && mouseY <= y + h;
    }

    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }

    // Draw the Tile on the scren
    public void drawYourself(Graphics g)
    {
        g.drawImage(image, this.getTileX(), this.getTileY() - View.getScrollPos(), this.getTileW(), this.getTileH(), null);
    }
}