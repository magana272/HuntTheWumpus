package View.Panel;

import javax.swing.*;
import java.awt.*;
import Controller.WumpusGameController;
import Controller.WumpusGameControllerInterface;
import View.ModelListener;

public class SettingsPanel extends JPanel implements ModelListener {
    private WumpusGameControllerInterface controller;
    private JButton cheatButton;
    private JButton restartButton;
    private JPanel legendPanel;
    private JPanel cheatInfo;

    public SettingsPanel(WumpusGameControllerInterface controller, JPanel legendPanel, JPanel cheatInfo) {
        this.controller = controller;
        this.legendPanel = legendPanel;
        this.cheatInfo = cheatInfo;
        
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(50, 20));
        setBackground(Color.WHITE);
        
        setupButtons();
    }
    
    private void setupButtons() {
        cheatButton = new JButton("Cheat Mode");
        cheatButton.addActionListener(e -> {
            controller.setCheatsEnabled();
        });

        restartButton = new JButton("Restart Game");
        restartButton.addActionListener(e -> {
            controller.startGame();
            cheatButton.setText("Enable Cheat Mode");
        });

        add(cheatButton, BorderLayout.WEST);
        add(restartButton, BorderLayout.EAST);
    }


    @Override
    public void update() {
        cheatButton.setText(controller.cheatsEnabled() ? "Disable Cheat Mode" : "Enable Cheat Mode");

        // Update visibility of cheat-related components
        cheatInfo.setVisible(controller.cheatsEnabled());
        legendPanel.setVisible(controller.cheatsEnabled());

        // Refresh components
        cheatInfo.revalidate();
        cheatInfo.repaint();
        cheatButton.setText(controller.cheatsEnabled() ? "Disable Cheat Mode" : "Enable Cheat Mode");


    }
}