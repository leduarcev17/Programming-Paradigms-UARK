//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 3/10/26
// Assignment Description: The object of this assignment is to use inheritance
//                         to make our code cleaner and eassier to extend and mantain,
//                         secondly, we had to implement new sprites using all we have
//                         learned in previous assignments.
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
    private boolean keyLeft;
	private boolean keyRight;
	private boolean keyUp;
	private boolean keyDown;

    // Constructor
    public Controller(Model m)
    {
        model = m;
        keepGoing = true;
    }

    // Update Controller Method
    public boolean update()
    {
        MsPacman msPacman = model.getMsPacman();
        msPacman.setPrevPosition();
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

    // Detect Mouse Clicks
    public void mousePressed(MouseEvent e)
    {
        int mouseX = e.getX();
        int mouseY = e.getY() + View.getScrollPos();

        if(model.getEditMode()  && (e.getButton() == MouseEvent.BUTTON3))
        {
            model.changeItemNum();
        }

        // Add Sprites
        if(model.getEditMode() && model.getAddMapItem() && (e.getButton() == MouseEvent.BUTTON1))
        {
            if(model.getItem().isTile())
            {
                model.addTile(mouseX, mouseY);
            }

            else if(model.getItem().isGhost())
            {
                Ghost ghost = (Ghost)model.getItem();
                model.addGhost(mouseX, mouseY, ghost.getGhostNum());
            }

            else if(model.getItem().isFruit())
            {
                Fruit fruit = (Fruit)model.getItem();
                model.addFruit(mouseX, mouseY, fruit.getFruitNum());
            }
        }

        // Remove Sprites
        if(model.getEditMode() && !model.getAddMapItem() && (e.getButton() == MouseEvent.BUTTON1))
        {
            if(model.getItem().isTile())
            {
                model.removeTile(mouseX, mouseY);
            }

            else if(model.getItem().isGhost())
            {
                model.removeGhost(mouseX, mouseY);
            }

            else if(model.getItem().isFruit())
            {
                model.removeFruit(mouseX, mouseY);
            }
        }
    }

    public void mouseDragged(MouseEvent e)
    {
        int mouseX = e.getX();
        int mouseY = e.getY() + View.getScrollPos();

        if(model.getEditMode() && (e.getButton() == MouseEvent.BUTTON3))
        {
            model.changeItemNum();
        }

        // Add Sprites
        if(model.getEditMode() && model.getAddMapItem() && (e.getButton() == MouseEvent.BUTTON1))
        {
            if(model.getItem().isTile())
            {
                model.addTile(mouseX, mouseY);
            }

            else if(model.getItem().isGhost())
            {
                Ghost ghost = (Ghost)model.getItem();
                model.addGhost(mouseX, mouseY, ghost.getGhostNum());
            }

            else if(model.getItem().isFruit())
            {
                Fruit fruit = (Fruit)model.getItem();
                model.addFruit(mouseX, mouseY, fruit.getFruitNum());
            }
        }

        // Remove Sprites
        if(model.getEditMode() && !model.getAddMapItem() && (e.getButton() == MouseEvent.BUTTON1))
        {
            if(model.getItem().isTile())
            {
                model.removeTile(mouseX, mouseY);
            }

            else if(model.getItem().isGhost())
            {
                model.removeGhost(mouseX, mouseY);
            }

            else if(model.getItem().isFruit())
            {
                model.removeFruit(mouseX, mouseY);
            }
        }
    }

    // Detect Key Presses
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
            if(model.getEditMode())
            {
                model.setEditMode(false);
                System.out.println("Edit Mode off");
            }
            else if(!model.getEditMode())
            {
                model.setEditMode(true);
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
        if(c == 'a' && model.getEditMode())
        {
            model.setAddMapItem(true);
        }

        if(c == 'r' && model.getEditMode())
        {
            model.setAddMapItem(false);
        }

        if(c == 'c' && model.getEditMode())
        {
            model.clearSprites();
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

    // Unused methods
    public void mouseMoved(MouseEvent e) {      } 
    public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }
    public void keyTyped(KeyEvent e) {      }
}
