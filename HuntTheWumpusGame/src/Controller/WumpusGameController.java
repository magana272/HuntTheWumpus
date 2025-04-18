package Controller;

import Model.GameBoard.Cave;
import Model.WumpusGameModel;
import Model.WumpusGameModelnterface;
import View.WumpusGameEndView;
import View.WumpusGameStartView;
import View.WumpusGameView;
import View.WumpusViewInterface;

import javax.swing.*;
import java.awt.*;

public class WumpusGameController extends WumpusGameControllerInterface {
    private WumpusGameModelnterface model;
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
        this.model.registerObserver(this.view);
    }

    public String getMessage(){
        if(model==null){
            return "";
        }
        return model.getMessage();
    }

    public Cave getPlayerPostion(){
        return model.getPlayerCave();
    }

    @Override
    public boolean cheatsEnabled() {
        return model.cheatsEnabled();
    }

    public void moveToCave(int id) {
       Cave playerCave = model.getPlayerCave();
        if (playerCave.getID() == id) {
              return; 
        }
        if(id < 0 || id >= model.getmap().getCaves().size()) {
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
        if (id < 0 || id >= model.getmap().getCaves().size()) {
            return;
        }
        if (playerCave.getEdgesToVertexId().contains(id)) {
            model.shootArrow(id);
        }
        validateGameState();
    }

    @Override
    public void setCheatsEnabled() {
         model.setCheats();
    }


    @Override
    public void setModel(WumpusGameModelnterface model) {
        this.model = model;
    }
    @Override
    public void startGame(){
        gameWindow.remove((Component) this.view);
        this.model = new WumpusGameModel();
        this.view = new WumpusGameView(this);
        gameWindow.add((Component) this.view, BorderLayout.CENTER);
        gameWindow.revalidate();
    }
    @Override
    public void gameOver(){
        gameWindow.remove((Component) this.view);
        this.view = new WumpusGameEndView(this);
        gameWindow.add((Component) this.view, BorderLayout.CENTER);
        gameWindow.revalidate();
    }
    @Override
    public WumpusGameModelnterface getModel() {
        return model;
    }
    @Override
    public void validateGameState() {
        System.out.println("Validating game state...");
        if(!model.wumpusIsAlive()) {
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

}
