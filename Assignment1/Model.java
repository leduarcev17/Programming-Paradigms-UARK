public class Model {
    private int turtleX;
	private int turtleY;
	private int turtleDestX;
	private int turtleDestY;
	private int turtleSpeed;

	public Model()
	{
		turtleSpeed = 4; // Sets the amount of pixels the turtle will move in one action
	}

	public void update()
	{
		// Move the turtle
		if(this.turtleX < this.turtleDestX)
			this.turtleX += Math.min(turtleSpeed, turtleDestX - turtleX); // This will choose the minimum amount of pixels to move
		else if(this.turtleX > this.turtleDestX)
			this.turtleX -= Math.min(turtleSpeed, turtleX - turtleDestX);
		if(this.turtleY < this.turtleDestY)
			this.turtleY += Math.min(turtleSpeed, turtleDestY - turtleY);
		else if(this.turtleY > this.turtleDestY)
			this.turtleY -= Math.min(turtleSpeed, turtleY - turtleDestY);
	}

	// Class Setters
	public void setTurtleDestination(int x, int y)
	{
		this.turtleDestX = x;
		this.turtleDestY = y;
	}
	
    public void setTurtleX(int x)
    {
        this.turtleX = x;
    }

    public void setTurtleY(int y)
    {
        this.turtleY = y;
    }

    public void setTurtleSpeed(int speed)
    {
        this.turtleSpeed =speed;
    }

	// Class Gettters
	public int getTurtleX()
	{
		return turtleX;
	}
	
	public int getTurtleY()
	{
		return turtleY;
	}

    public int getTurtleDestX()
    {
        return turtleDestX;
    }

    public int getTurtleDestY()
    {
        return turtleDestY;
    }
    
    public int getTurtleSpeed()
    {
        return turtleSpeed;
    }

	// Set the turtle destination coordinates depending on which key we're pressing
	public void turtleMovesRight()
	{
		this.turtleDestX = getTurtleX() + turtleSpeed;
	}

	public void turtleMovesLeft()
	{
		this.turtleDestX = getTurtleX() - turtleSpeed;
	}

	public void turtleMovesUp()
	{
		this.turtleDestY = getTurtleY() - turtleSpeed;
	}

	public void turtleMovesDown()
	{
		this.turtleDestY = getTurtleY() + turtleSpeed;
	}
}
