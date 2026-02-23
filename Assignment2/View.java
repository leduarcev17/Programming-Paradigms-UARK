//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 2/10/26
// Assignment Description: The objective of this assignment is to build a 
//                         map editor that is able to draw and remove tiles on
//                         the display using an array list, also, it has to be able
//                         save and load maps in a .json file
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
    private BufferedImage tileImage;
    private Model model;
    private Controller controller;
    private int scrollPos;

    // Contructor
    public View(Controller c, Model m)
    {
        model = m;
        controller = c;
        c.setView(this);
        scrollPos = 0;

        // Load the tile image
        try
        {
            this.tileImage = ImageIO.read(new File("images/tile2.png"));
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

        for(int i = 0; i < model.getTileCount(); i++)
        {
            Tile tile = model.getTile(i);
            g.drawImage(tileImage, tile.getTileX(), tile.getTileY() - scrollPos, tile.getTileW(), tile.getTileH(), null);
        }

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

    // Scroll methods
    public void scrollDown()
    {
        scrollPos += 40;
    }

    public void scrollUp()
    {
        scrollPos -= 40;
    }

    public int getScrollPos()
    {
        return scrollPos;
    }
}
