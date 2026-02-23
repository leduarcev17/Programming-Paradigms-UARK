//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 1/27/26
// Assignment Description: For this assignment I was required to build a program
//                         that is able to show the image of a turtle on a window
//                         and process both keyboard and mouse inputs to move this 
//						   image around the window
//************************************************************************   

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
	// Declaring member variables
	private boolean keepGoing;
	private Model model;
	private Controller controller;
	private View view;

	// Default Game Class constructor
	public Game()
	{
		keepGoing = true;
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		view.addMouseListener(controller);
		this.addKeyListener(controller);

		// Configuring the Window displayed
		this.setTitle("Turtle wars!");
		this.setSize(500, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	// This method will keep the program running, it will register any of the turtle's movement,
	// and it will update the screen to show the movement
	public void run()
	{
		do
		{
			keepGoing = controller.update();
			model.update();
			view.repaint(); // This will indirectly call View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 50 milliseconds
			try
			{
				Thread.sleep(50);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		while(keepGoing);

		System.out.println("Goodbye my bro!"); 
		System.exit(0);
	}

	// Main
	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();

	}
}
