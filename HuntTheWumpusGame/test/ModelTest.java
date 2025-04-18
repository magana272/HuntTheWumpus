import Model.GameBoard.Cave;
import Model.GameBoard.Dodecahedron;
import Model.GameBoard.Map;
import Model.WumpusGameModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModelTest {

    private WumpusGameModel model;
    private WumpusGameModel testModel;

    @Before
    public void setUp() {
        this.model = new WumpusGameModel();
        this.testModel = new WumpusGameModel(1, 1, 1);
        this.testModel.setCheats();
    }

    @Test
    public void testDodecahedronInitialization() {
        Map dodecahedron = model.getmap();
        assertNotNull("Dodecahedron should be initialized", dodecahedron);
        assertEquals("Dodecahedron should have 20 caves", 20, dodecahedron.getCaves().size());
        for (Cave cave : dodecahedron.getCaves()) {
            assertEquals("Each cave should have 3 connections", 3, cave.getEdgesToVertexId().size());
        }
    }
    
    @Test
    public void testPlayerInitialization() {
        Cave playerCave = model.getPlayerCave();
        assertNotNull("Player position should be initialized", playerCave);
        assertTrue("Player cave ID should be valid (0-19)", playerCave.getID() >= 0 && playerCave.getID() <= 19);
    }
    
    @Test
    public void testArrowCount() {
        assertEquals("Player should start with 3 arrows", 3, model.getArrowCount());
        Cave playerCave = testModel.getPlayerCave();
        int targetCaveId = -1;
        for (Integer edgeId : playerCave.getEdgesToVertexId()) {
            Cave adjacentCave = testModel.getmap().getCaves().get(edgeId);
            if (!adjacentCave.containsWumpus()) {
                targetCaveId = adjacentCave.getID();
                break;
            }
        }
        
        if (targetCaveId != -1) {
            testModel.shootArrow(targetCaveId);
            assertEquals("Arrow count should decrease after shooting", 2, testModel.getArrowCount());
        }
    }
    
    @Test
    public void testPitPlacement() {
        int pitCount = 0;
        for (Cave cave : testModel.getmap().getCaves()) {
            if (cave.containsPit()) {
                pitCount++;
            }
        }
        assertEquals("Test model should have exactly 1 pit", 1, pitCount);
    }
    
    @Test
    public void testBatPlacement() {
        int batCount = 0;
        for (Cave cave : testModel.getmap().getCaves()) {
            if (cave.containsBat()) {
                batCount++;
            }
        }
        assertEquals("Test model should have exactly 1 bat", 1, batCount);
    }
    
    @Test
    public void testWumpusPlacement() {
        int wumpusCount = 0;
        for (Cave cave : testModel.getmap().getCaves()) {
            if (cave.containsWumpus()) {
                wumpusCount++;
            }
        }
        assertEquals("Test model should have exactly 1 wumpus", 1, wumpusCount);
    }
    
    @Test
    public void testMovePlayer() {
        Cave initialPosition = testModel.getPlayerCave();
        int initialId = initialPosition.getID();
        Integer targetCaveId = initialPosition.getEdgesToVertexId().get(0);
        testModel.movePlayer(targetCaveId);
        
        Cave newPosition = testModel.getPlayerCave();
        assertNotEquals("Player position should change after move", initialId, newPosition.getID());
        assertEquals("Player should be at the target cave", targetCaveId.intValue(), newPosition.getID());
    }
    
    @Test
    public void testGameOverWhenArrowsDeplete() {
        Cave playerCave = testModel.getPlayerCave();
        Integer targetCaveId = playerCave.getEdgesToVertexId().get(0);
        for (int i = 0; i < 3; i++) {
            testModel.shootArrow(targetCaveId);
        }
        assertEquals("Arrow count should be 0", 0, testModel.getArrowCount());
        assertTrue("Game should be over when arrows are depleted", testModel.isGameOver());
    }
    
    @Test
    public void testWumpusKilled() {
        Cave wumpusCave = null;
        for (Cave cave : testModel.getmap().getCaves()) {
            if (cave.containsWumpus()) {
                wumpusCave = cave;
                break;
            }
        }
        
        if (wumpusCave != null) {
            testModel.shootArrow(wumpusCave.getID());
            assertFalse("Wumpus should be dead after shooting it", testModel.wumpusIsAlive());
            assertTrue("Game should be over after killing the wumpus", testModel.isGameOver());
        }
    }
    
    @Test
    public void testFallIntoPit() {
        // Find a pit
        Cave pitCave = null;
        for (Cave cave : testModel.getmap().getCaves()) {
            if (cave.containsPit()) {
                pitCave = cave;
                break;
            }
        }
        
        if (pitCave != null) {
            testModel.movePlayer(pitCave.getID());
            assertTrue("Game should be over after falling into pit", testModel.isGameOver());
        }
    }
}
