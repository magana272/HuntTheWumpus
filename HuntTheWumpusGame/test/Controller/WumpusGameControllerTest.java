package Controller;

import Model.GameBoard.Cave;
import Model.WumpusGameModel;
import Model.WumpusGameModelnterface;
import View.WumpusGameStartView;
import View.WumpusViewInterface;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class WumpusGameControllerTest extends TestCase {
    
    private WumpusGameController controller;
    private WumpusGameModel testModel;
    private WumpusViewInterface testView;
    
    @Before
    public void setUp() {
        testModel = new WumpusGameModel();
        controller = new WumpusGameController();
        controller.setModel(testModel);
        testView = new WumpusGameStartView(controller);
    }

    @Test
    public void testStartScreen() {
        controller.startScreen();
        // Verify view was set up for start screen
        assertTrue("Controller should be set on view", testView.getController()!=null);
    }

    @Test
    public void testStartGame() {
        controller.startGame();
        assertTrue("Controller should be set on view", testView.getController()!=null);

    }

    @Test
    public void testGameOver() {
        controller.gameOver();
        assertTrue("Controller should be set on view", testView.getController()!=null);

    }

    @Test
    public void testGetMessage() {
        String message = controller.getMessage();
        assertTrue( message.contains("You are in cave"));
    }

    @Test
    public void testGetModel() {
        WumpusGameModelnterface result = controller.getModel();
        assertSame(testModel, result);
    }
    @Test
    public void testGetPlayerPosition() {
        Cave result = controller.getPlayerPostion();
        assertTrue(result.containsPlayer());
    }

    @Test
    public void testMoveToCave() {
        Cave cave = controller.getPlayerPostion();
        controller.getModel().movePlayer(cave.getID()+1);
        assertTrue(controller.getPlayerPostion().getID() == cave.getID() +1);
    }

    @Test
    public void testCheatsEnabled() {
        boolean result= controller.cheatsEnabled();
        assertTrue(!result);
    }
    @Test
    public void testSetCheatsEnabled() {
        controller.setCheatsEnabled();
        boolean result= controller.cheatsEnabled();
        assertTrue(result);
    }
}