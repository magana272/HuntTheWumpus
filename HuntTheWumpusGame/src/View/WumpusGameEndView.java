package View;

import Controller.WumpusGameController;
import Controller.WumpusGameControllerInterface;

import javax.swing.*;

public class WumpusGameEndView extends JPanel implements WumpusViewInterface {

    private WumpusGameControllerInterface controller;

    public WumpusGameEndView(WumpusGameControllerInterface controller) {
        this.controller = controller;
        setUp();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void setUp() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel messageLabel = new JLabel("Game Over! " + controller.getMessage());
        messageLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(messageLabel);

        JButton restartButton = new JButton("Restart Game");
        restartButton.setAlignmentX(CENTER_ALIGNMENT);
        restartButton.addActionListener(e -> {
            System.out.println("Restarting game...");
            controller.startScreen();
        });
        add(restartButton);

    }

    @Override
    public void setController(WumpusGameControllerInterface controller) {
       this.controller = controller;
    }
    @Override
    public WumpusGameControllerInterface getController() {
        return this.controller;
    }

    @Override
    public void update() {

    }
}
