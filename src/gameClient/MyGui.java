package gameClient;
import api.game_service;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the Frame on which the pokemon game
 * is being displayed on.
 */
public class MyGui extends JFrame {
    MyPanel panel;
    /**
     * Constructor for the class. Initializes the class which this class extends.
     * Initializes the panel of the class.
     */
    public MyGui(String name){
        super(name);
        panel=new MyPanel();
        add(panel);
        Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dimension.width/2,dimension.height/2);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon image=new ImageIcon("./Images/Pokeball.png");
        setIconImage(image.getImage());
        getContentPane().setBackground(Color.white);
        setVisible(true);
    }
    /**
     * This method calls for the paint method of this panel.
     */
    public void paint(){
        panel.repaint();
    }
    /**
     * This method calls for the update method of this panel according
     * the received parameters.
     * @param ar
     * @param game
     */
    public void update(MyArena ar, game_service game){
        panel.update(ar,game);
    }

}
