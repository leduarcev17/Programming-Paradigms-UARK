//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 3/10/26
// Assignment Description: The object of this assignment is to use inheritance
//                         to make our code cleaner and eassier to extend and mantain,
//                         secondly, we had to implement new sprites using all we have
//                         learned in previous assignments.
//************************************************************************  

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.util.Iterator;

public class View extends JPanel {
    
    //Defining Member Variables
    public static final int WINDOW_HEIGHT = 800;
    public static final int MAP_HEIGHT = 960;

    private Model model;
    private static int scrollPos;

    // Contructor
    public View(Controller c, Model m)
    {
        model = m;
        c.setView(this);
        scrollPos = 0;
    }

    // Display control
    public void paintComponent(Graphics g)
    {
        g.setColor(new Color(1, 5, 66));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Draw Sprites
        Iterator<Sprite> it = model.iterator();
        while (it.hasNext()) {
            
            Sprite sprite = it.next();
            sprite.drawYourself(g);
        }

        // Edit Mode Display
        if(model.getEditMode())
        {
            if(model.getAddMapItem())
            {
                g.setColor(new Color(43, 203, 54));
                g.fillRect(0, 0, 100, 100);
                Sprite sprite = model.getItem();
                sprite.drawYourself(g);
            }
            else
            {
                g.setColor(new Color(203, 43, 43));
                g.fillRect(0, 0, 100, 100);
                Sprite sprite = model.getItem();
                sprite.drawYourself(g);
            }
        }
    }

    // Camera method
    public static void followMsPacman(int msPacmanY)
    {
        int target = WINDOW_HEIGHT / 2;

        scrollPos = msPacmanY - target;

        if(scrollPos < 0)
            scrollPos = 0;

        int maxScroll = MAP_HEIGHT - WINDOW_HEIGHT;
        if(scrollPos > maxScroll)
            scrollPos = maxScroll;
    }

    public static int getScrollPos()
    {
        return scrollPos;
    }


    // Load Images
    public static BufferedImage loadImage(String file)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(new File(file));
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
            System.exit(1);
            return null;
        }
        return image;
    }
}
