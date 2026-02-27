//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 2/24/26
// Assignment Description: The objective of this assignment is to extend the
//                         previous assignment by adding MsPacman to our map, adding
//                         collision detection and fixing collisions.
//************************************************************************  

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller implements MouseListener, KeyListener, MouseMotionListener {
    
    // Defining Member Variables
    private boolean keepGoing;
    private View view;
    private Model model;
    private MsPacman msPacman;
    private boolean editMode, addMapItem;
    private boolean keyLeft;
	private boolean keyRight;
	private boolean keyUp;
	private boolean keyDown;

    // Constructor
    public Controller(Model m)
    {
        model = m;
        keepGoing = true;
        editMode = false;
        addMapItem = true;
        msPacman = model.getMsPacman();
    }

    public boolean update()
    {
        msPacman.setPreviousPosition();
        if (keyRight)
            msPacman.moveRight();

        if (keyLeft)
            msPacman.moveLeft();

        if (keyUp)
            msPacman.moveUp();

        if (keyDown)
            msPacman.moveDown();

        return keepGoing;
    }

    public void setView(View v)
    {
        view = v;
    }

    public void mousePressed(MouseEvent e)
    {
        int mouseX = e.getX();
        int mouseY = e.getY() + View.getScrollPos();

        if(editMode && !addMapItem)
            model.removeTile(mouseX, mouseY);

        if(editMode && addMapItem)
            model.addTile(mouseX, mouseY);
    }

    public void mouseDragged(MouseEvent e)
    {
        int mouseX = e.getX();
        int mouseY = e.getY() + View.getScrollPos();

        if(editMode && !addMapItem)
            model.removeTile(mouseX, mouseY);

        if(editMode && addMapItem)
            model.addTile(mouseX, mouseY);
    }

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
        
        char c = Character.toLowerCase(e.getKeyChar());
        if(c == 'q')
            keepGoing = false;

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            keepGoing = false;

        // Edit mode switch 
        if(c == 'e')
        {
            if(editMode)
            {
                editMode = false;
                System.out.println("Edit Mode off");
            }
            else if(!editMode)
            {
                editMode = true;
                System.out.println("Edit Mode on");
            }
        }

        // Save map
        if(c == 's')
        {
            Json map = model.marshal();
            map.save("map.json");
            System.out.println("Map saved to map.json");
        }

        // Load map
        if(c == 'l')
        {
            Json map = Json.load("map.json");
            model.unmarshal(map);
            System.out.println("Map loaded from map.json");
        }

        // Edit mode options
        if(c == 'a' && editMode == true)
        {
            addMapItem = true;
        }

        if(c == 'r' && editMode == true)
        {
            addMapItem = false;
        }

        if(c == 'c' && editMode == true)
        {
            model.clearTiles();
        }

        // Scroll control
        // if(c == '8')
        //     view.scrollUp();

        // if(c == '2')
        //     view.scrollDown();
    }

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

    public boolean getEditMode()
    {
        return editMode;
    }

    public boolean getAddMapItem()
    {
        return addMapItem;
    }

    // Unused methods
    public void mouseMoved(MouseEvent e) {      } 
    public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }
    public void keyTyped(KeyEvent e) {      }
}
