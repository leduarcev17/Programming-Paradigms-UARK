//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 2/10/26
// Assignment Description: The objective of this assignment is to build a 
//                         map editor that is able to draw and remove tiles on
//                         the display using an array list, also, it has to be able
//                         save and load maps in a .json file
//************************************************************************ 

import java.util.ArrayList;

public class Model {
    private ArrayList<Tile> tiles;

    public Model()
    {
        tiles = new ArrayList<Tile>();
    }

    public int getTileCount()
    {
        return tiles.size();
    }

    public Tile getTile(int index)
    {
        return tiles.get(index); 
    }

    public void addTile(int mouseX, int mouseY)
    {
        int x = Math.floorDiv(mouseX, 40) * 40;
        int y = Math.floorDiv(mouseY,40) * 40;

        // Detect if there already exists a tile in that position
        for(Tile tile : tiles)
        {
            if(tile.tileDetection(x + 1, y + 1))
            {
                return;
            }
        }

        Tile t = new Tile(x, y);
        tiles.add(t);
    }

    public void removeTile(int mouseX, int mouseY)
    {
        for(int i = 0; i < tiles.size(); i++)
        {
            if(tiles.get(i).tileDetection(mouseX, mouseY))
            {
                tiles.remove(i);
                return;
            }
        }
    }

    public void clearTiles()
    {
        tiles.removeAll(tiles);
    }

    // Json file handler
    public Json marshal()
    {
        Json ob = Json.newObject();
        Json tmpList = Json.newList();
        ob.add("tiles", tmpList);
        for(int i = 0; i < tiles.size(); i++)
        {
            tmpList.add(tiles.get(i).marshal());
        }
        return ob;
    }

    public void unmarshal(Json map)
    {
        tiles.clear();
        Json tmpList = map.get("tiles");
        for(int i = 0; i < tmpList.size(); i++)
        {
            tiles.add(new Tile(tmpList.get(i)));
        }
    }
}
