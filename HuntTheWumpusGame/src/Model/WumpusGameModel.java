package Model;

import java.util.ArrayList;
import java.util.List;

import Model.GameBoard.Cave;
import Model.GameBoard.Dodecahedron;
import Model.GameBoard.Map;
import View.ModelListener;

public class WumpusGameModel implements GamePublisher, WumpusGameModelnterface{
    private Map map = new Dodecahedron();
    private Cave playerPostion;
    int wumpusVertexId;
    private Cave wumpusVertex;
    private final int MAX_PITS;
    private final int MAX_BATS;
    private final int MAX_WUMPUS; 
    private final Cave[] pitCaves;
    private final Cave[] batCaves;
    private int arrowCount = 3;
    private boolean WumpusAlive = true;
    private boolean gameOver = false;
    private boolean cheatsEnabled;


    private String gameMessage = "";
    private List<ModelListener> listeners = new ArrayList<>();
    
    public WumpusGameModel() {
        MAX_BATS = 2;
        MAX_PITS = 2;
        MAX_WUMPUS = 1;
        pitCaves = new Cave[MAX_PITS];
        batCaves = new Cave[MAX_BATS];
        initializeBoard();
        placePlayer();
        placePits();
        placeBats();
        placeWumpus();
    }
    public WumpusGameModel(int numPits, int numBats, int numWumpus) {
        this.map = new Dodecahedron();
        this.MAX_PITS = numPits;
        this.MAX_BATS = numBats;
        this.MAX_WUMPUS = numWumpus;
        this.pitCaves = new Cave[MAX_PITS];
        this.batCaves = new Cave[MAX_BATS];
        this.cheatsEnabled = true;
        initializeBoard();
        placePlayer();
        placePits();
        placeBats();
        placeWumpus();
    }

    private void initializeBoard() {
        map = new Dodecahedron();
    }
    private void placeWumpus() {
        for (int i = 0; i < MAX_WUMPUS; i++) {
            wumpusVertexId = (int) (Math.random() * map.getCaves().size());
            boolean set = map.getCaves().get(wumpusVertexId).setWumpus(true);
            if (set) {
                wumpusVertex = map.getCaves().get(wumpusVertexId);
            } else {
                i--;
            }
        }
        

    }
    private void placePlayer() {
        int playerVertexId = (int) (Math.random() * map.getCaves().size());
        boolean set = map.getCaves().get(playerVertexId).setPlayer(true);
        if (!set) {
            placePlayer();
        }
        this.playerPostion = map.getCaves().get(playerVertexId);

    }
    private void placePits() {
        for (int i = 0; i < MAX_PITS; i++) {
            int pitVertexId = (int) (Math.random() * map.getCaves().size());
            boolean set = map.getCaves().get(pitVertexId).setPit(true);
            if (set) {
                pitCaves[i] = map.getCaves().get(pitVertexId);
            } else {
                i--;
            }
        }
    }
    public void placeBats() {
        for (int i = 0; i < MAX_BATS; i++) {
            int batVertexId = (int) (Math.random() * map.getCaves().size());
            boolean set = map.getCaves().get(batVertexId).setBat(true);
            if (set) {
                batCaves[i] = map.getCaves().get(batVertexId);
            } else {
                i--;
            }
        }
    }

    @Override
    public Map getmap() {
        return map;
    }


    @Override
    public void movePlayer(int caveId) {
        if (caveId >= 0 && caveId < map.getCaves().size()) {
            playerPostion.setPlayer(false);
            map.getCaves().get(caveId).setPlayer(true);
            playerPostion = map.getCaves().get(caveId);

            if(playerPostion.containsBat() && playerPostion.containsPit()){
                int randomChance = (int) (Math.random() );
                if(randomChance < .5){
                    playerPostion.setBat(false);
                    int newCaveId = (int) (Math.random() * map.getCaves().size());
                    playerPostion.setPlayer(false);
                    map.getCaves().get(newCaveId).setPlayer(true);
                    playerPostion = map.getCaves().get(newCaveId);
                    notifyObservers();
                    movePlayer(newCaveId);
                }
                else{
                    gameOver = true;
                }


            }
            else if(playerPostion.containsBat()) {
                int randomChance = (int) (Math.random() );
                if(randomChance < .5){
                    System.out.println("You have been moved by a bat!");
                    playerPostion.setBat(false);
                    int newCaveId = (int) (Math.random() * map.getCaves().size());
                    playerPostion.setPlayer(false);
                    map.getCaves().get(newCaveId).setPlayer(true);
                    playerPostion = map.getCaves().get(newCaveId);
                    notifyObservers();
                    movePlayer(newCaveId);
                }
                else{
                    System.out.println("You are in a cave with a bat.");
                }
            }
            else if (playerPostion.containsWumpus()) {
                System.out.println("You have been eaten by the Wumpus!");
                gameOver = true;
            } else if (playerPostion.containsPit()) {
                System.out.println("You have fallen into a pit!");
                gameOver = true;
            } else {
                System.out.println("You moved to cave " + caveId + ".");
            }
        } else {
            System.out.println("Invalid cave ID.");
        }
        notifyObservers();
    }

    @Override
    public void shootArrow(int caveId) {
        if (arrowCount > 0) {
            arrowCount--;
            notifyObservers();
            if (wumpusVertex.getID() == caveId) {
                this.WumpusAlive = false;
                System.out.println("Wumpus hit!");
                this.gameOver = true;
            } else {
                System.out.println("Arrow missed.");
                if(arrowCount==0){
                    this.gameOver = true;
                }
            }
        } else {
            this.gameOver = true;
        }
        notifyObservers();
    }
    @Override
    public int getArrowCount() {
        return arrowCount;
    }
    @Override
    public Cave getPlayerCave() {
        return playerPostion;
    }
    @Override
    public String getMessage(){
        ArrayList<Integer> cavesConnectedToPlayer = playerPostion.getEdgesToVertexId();
        StringBuilder message = new StringBuilder("You are in cave " + playerPostion.getID() + ".\n");

        if(!WumpusAlive){
            message = new StringBuilder("You have killed the Wumpus!\n");
            gameMessage = message.toString();
            return message.toString();
        }
        if(playerPostion.containsBat()){
            message = new StringBuilder("You are in cave " + playerPostion.getID() + ".\n");
            gameMessage = message.toString();
            return message.toString();
        }
        else if(playerPostion.containsWumpus()){
            message.append("You were eaten by the Wumpus\n");
            gameMessage = message.toString();
            return message.toString();
        }
        else if(playerPostion.containsPit()){
            message.append("You fell into a bottomless pit\n");
            gameMessage = message.toString();
            return message.toString();
        }
        else{
            for (int cID : cavesConnectedToPlayer){
                Cave c = map.getCaves().get(cID);
                if(c.containsBat()){
                    message.append("You hear a bat nearby.\n");
                }
                if(c.containsWumpus()){
                    message.append("You smell a wumpus nearby.\n");
                }
                if(c.containsPit()){
                    message.append("You feel a draft nearby.\n");
                }
            }
            return message.toString();
        }
        
    }
    public void validateGameState(){
        if(playerPostion.containsWumpus()){
            System.out.println("You have been eaten by the Wumpus!");
            gameOver = true;
        } else if(playerPostion.containsPit()){
            System.out.println("You have fallen into a pit!");
            gameOver = true;
        } else if(arrowCount == 0){
            System.out.println("You have run out of arrows!");
            gameOver = true;
        }
    }
    @Override
    public boolean wumpusIsAlive() {
        return WumpusAlive;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }
    @Override
    public boolean cheatsEnabled() {
        return cheatsEnabled;
    }
    @Override
    public void setCheats(){
        this.cheatsEnabled = !this.cheatsEnabled;
        notifyObservers();
    }
    @Override
    public void registerObserver(ModelListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    @Override
    public void removeObserver(ModelListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    @Override
    public void notifyObservers() {
        for (ModelListener listener : listeners) {
            listener.update();
        }
    }


}
