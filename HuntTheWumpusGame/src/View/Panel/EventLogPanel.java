package View.Panel;

import javax.swing.*;
import java.awt.*;
import Controller.WumpusGameController;
import Controller.WumpusGameControllerInterface;
import Model.WumpusGameModelnterface;
import View.ModelListener;

public class EventLogPanel extends JPanel implements ModelListener {
    private WumpusGameControllerInterface controller;
    private JTextArea eventLog;
    private JPanel statsPanel;
    private LegendPanel legendPanel;
    private JPanel cheatInfo;
    
    public EventLogPanel(WumpusGameControllerInterface controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createTitledBorder("Game Messages"));
        
        setupEventLog();
        setupStatsPanel();
        setupCheatInfo();
    }
    
    private void setupEventLog() {
        eventLog = new JTextArea(4, 40);
        eventLog.setEditable(false);
        eventLog.setFont(new Font("Arial", Font.PLAIN, 12));
        eventLog.setBackground(new Color(250, 250, 250));
        eventLog.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(eventLog);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void setupStatsPanel() {
        statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statsPanel.setOpaque(false);
        updateStatsPanel();
        add(statsPanel, BorderLayout.NORTH);
    }
    
    private void setupCheatInfo() {
        cheatInfo = new JPanel(new BorderLayout());
        legendPanel = new LegendPanel(controller);
        
        cheatInfo.add(legendPanel, BorderLayout.CENTER);
        add(cheatInfo, BorderLayout.SOUTH);
        
        legendPanel.setVisible(controller.cheatsEnabled());
    }
    
    public void updateEventLog() {
        WumpusGameModelnterface model = controller.getModel();
        String message = model.getMessage();
        
        if(message != null && !message.isEmpty()) {
            eventLog.setText("");
            eventLog.append(message);
            eventLog.setCaretPosition(eventLog.getDocument().getLength());
        }
    }
    
    public void updateStatsPanel() {
        WumpusGameModelnterface model = controller.getModel();
        statsPanel.removeAll();
        statsPanel.add(new JLabel("Arrows: " + model.getArrowCount()));
        statsPanel.add(new JLabel(" | Cave: " + model.getPlayerCave().getID()));
        statsPanel.revalidate();
    }
    
    public void update() {
        updateEventLog();
        updateStatsPanel();
    }
    
    public JPanel getCheatInfo() {
        return cheatInfo;
    }
    
    public LegendPanel getLegendPanel() {
        return legendPanel;
    }
}