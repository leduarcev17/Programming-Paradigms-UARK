//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 3/10/26
// Assignment Description: The object of this assignment is to use inheritance
//                         to make our code cleaner and eassier to extend and mantain,
//                         secondly, we had to implement new sprites using all we have
//                         learned in previous assignments.
//************************************************************************  

import java.awt.Graphics;

public abstract class Sprite {
    protected int x, y, w, h, px, py;

    public Sprite(int x, int y, int h, int w)
    {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }

    // Getters
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getH()
    {
        return h;
    }

    public int getW()
    {
        return w;
    }

    public int getRight()
    {
        return x + w;
    }

    public int getLeft()
    {
        return x;
    }

    public int getTop()
    {
        return y;
    }

    public int getBottom()
    {
        return y + h;
    }

    public int getPX()
    {
        return px;
    }

    public int getPY()
    {
        return py;
    }

    public int getPrevRight()
    {
        return px + w;
    }

    public int getPrevLeft()
    {
        return px;
    }

    public int getPrevTop()
    {
        return py;
    }

    public int getPrevBottom()
    {
        return py + h;
    }

    // Setters
    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setH(int h)
    {
        this.h = h;
    }

    public void setW(int w)
    {
        this.w = w;
    }

    public void setPX(int px)
    {
        this.px = px;
    }

    public void setPY(int py)
    {
        this.py = py;
    }

    public void setPrevPosition()
    {
        this.px = x;
        this.py = y;
    }

    // Fix Collisions
    public void fixCollision(Sprite other)
    {
        if(getPrevRight() <= other.getLeft() && getRight() > other.getLeft())
        {
            x = other.getLeft() - w - 1;
            return;
        }

        if(getPrevLeft() >= other.getRight() && getLeft() < other.getRight())
        {
            x = other.getRight() + 1;
            return;
        }

        if(getPrevBottom() <= other.getTop() && getBottom() > other.getTop())
        {
            y = other.getTop() - h - 1;
            return;
        }

        if(getPrevTop() >= other.getBottom() && getTop() < other.getBottom())
        {
            y = other.getBottom() + 1;
            return;
        }
    }

    // Detect if clicking an existing sprite
    public boolean spriteDetection(int mouseX, int mouseY)
    {
        return mouseX >= x && mouseX <= x + w && 
               mouseY >= y && mouseY <= y + h;
    }

    public abstract boolean update();
    public abstract void drawYourself(Graphics g);
    public abstract Json marshal();

    public boolean isMsPacman()
    {
        return false;
    }

    public boolean isTile()
    {
        return false;
    }

    public boolean isFruit()
    {
        return false;
    }

    public boolean isGhost()
    {
        return false;
    }
}
