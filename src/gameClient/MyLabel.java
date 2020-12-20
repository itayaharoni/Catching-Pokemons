package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the Frame on which the login screen to the game is being displayed on.
 */
public class MyLabel extends JFrame implements ActionListener {
    private JLabel label;
    private JLabel label2;
    private JTextField tf;
    private JTextField tf2;
    private JButton button;
    private long id;
    private int level;
    /**
     * Default constructor to the class, initializes the parameters of this instance,
     * and sets the screen to default settings.
     */
    public MyLabel() {
        super("Login screen");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        label = new JLabel("Please enter game level: ");
        label2 = new JLabel("Please enter your ID: ");
        button = new JButton("Start");
        tf = new JTextField();
        tf2 = new JTextField();
        setLayout(null);
        setSize(260,370);
        setLocationRelativeTo(null);
        tf.setBounds(50,100,150,20);
        label.setBounds(50,50,250,20);
        tf2.setBounds(50,200,150,20);
        label2.setBounds(50,150,250,20);
        button.setBounds(75,250,95,20);
        add(tf);
        add(tf2);
        add(label);
        add(label2);
        add(button);
        ImageIcon image=new ImageIcon("./Images/Pokeball.png");
        setIconImage(image.getImage());
        setVisible(true);
        button.addActionListener(this);

    }
    /**
     * This method returns the id of this class.
     * @return id
     */
    public long getId() {
        return id;
    }
    /**
     * This method returns the level of this class.
     * @return level
     */
    public int getLevel() {
        return level;
    }
    /**
     * This method disposes of this instance if the user inserted
     * to the login screen a valid ID and level to start the game
     * and pressed the "Start" button.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(button)) {
            String levelS = tf.getText();
            String idS = tf2.getText();
            try {
                id=Long.parseLong(idS);
                level=Integer.parseInt(levelS);
                Thread client = new Thread(new Ex2());
                client.start();
                dispose();
            }catch (Exception error){
               new MyLabelError();
            }
        }
    }
}