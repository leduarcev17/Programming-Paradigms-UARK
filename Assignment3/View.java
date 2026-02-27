//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 2/24/26
// Assignment Description: The objective of this assignment is to extend the
//                         previous assignment by adding MsPacman to our map, adding
//                         collision detection and fixing collisions.
//************************************************************************  

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;

public class View extends JPanel {
    
    //Defining Member Variables
    public static final int WINDOW_HEIGHT = 800;
    public static final int MAP_HEIGHT = 960;

    private BufferedImage tileImage;
    private Model model;
    private Controller controller;
    private static int scrollPos;

    // Contructor
    public View(Controller c, Model m)
    {
        model = m;
        controller = c;
        c.setView(this);
        scrollPos = 0;

        try
        {
            tileImage = ImageIO.read(new File("images/tile2.png"));
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    // Display control
    public void paintComponent(Graphics g)
    {
        g.setColor(new Color(51, 0, 0));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Draw tiles
        for(int i = 0; i < model.getTileCount(); i++)
        {
            Tile tile = model.getTile(i);
            tile.drawYourself(g);
        }

        MsPacman msPacman = model.getMsPacman();
        msPacman.drawYourself(g);

        // Edit Mode Display
        if(controller.getEditMode())
        {
            if(controller.getAddMapItem())
            {
                g.setColor(new Color(43, 203, 54));
                g.fillRect(0, 0, 100, 100);
                g.drawImage(tileImage, 30, 30, 40, 40, null);
            }
            else
            {
                g.setColor(new Color(203, 43, 43));
                g.fillRect(0, 0, 100, 100);
                g.drawImage(tileImage, 30, 30, 40, 40, null);
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
