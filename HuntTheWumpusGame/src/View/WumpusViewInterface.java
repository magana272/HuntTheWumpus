package View;

import Controller.WumpusGameController;
import Controller.WumpusGameControllerInterface;

public interface WumpusViewInterface extends ModelListener {
    void setVisible(boolean visible);
    void setController(WumpusGameControllerInterface controller);
    WumpusGameControllerInterface getController();
    public void setUp();
}