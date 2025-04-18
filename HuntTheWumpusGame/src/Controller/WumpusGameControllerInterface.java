package Controller;

import Model.GameBoard.Cave;
import Model.WumpusGameModelnterface;

public abstract class WumpusGameControllerInterface {
    private WumpusGameModelnterface model;
    public void setModel(WumpusGameModelnterface model){
        this.model = model;
    }
    public WumpusGameModelnterface getModel(){
        return this.model;
    }
    public abstract void startGame();
    public abstract void gameOver();
    public abstract void validateGameState();
    public abstract Cave getPlayerPostion();
    public abstract boolean cheatsEnabled();
    public abstract void setCheatsEnabled();
    public abstract String getMessage();
    public abstract void startScreen();
    public abstract void moveToCave(int id);
    public abstract void shootArrow(int id);

}
