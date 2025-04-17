package View;

import Model.Cave;
import Model.Dodecahedron;
import Model.WumpusGameModel;

import javax.swing.*;
import javax.swing.JTextArea;
import static javax.swing.SwingUtilities.isLeftMouseButton;

import Controller.WumpusGameController;

import java.awt.*;

public class WumpusGameView extends JPanel implements WumpusViewInterface{
    private static final long serialVersionUID = 1L;
    private JPanel mainPanel;
    private JTextArea eventLog;
    private JPanel eventLogPanel;
    private JPanel statsPanel;
    private JPanel settingPanel;
    private JPanel legendPanel;
    private JPanel cheatInfo;
    private WumpusGameController controller;


    private static final Color PLAYER_COLOR = Color.BLUE;
    private static final Color BAT_PIT_COLOR = Color.MAGENTA;
    private static final Color BAT_COLOR = Color.CYAN;
    private static final Color WUMPUS_COLOR = Color.RED;
    private static final Color PIT_COLOR = Color.YELLOW;
    private static final Color EMPTY_COLOR = Color.LIGHT_GRAY;

    
    Graphics2D g;
    public WumpusGameView(WumpusGameController controller) {
        this.controller = controller;
        this.setUp();
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int x = evt.getX();
                int y = evt.getY();
                if(isLeftMouseButton(evt)){
                    // Move
                    onMouseClick(x, y, true);
                }
                else{
                    onMouseClick(x, y, false);
                }
            }
        });
    }
    public void drawMap(Graphics2D g){
        WumpusGameModel model = controller.getModel();
        Dodecahedron dodecahedron = model.getDodecahedron();
        Cave player = controller.getPlayerPostion();
        for(int i = 0; i < dodecahedron.getCaves().size(); i++) {
            Cave cave = dodecahedron.getCaves().get(i);
                        // Draw edges
            for (int j = 0; j < cave.getEdgesToVertexId().size(); j++) {
                Integer edgeIndex = cave.getEdgesToVertexId().get(j);
                if (edgeIndex != -1) {
                    g.setColor(Color.GRAY);
                    g.setStroke(new BasicStroke(5));
                    g.drawLine(cave.getX() + 30, cave.getY() + 30, dodecahedron.getCaves().get(edgeIndex).getX() + 30, dodecahedron.getCaves().get(edgeIndex).getY() + 30);
                }
            }
           
        }
        for (int i = 0; i < dodecahedron.getCaves().size(); i++) {
            Cave cave = dodecahedron.getCaves().get(i);
            if(this.controller.cheatsEnabled()){
                if (cave.containsBat() && cave.containsPit()) {
                    g.setColor(BAT_PIT_COLOR);
                }
                else if(cave.containsBat()){
                    g.setColor(BAT_COLOR);
                }
                else if(cave.containsWumpus()){
                    g.setColor(WUMPUS_COLOR);
                } else if(cave.containsPit()){
                    g.setColor(PIT_COLOR);
                } else {
                    g.setColor(EMPTY_COLOR);
                }


            }
            else{
                if (cave.getID() == player.getEdgesToVertexId().get(0) || cave.getID() == player.getEdgesToVertexId().get(1) || cave.getID() == player.getEdgesToVertexId().get(2)){
                    g.setColor(Color.PINK);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }   

            }
            g.fillRect(cave.getX(), cave.getY(), 60, 60);
            g.setFont(getFont().deriveFont(12f));
            g.setColor(Color.BLACK);
            g.drawRect(cave.getX(), cave.getY(), 60, 60);
            g.drawString("Cave " + cave.getID(), cave.getX() + 10, cave.getY() + 30);
            
           
        }
    }


    public void setUp() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        setupSettingPanel();      // Top buttons
        setupMainPanel();         // Map drawing
        setupEventLogPanel();     // Bottom log + stats + legend

        this.add(settingPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(eventLogPanel, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(800, 800));
    }
    private void setupSettingPanel() {
        settingPanel = new JPanel(new BorderLayout());
        settingPanel.setPreferredSize(new Dimension(50, 20));
        settingPanel.setBackground(Color.WHITE);

        JButton cheatButton = new JButton("Cheat Mode");
        cheatButton.addActionListener(e -> {
            controller.setCheatsEnabled();
            cheatButton.setText(controller.cheatsEnabled() ? "Disable Cheat Mode" : "Enable Cheat Mode");
            mainPanel.revalidate();
            mainPanel.repaint();


            cheatInfo.setVisible(controller.cheatsEnabled());
            legendPanel.setVisible(controller.cheatsEnabled());
            cheatInfo.revalidate();
            cheatInfo.repaint();

            eventLogPanel.revalidate();
            eventLogPanel.repaint();
        });

        JButton restartButton = new JButton("Restart Game");
        restartButton.addActionListener(e -> {
            controller.startGame();
            cheatButton.setText("Enable Cheat Mode");
            repaint();
        });

        settingPanel.add(cheatButton, BorderLayout.WEST);
        settingPanel.add(restartButton, BorderLayout.EAST);
    }
    private void setupMainPanel() {
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                drawMap(g2d);
                drawPlayer(g);
            }
        };
        mainPanel.setBackground(Color.WHITE);
    }
    private void setupEventLogPanel() {
        eventLogPanel = new JPanel(new BorderLayout());
        eventLogPanel.setBackground(new Color(240, 240, 240));
        eventLogPanel.setBorder(BorderFactory.createTitledBorder("Game Messages"));

        eventLog = new JTextArea(4, 40);
        eventLog.setEditable(false);
        eventLog.setFont(new Font("Arial", Font.PLAIN, 12));
        eventLog.setBackground(new Color(250, 250, 250));
        eventLog.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(eventLog);
        eventLogPanel.add(scrollPane, BorderLayout.CENTER);

        statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statsPanel.setOpaque(false);
        statsPanel.add(new JLabel("Arrows: " + controller.getModel().getArrowCount()));
        statsPanel.add(new JLabel(" | Cave: " + controller.getModel().getPlayerCave().getID()));
        eventLogPanel.add(statsPanel, BorderLayout.NORTH);

        setupLegend(); // Add the color legend here
    }
    private void setupLegend() {
        cheatInfo = new JPanel(new BorderLayout());
        legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        legendPanel.add(createLegendItem(PLAYER_COLOR, "Player"));
        legendPanel.add(createLegendItem(BAT_COLOR, "Bat"));
        legendPanel.add(createLegendItem(WUMPUS_COLOR, "Wumpus"));
        legendPanel.add(createLegendItem(BAT_PIT_COLOR, "Bat and Pit"));
        legendPanel.add(createLegendItem(PIT_COLOR, "Pit"));
        legendPanel.add(createLegendItem(EMPTY_COLOR, "Empty"));

        cheatInfo.add(legendPanel, BorderLayout.CENTER); // Row of colors
        eventLogPanel.add(cheatInfo, BorderLayout.SOUTH);
        legendPanel.setVisible(controller.cheatsEnabled());
    }

    public void drawPlayer(Graphics g){
        WumpusGameModel model = controller.getModel();
        Dodecahedron dodecahedron = model.getDodecahedron();
        Cave playerCave = controller.getPlayerPostion();
        g.setColor(Color.BLUE);
        g.fillRect(playerCave.getX()+20, playerCave.getY()+30, 20, 20);

    }
    public void writeState(Graphics g) {
        WumpusGameModel model = controller.getModel();
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Arrows Left: " + model.getArrowCount(), 10, 20);
        g.drawString("Player Cave: " + model.getPlayerCave().getID(), 10, 40);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString(model.getMessage(), 10, 60);
        g.setColor(Color.LIGHT_GRAY);
        if(model.getMessage()!=""){
            eventLog.setText(""); // Clear the event log before appending new messages
            eventLog.append(model.getMessage());
        }
        eventLog.setCaretPosition(eventLog.getDocument().getLength()); // Scroll to the bottom
        eventLogPanel.repaint();
        statsPanel.removeAll();
        statsPanel.add(new JLabel("Arrows: " + controller.getModel().getArrowCount()));
        statsPanel.add(new JLabel(" | Cave: " + controller.getModel().getPlayerCave().getID()));
        statsPanel.revalidate();

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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        writeState(g);
        mainPanel.repaint();
    }

    @Override
    public void setController(WumpusGameController controller) {
        this.controller = controller;
    }
    public void onMouseClick(int x, int y, boolean isLeftMouseButton) {
        WumpusGameModel model = controller.getModel();
        Dodecahedron dodecahedron = model.getDodecahedron();
        if(isLeftMouseButton){
            for (Cave cave : dodecahedron.getCaves()) {
                if (cave.getX() <= x && x <= cave.getX() + 60 && cave.getY() <= y && y <= cave.getY() + 60) {
                        controller.moveToCave(cave.getID());
                        repaint();
                        return;
                    }
                }
        } else {
            System.out.println("Right mouse button clicked at (" + x + ", " + y + ")");
            for (Cave cave : dodecahedron.getCaves()) {
                if (cave.getX() <= x && x <= cave.getX() + 60 && cave.getY() <= y && y <= cave.getY() + 60) {
                    controller.shootArrow(cave.getID());
                    System.out.println("Shot arrow at Cave " + cave.getID());
                    repaint();
                    return;
                }
            }
        } 
    }
}
