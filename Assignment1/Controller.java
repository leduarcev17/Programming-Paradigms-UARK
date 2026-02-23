//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 1/27/26
// Assignment Description: For this assignment I was required to build a program
//                         that is able to show the image of a turtle on a window
//                         and process both keyboard and mouse inputs to move this 
//						   image around the window
//************************************************************************   

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller implements ActionListener, MouseListener, KeyListener
{
	// Defining Member Variables
	private boolean keepGoing;
	private View view;
	private Model model;
	private boolean keyLeft;
	private boolean keyRight;
	private boolean keyUp;
	private boolean keyDown;
	
	// Default Controller Class Constructor
	public Controller(Model m)
	{
		model = m;
		keepGoing = true;
	}

	// This method will check if the user has pressed the button showed at the begining of the program
	public void actionPerformed(ActionEvent e)
	{
		view.removeButton(); // After the user presses the button it will remove itself
	}

	// This method will detect if we want to move the turtle using the keyboard
	public boolean update()
	{	

		if(keyRight) 
			model.turtleMovesRight();

		if(keyLeft) 
			model.turtleMovesLeft();

		if(keyDown)
			model.turtleMovesDown();

		if(keyUp)
			model.turtleMovesUp();

		//the Controller keeps track of whether or not we have 
		//quit the program and returns this value to the Game 
		//engine of whether or not to continue the game loop		
		return keepGoing;
	}

	public void setView(View v)
	{
		view = v;
	}

	// This method will get the coordinates of where the user presed its mouse to move the turtle there
	public void mousePressed(MouseEvent e)
	{
		model.setTurtleDestination(e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }

	// This method will check which key is being pressed
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: 
				keyRight = true; 
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = true; 
				break;
			case KeyEvent.VK_UP: 
				keyUp = true; 
				break;
			case KeyEvent.VK_DOWN: 
				keyDown = true; 
				break;
		}
	}

	// This method will check if the user released the key that was pressing
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT:
				keyRight = false; 
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = false; 
				break;
			case KeyEvent.VK_UP: 
				keyUp = false; 
				break;
			case KeyEvent.VK_DOWN: 
				keyDown = false; 
				break;
			case KeyEvent.VK_ESCAPE:
				keepGoing = false;
		}

		// In this part of the method we check if the used pressed "q" to exit the program
		char c = Character.toLowerCase(e.getKeyChar());
		if(c == 'q') 
			keepGoing = false;
	}

	public void keyTyped(KeyEvent e)
	{    }

}
