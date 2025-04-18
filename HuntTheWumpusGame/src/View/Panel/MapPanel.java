package View.Panel;

import javax.swing.*;
import java.awt.*;

import Controller.WumpusGameControllerInterface;
import Model.GameBoard.Cave;
import Model.GameBoard.Map;
import Model.WumpusGameModelnterface;
import View.ModelListener;

public class MapPanel extends JPanel implements ModelListener {
    private WumpusGameControllerInterface controller;
    
    private static final Color PLAYER_COLOR = Color.BLUE;
    private static final Color BAT_PIT_COLOR = Color.MAGENTA;
    private static final Color BAT_COLOR = Color.CYAN;
    private static final Color WUMPUS_COLOR = Color.RED;
    private static final Color PIT_COLOR = Color.YELLOW;
    private static final Color EMPTY_COLOR = Color.LIGHT_GRAY;
    private static final Color ADJACENT_CAVE_COLOR = Color.PINK;
    
    public MapPanel(WumpusGameControllerInterface controller) {
        this.controller = controller;
        setBackground(Color.WHITE);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawMap(g2d);
        drawPlayer(g);
    }
    
    public void drawMap(Graphics2D g) {
        WumpusGameModelnterface model = controller.getModel();
        Map map = model.getmap();
        Cave player = controller.getPlayerPostion();
        
        // First draw all edges
        for(int i = 0; i < map.getCaves().size(); i++) {
            Cave cave = map.getCaves().get(i);
            // Draw edges
            for (int j = 0; j < cave.getEdgesToVertexId().size(); j++) {
                Integer edgeIndex = cave.getEdgesToVertexId().get(j);
                if (edgeIndex != -1) {
                    g.setColor(Color.GRAY);
                    g.setStroke(new BasicStroke(5));
                    g.drawLine(cave.getX() + 30, cave.getY() + 30, 
                              map.getCaves().get(edgeIndex).getX() + 30,
                              map.getCaves().get(edgeIndex).getY() + 30);
                }
            }
        }
        
        // Then draw all caves
        for (int i = 0; i < map.getCaves().size(); i++) {
            Cave cave = map.getCaves().get(i);
            
            // Set cave color based on game state and cheat settings
            if(controller.cheatsEnabled()){
                if (cave.containsBat() && cave.containsPit()) {
                    g.setColor(BAT_PIT_COLOR);
                }
                else if(cave.containsBat()){
                    g.setColor(BAT_COLOR);
                }
                else if(cave.containsWumpus()){
                    g.setColor(WUMPUS_COLOR);
                } 
                else if(cave.containsPit()){
                    g.setColor(PIT_COLOR);
                } 
                else {
                    g.setColor(EMPTY_COLOR);
                }
            }
            else {
                // Mark adjacent caves with different color
                if (cave.getID() == player.getEdgesToVertexId().get(0) || 
                    cave.getID() == player.getEdgesToVertexId().get(1) || 
                    cave.getID() == player.getEdgesToVertexId().get(2)){
                    g.setColor(ADJACENT_CAVE_COLOR);
                } else {
                    g.setColor(EMPTY_COLOR);
                }   
            }
            
            // Draw cave
            g.fillRect(cave.getX(), cave.getY(), 60, 60);
            g.setFont(getFont().deriveFont(12f));
            g.setColor(Color.BLACK);
            g.drawRect(cave.getX(), cave.getY(), 60, 60);
            g.drawString("Cave " + cave.getID(), cave.getX() + 10, cave.getY() + 30);
        }
    }
    
    public void drawPlayer(Graphics g) {
        WumpusGameModelnterface model = controller.getModel();
        Cave playerCave = controller.getPlayerPostion();
        g.setColor(PLAYER_COLOR);
        g.fillRect(playerCave.getX()+20, playerCave.getY()+30, 20, 20);
    }

    @Override
    public void update() {
        this.repaint();
    }
}