//***********************************************************************
// Name: Eduardo Arce Vargas
// Date: 2/10/26
// Assignment Description: The objective of this assignment is to build a 
//                         map editor that is able to draw and remove tiles on
//                         the display using an array list, also, it has to be able
//                         save and load maps in a .json file
//************************************************************************ 

import javax.swing.JFrame;
import java.awt.Toolkit;


public class Game extends JFrame {
    // Declaring Member variables
    private boolean keepGoing;
    private Controller controller;
    private View view;
    private Model model;

    public Game()
    {
        keepGoing = true;
        model = new Model();
        controller = new Controller(model);
        view = new View(controller, model);
        view.addMouseListener(controller);
        view.addMouseMotionListener(controller);
        this.addKeyListener(controller);

        //Configuring the window
        this.setTitle("A2 - Map Editor");
        this.setSize(776, 800);
        this.setFocusable(true);
        this.getContentPane().add(view);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void run()
    {
        do
        {
            keepGoing = controller.update();
            view.repaint();
            Toolkit.getDefaultToolkit().sync();

            try
			{
				Thread.sleep(50);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
        }
        while(keepGoing);

        System.out.println("Goodbye!!!!");
        System.exit(0);
    }

    public static void main(String[] args)
    {
        Game g = new Game();
        g.run();
    }
}
