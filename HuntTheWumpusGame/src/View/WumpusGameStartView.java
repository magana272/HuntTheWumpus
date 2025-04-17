package View;

import java.awt.*;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Controller.WumpusGameController;

public class WumpusGameStartView extends JPanel implements WumpusViewInterface {
    WumpusGameController controller;
    public WumpusGameStartView(WumpusGameController controller) {
        this.controller = controller;
        setPreferredSize(new Dimension(750, 800));
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                resized(e);
            }
        });
        setUp();
    }
    public void setUp() {  
        setLayout(new BorderLayout());
        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new BorderLayout());
        bottomRightPanel.setOpaque(false);  // Make it transparent
        bottomRightPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20)); // Add padding
        JTextArea instructions = new JTextArea(
            "Instructions: \n" +
            "1. Left Click to Move\n" +
            "2. Right Click to shoot an arrow.\n" +
            "3. Avoid the Wumpus and pits!\n"
        );
        instructions.setOpaque(false);  
        instructions.setEditable(false);
        instructions.setForeground(Color.red);  
        instructions.setFont(new Font("Arial", Font.BOLD, 16));
        
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        StartButton startButton = new StartButton("Start Game", controller);
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBackground(new Color(0, 0, 0));
        startButton.setForeground(Color.WHITE);
        
   
        buttonPanel.add(startButton);
        
    
        bottomRightPanel.add(instructions, BorderLayout.NORTH);
        bottomRightPanel.add(buttonPanel, BorderLayout.CENTER);
        
    
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(bottomRightPanel, BorderLayout.SOUTH);
        
        add(wrapperPanel, BorderLayout.EAST);
    }
    @Override
    public void setController(WumpusGameController controller) {
        this.controller = controller;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String imagePath = "./HuntTheWumpusGame/src/static/Hunt the Wumpus.PNG";
        ImageIcon wumpusImage = new ImageIcon(imagePath);
        g.drawImage(wumpusImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        
    }

    public void resized(ComponentEvent e){
        int width = getWidth();
        int height = getHeight();
        setPreferredSize(new Dimension(width, height));
        revalidate();
        repaint();
    }
    
    
}
