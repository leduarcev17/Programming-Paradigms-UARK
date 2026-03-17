//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 3/10/26
// Assignment Description: The object of this assignment is to use inheritance
//                         to make our code cleaner and eassier to extend and mantain,
//                         secondly, we had to implement new sprites using all we have
//                         learned in previous assignments.
//************************************************************************  

import java.util.ArrayList;
import java.util.Iterator;

public class Model {
    private ArrayList<Sprite> sprites;
    private static ArrayList<Sprite> itemsICanAdd;
    private MsPacman msPacman;
    private static boolean editMode, addMapItem;
    private static int itemNum = 0;
    private static final int AVAILABLE_GHOSTS = 4;
    private static final int AVAILABLE_FRUITS = 7;
    private static final int EDIT_MODE_COORDINATES = 30;

    // Constructor
    public Model()
    {
        sprites = new ArrayList<Sprite>();
        editMode = false;
        addMapItem = true;
        setItemsList();
    }

    // Set List of addable items
    private void setItemsList()
    {
        itemsICanAdd = new ArrayList<Sprite>();
        itemsICanAdd.add(new Tile(EDIT_MODE_COORDINATES, EDIT_MODE_COORDINATES));

        for(int i = 0; i < AVAILABLE_GHOSTS; i++)
        {
            Ghost ghost = new Ghost(EDIT_MODE_COORDINATES, EDIT_MODE_COORDINATES, i);
            ghost.setH(40);
            ghost.setW(40);
            itemsICanAdd.add(ghost);
        }

        for(int i = 0; i < AVAILABLE_FRUITS; i++)
        {
            Fruit fruit = new Fruit(EDIT_MODE_COORDINATES, EDIT_MODE_COORDINATES, i);
            fruit.setH(40);
            fruit.setW(40);
            itemsICanAdd.add(fruit);
        }
    }

    // Getters
    public int getItemsCount()
    {
        return itemsICanAdd.size();
    }

    public Sprite getItem()
    {
        return itemsICanAdd.get(itemNum);
    }

    public int getSpritesCount()
    {
        return sprites.size();
    }

    public Sprite getSprite(int index)
    {
        return sprites.get(index); 
    }

    public MsPacman getMsPacman()
    {
        return this.msPacman;
    }

    public boolean getEditMode()
    {
        return editMode;
    }

    public boolean getAddMapItem()
    {
        return addMapItem;
    }

    public Iterator<Sprite> iterator()
    {
        return sprites.iterator();
    }

    // Setters
    public void setEditMode(boolean value)
    {
        this.editMode = value;
    }

    public void setAddMapItem(boolean value)
    {
        this.addMapItem = value;
    }

    // Change item to add or remove in editMode
    public void changeItemNum()
    {
        if(itemNum >= getItemsCount() -1)
        {
            itemNum = 0;
            return;
        }
        itemNum++;
    }
    // Add Sprites
    public void addTile(int mouseX, int mouseY)
    {
        int x = Math.floorDiv(mouseX, 40) * 40;
        int y = Math.floorDiv(mouseY,40) * 40;

        // Detect if there already exists a tile in that position
        for(Sprite sprite : sprites)
        {
            if(sprite.spriteDetection(x + 1, y + 1))
            {
                return;
            }
        }

        Tile t = new Tile(x, y);
        sprites.add(t);
    }

    public void addFruit(int mouseX, int mouseY, int fruitNum)
    {
        for(Sprite sprite : sprites)
        {
            if(detectCollision(new Fruit(mouseX, mouseY, fruitNum), sprite))
            {
                return;
            }
        }

        Fruit f = new Fruit(mouseX, mouseY, fruitNum);
        sprites.add(f);
    }

    public void addGhost(int mouseX, int mouseY, int ghostNum)
    {
        for(Sprite sprite : sprites)
        {
            if(detectCollision(new Ghost(mouseX, mouseY, ghostNum), sprite))
            {
                return;
            }
        }

        Ghost g = new Ghost(mouseX, mouseY, ghostNum);
        sprites.add(g);
    }

    // Remove Sprites
    public void removeTile(int mouseX, int mouseY)
    {        

        for(int i = 0; i < sprites.size(); i++)
        {
            if(sprites.get(i).isTile())
            {
                if(sprites.get(i).spriteDetection(mouseX, mouseY))
                {
                    sprites.remove(i);
                    return;
                }
            }
        }
    }

    public void removeGhost(int mouseX, int mouseY)
    {        

        for(int i = 0; i < sprites.size(); i++)
        {
            if(sprites.get(i).isGhost())
            {
                if(sprites.get(i).spriteDetection(mouseX, mouseY))
                {
                    sprites.remove(i);
                    return;
                }
            }
        }
    }

    public void removeFruit(int mouseX, int mouseY)
    {        

        for(int i = 0; i < sprites.size(); i++)
        {
            if(sprites.get(i).isFruit())
            {
                if(sprites.get(i).spriteDetection(mouseX, mouseY))
                {
                    sprites.remove(i);
                    return;
                }
            }
        }
    }

    public void clearSprites()
    {
        sprites.clear();
    }

    // Json file handler
    public Json marshal()
    {
        Json ob = Json.newObject();
        Json TilesTmpList = Json.newList();
        Json GhostTmpList = Json.newList();
        Json FruitTmpList = Json.newList();
        ob.add("tiles", TilesTmpList);
        ob.add("ghosts", GhostTmpList);
        ob.add("fruits", FruitTmpList);
        for(int i = 0; i < sprites.size(); i++)
        {
            if(sprites.get(i).isMsPacman())
            {
                ob.add("mspacman", msPacman.marshal());
            }
            else if(sprites.get(i).isTile())
            {
                TilesTmpList.add(sprites.get(i).marshal());
            }
            else if(sprites.get(i).isGhost())
            {
                GhostTmpList.add(sprites.get(i).marshal());
            }
            else if(sprites.get(i).isFruit())
            {
                FruitTmpList.add(sprites.get(i).marshal());
            }
        }
        return ob;
    }

    public void unmarshal(Json map)
    {
        sprites.clear();

        Json msPacmanJson = map.get("mspacman");
        msPacman = new MsPacman(msPacmanJson);
        sprites.add(msPacman);

        Json TileTmpList = map.get("tiles");
        Json GhostTmpList = map.get("ghosts");
        Json FruitTmpList = map.get("fruits");

        for(int i = 0; i < TileTmpList.size(); i++)
        {
            sprites.add(new Tile(TileTmpList.get(i)));
        }
        for(int i = 0; i < GhostTmpList.size(); i++)
        {
            sprites.add(new Ghost(GhostTmpList.get(i)));
        }
        for(int i = 0; i < FruitTmpList.size(); i++)
        {
            sprites.add(new Fruit(FruitTmpList.get(i)));
        }
    }

    // Detect if a Sprite is colliding with another
    private boolean detectCollision(Sprite a, Sprite b)
    {
        if(a.getRight() <= b.getLeft())
            return false;

        if(a.getLeft() >= b.getRight())
            return false;

        if(a.getBottom() <= b.getTop())
            return false;

        if(a.getTop() >= b.getBottom())
            return false;

        return true;
    }

    // Update Model Method
    public void update()
    {
        Iterator<Sprite> it = sprites.iterator();
        while(it.hasNext())
        {
            Sprite s = it.next();

            // Set Sprite's previous Position
            if(s.isFruit() || s.isGhost())
                s.setPrevPosition();

            // Update sprites
            if(!s.update())
                it.remove();

            // Detect collisions
            Iterator<Sprite> it2 = sprites.iterator();
            while(it2.hasNext())
            {
                Sprite other = it2.next();
                if(s != other && detectCollision(s, other))
                {
                    if(s.isMsPacman() && other.isTile())
                    {
                        s.fixCollision(other);
                    }
                    else if(s.isFruit() && other.isTile())
                    {
                        Fruit fruit = (Fruit)s;
                        s.fixCollision(other);
                        fruit.getRandomDirection();
                    }
                    else if(s.isFruit() && other.isMsPacman())
                    {
                        Fruit fruit = (Fruit)s;
                        fruit.Eaten();
                    }
                    else if(s.isGhost() && other.isTile())
                    {
                        Ghost ghost = (Ghost)s;
                        s.fixCollision(other);
                        ghost.getRandomDirection();
                    }
                    else if(s.isMsPacman() && other.isGhost())
                    {
                        Ghost ghost = (Ghost)other;
                        if(ghost.isEaten() == false)
                            ghost.eatenAnimation();
                    }
                }
            }
        }
    }
}
