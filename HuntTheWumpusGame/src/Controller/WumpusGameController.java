package Controller;

import Model.Cave;
import Model.WumpusGameModel;
import Model.WumpusGameModelHard;
import View.WumpusGameEndView;
import View.WumpusGameStartView;
import View.WumpusGameView;
import View.WumpusViewInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class WumpusGameController {
    private WumpusGameModel model;
    private WumpusViewInterface view;
    private JFrame gameWindow;

    public WumpusGameController(){
        this.view = new WumpusGameStartView(this);
        this.gameWindow = new JFrame();
        view.setVisible(true);
    
    }

    public void startScreen(){
        view.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setTitle("Hunt the Wumpus");
        gameWindow.setResizable(true);
        gameWindow.remove((Component) this.view);
        this.view = new WumpusGameStartView(this);
        this.model = new WumpusGameModel();
        gameWindow.add((Component) this.view, BorderLayout.CENTER);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
        gameWindow.revalidate();

    }

    public void startGame(){
        gameWindow.remove((Component) this.view);
        this.view = new WumpusGameView(this);
        this.model = new WumpusGameModel();
        gameWindow.add((Component) this.view, BorderLayout.CENTER);
        gameWindow.revalidate();
    }

    public void gameOver(){
        gameWindow.remove((Component) this.view);
        this.view = new WumpusGameEndView(this);
        gameWindow.add((Component) this.view, BorderLayout.CENTER);
        gameWindow.revalidate();
    }
    public String getMessage(){
        return model.getMessage();
    }
    public WumpusGameModel getModel() {
        return model;
    }
    public Cave getPlayerPostion(){
        return model.getPlayerCave();
    }

    public void moveToCave(int id) {
       Cave playerCave = model.getPlayerCave();
        if (playerCave.getID() == id) {
              return; 
        }
        if(id < 0 || id >= model.getDodecahedron().getCaves().size()) {
            return;
        }
        if (playerCave.getEdgesToVertexId().contains(id)) {
            model.movePlayer(id);
        }
        validateGameState();
       
    }
    public void shootArrow(int id) {
        Cave playerCave = model.getPlayerCave();
        if (playerCave.getID() == id) {
            return; 
        }
        if (id < 0 || id >= model.getDodecahedron().getCaves().size()) {
            return;
        }
        if (playerCave.getEdgesToVertexId().contains(id)) {
            model.shootArrow(id);
        }
        validateGameState();
    }
    public void validateGameState() {
        System.out.println("Validating game state...");
        if(model.wumpusIsAlive() == false) {
            System.out.println("Wumpus is dead!In gameOver()");
            gameOver();
        } 
        if(model.getPlayerCave().containsWumpus()) {
            gameOver();
        } else if(model.getPlayerCave().containsPit()) {
            gameOver();
        } else if(model.getArrowCount() == 0) {
            gameOver();
        }
    }

    public boolean cheatsEnabled() {
        return model.cheatsEnabled();
    }

    public void setCheatsEnabled() {
        model.setCheats();
    }


}
