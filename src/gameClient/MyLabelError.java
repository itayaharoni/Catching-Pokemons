package gameClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents an error message in the occurrence
 * that the user inserted an invalid ID or level in the game login screen.
 */
public class MyLabelError extends JFrame implements ActionListener {
    JButton b;
    JLabel label;
    /**
     * Default constructor to the class, initializes the class parameters.
     */
    public MyLabelError(){
        super("Login error");
        b=new JButton("Ok");
        label=new JLabel("Invalid ID or Level!");
        setLayout(null);
        setSize(250,150);
        label.setBounds(55,30,150,20);
        b.setBounds(80,80,50,20);
        add(b);
        add(label);
        setVisible(true);
        b.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    /**
     * This method disposes of this instance if the user clicks on the "ok" button.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(b)){
            dispose();
        }
    }
}
