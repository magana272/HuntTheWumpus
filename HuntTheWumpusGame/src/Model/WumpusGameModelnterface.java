package Model;

import Model.GameBoard.Cave;
import Model.GameBoard.Map;

public interface WumpusGameModelnterface extends GamePublisher{

    void movePlayer(int caveId);
    void shootArrow(int caveId);
    int getArrowCount();
    String getMessage();
    boolean wumpusIsAlive();
    boolean isGameOver();
    boolean cheatsEnabled();
    void setCheats();
    Cave getPlayerCave();
    Map getmap();
}
