package View.Panel;

import javax.swing.*;

import Controller.WumpusGameController;
import Controller.WumpusGameControllerInterface;
import Model.WumpusGameModelnterface;
import View.ModelListener;

import java.awt.*;

public class LegendPanel extends JPanel implements ModelListener {
    private static final Color PLAYER_COLOR = Color.BLUE;
    private static final Color BAT_PIT_COLOR = Color.MAGENTA;
    private static final Color BAT_COLOR = Color.CYAN;
    private static final Color WUMPUS_COLOR = Color.RED;
    private static final Color PIT_COLOR = Color.YELLOW;
    private static final Color EMPTY_COLOR = Color.LIGHT_GRAY;
    private WumpusGameControllerInterface controller;
    
    public LegendPanel(WumpusGameControllerInterface controller) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(createLegendItem(PLAYER_COLOR, "Player"));
        add(createLegendItem(BAT_COLOR, "Bat"));
        add(createLegendItem(WUMPUS_COLOR, "Wumpus"));
        add(createLegendItem(BAT_PIT_COLOR, "Bat and Pit"));
        add(createLegendItem(PIT_COLOR, "Pit"));
        add(createLegendItem(EMPTY_COLOR, "Empty"));
        this.controller = controller;
    }
    
    private JPanel createLegendItem(Color color, String label) {
        JPanel item = new JPanel();
        JLabel colorBox = new JLabel("     ");
        colorBox.setOpaque(true);
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(15, 15));
        JLabel textLabel = new JLabel(label);

        item.add(colorBox);
        item.add(textLabel);
        return item;
    }

    @Override
    public void update() {
        this.setVisible(controller.cheatsEnabled());
    }
}