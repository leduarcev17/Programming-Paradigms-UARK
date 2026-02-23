//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 2/10/26
// Assignment Description: The objective of this assignment is to build a 
//                         map editor that is able to draw and remove tiles on
//                         the display using an array list, also, it has to be able
//                         save and load maps in a .json file
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
    private boolean editMode, addMapItem;

    // Constructor
    public Controller(Model m)
    {
        model = m;
        keepGoing = true;
        editMode = false;
        addMapItem = true;
    }

    public boolean update()
    {
        return keepGoing;
    }

    public void setView(View v)
    {
        view = v;
    }

    public void mousePressed(MouseEvent e)
    {
        int mouseX = e.getX();
        int mouseY = e.getY() + view.getScrollPos();

        if(editMode && !addMapItem)
            model.removeTile(mouseX, mouseY);

        if(editMode && addMapItem)
            model.addTile(mouseX, mouseY);
    }

    public void mouseDragged(MouseEvent e)
    {
        int mouseX = e.getX();
        int mouseY = e.getY() + view.getScrollPos();

        if(editMode && !addMapItem)
            model.removeTile(mouseX, mouseY);

        if(editMode && addMapItem)
            model.addTile(mouseX, mouseY);
    }

    public void keyReleased(KeyEvent e)
    {
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
        if(c == '8')
            view.scrollUp();

        if(c == '2')
            view.scrollDown();
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
    public void keyPressed(KeyEvent e) {    }
    public void keyTyped(KeyEvent e) {      }
}
