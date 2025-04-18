package View;

import Controller.WumpusGameControllerInterface;
import Model.GameBoard.Cave;
import Model.GameBoard.Map;

import javax.swing.*;
import static javax.swing.SwingUtilities.isLeftMouseButton;

import Controller.WumpusGameController;
import Model.WumpusGameModelnterface;
import View.Panel.EventLogPanel;
import View.Panel.MapPanel;
import View.Panel.SettingsPanel;

import java.awt.*;

public class WumpusGameView extends JPanel implements WumpusViewInterface, ModelListener{
    private MapPanel mapPanel;
    private EventLogPanel eventLogPanel;
    private SettingsPanel settingsPanel;
    private WumpusGameControllerInterface controller;

    public WumpusGameView(WumpusGameControllerInterface controller) {
        this.controller = controller;
        this.setUp();
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int x = evt.getX();
                int y = evt.getY();
                if(isLeftMouseButton(evt)) {
                    onMouseClick(x, y, true);
                } else {
                    onMouseClick(x, y, false);
                }
            }
        });
    }
    @Override
    public void setUp() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        eventLogPanel = new EventLogPanel(controller);
        
        settingsPanel = new SettingsPanel(
            controller, 
            eventLogPanel.getLegendPanel(), 
            eventLogPanel.getCheatInfo()
        );
        mapPanel = new MapPanel(controller);
        
        this.add(settingsPanel, BorderLayout.NORTH);
        this.controller.getModel().registerObserver(settingsPanel);
        this.add(mapPanel, BorderLayout.CENTER);
        this.controller.getModel().registerObserver(mapPanel);
        this.add(eventLogPanel, BorderLayout.SOUTH);
        this.controller.getModel().registerObserver(eventLogPanel);

        this.setPreferredSize(new Dimension(800, 800));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        eventLogPanel.update();
        mapPanel.repaint();
    }

    @Override
    public void setController(WumpusGameControllerInterface controller) {
        this.controller = controller;
    }

    @Override
    public WumpusGameControllerInterface getController() {
        return this.controller;
    }

    public void onMouseClick(int x, int y, boolean isLeftMouseButton) {
        WumpusGameModelnterface model = controller.getModel();
        Map dodecahedron = model.getmap();
        
        if(isLeftMouseButton) {
            for (Cave cave : dodecahedron.getCaves()) {
                if (cave.getX() <= x && x <= cave.getX() + 60 && 
                    cave.getY() <= y && y <= cave.getY() + 60) {
                    controller.moveToCave(cave.getID());
//                    repaint();
                    return;
                }
            }
        } else {
            for (Cave cave : dodecahedron.getCaves()) {
                if (cave.getX() <= x && x <= cave.getX() + 60 && 
                    cave.getY() <= y && y <= cave.getY() + 60) {
                    controller.shootArrow(cave.getID());
                    System.out.println("Shot arrow at Cave " + cave.getID());
//                    repaint();
                    return;
                }
            }
        } 
    }

    @Override
    public void update() {
        repaint();
    }
}