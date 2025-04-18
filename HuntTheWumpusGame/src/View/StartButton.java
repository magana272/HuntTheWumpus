package View;

import javax.swing.*;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controller.WumpusGameController;
import Controller.WumpusGameControllerInterface;

public class StartButton extends JButton implements ActionListener {
    private WumpusGameControllerInterface controller;
    public StartButton(String name, WumpusGameControllerInterface controller) {
        super(name);
        this.controller = controller; 
        this.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        controller.startGame();
        System.out.println("Start button clicked!");
        Container parent = this.getParent();
        if(parent != null) {
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
        }
    }
}
