//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 1/27/26
// Assignment Description: For this assignment I was required to build a program
//                         that is able to show the image of a turtle on a window
//                         and process both keyboard and mouse inputs to move this 
//						   image around the window
//************************************************************************   


import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;

public class View extends JPanel
{
	// Defining Member Variables 
	private JButton b1;
	private BufferedImage turtle_image;
	private Model model;

	// Default View Class Constructor
	public View(Controller c, Model m)
	{
		model = m;

		// Creates a button on the screen 
		b1 = new JButton("Don't push me!");
		b1.addActionListener(c);
		this.add(b1);
		c.setView(this);

		// This part of the program will try to open our turtle image
		try
		{
			this.turtle_image = ImageIO.read(new File("images/turtle.png")); 
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	public void removeButton()
	{
		// Remove the button from the screen 
		this.remove(b1);
	}

	// This method will draw the turtle and updates the window's appearance

	public void paintComponent(Graphics g)
	{
		// Backgound 
		g.setColor(new Color(179, 225, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// Turtle Image
		g.drawImage(this.turtle_image, model.getTurtleX(), model.getTurtleY(), null);
	}
}
