//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 2/10/26
// Assignment Description: The objective of this assignment is to build a 
//                         map editor that is able to draw and remove tiles on
//                         the display using an array list, also, it has to be able
//                         save and load maps in a .json file
//************************************************************************ 

public class Tile {
    private int x, y, w, h;
    private static final int TILE_HEIGHT = 40, TILE_WIDTH = 40;


    // Constructor
    public Tile(int tileX, int tileY)
    {
        this.x = tileX;
        this.y = tileY;
        this.w = TILE_WIDTH;
        this.h = TILE_HEIGHT;
    }

    // Json contructor
    public Tile(Json ob)
    {
        this.x = (int)ob.getLong("x");
        this.y = (int)ob.getLong("y");
        this.w = TILE_WIDTH;
        this.h = TILE_HEIGHT;
    }

    // Getters
    public int getTileX()
    {
        return x;
    }

    public int getTileY()
    {
        return y;
    }

    public int getTileW()
    {
        return w;
    }

    public int getTileH()
    {
        return h;
    }

    // Setters

    public void setTileX(int userX)
    {
        this.x = userX;
    }

    public void setTileY(int userY)
    {
        this.y = userY;
    }

    public void setTileW(int userW)
    {
        this.w = userW;
    }

    public void setTileH(int userH)
    {
        this.h = userH;
    }

    // Detect if clicking an existing tile
    public boolean tileDetection(int mouseX, int mouseY)
    {
        return mouseX >= x && mouseX <= x + w && 
               mouseY >= y && mouseY <= y + h;
    }

    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }
}
