package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WumpusGameModel {
    private Dodecahedron dodecahedron = new Dodecahedron();
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
        this.dodecahedron = new Dodecahedron();
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
    
    public Dodecahedron getDodecahedron() {
        return dodecahedron;
    }
    private void initializeBoard() {
        dodecahedron = new Dodecahedron();
    }
    private void placeWumpus() {
        for (int i = 0; i < MAX_WUMPUS; i++) {
            wumpusVertexId = (int) (Math.random() * dodecahedron.getCaves().size());
            boolean set = dodecahedron.getCaves().get(wumpusVertexId).setWumpus(true);
            if (set) {
                wumpusVertex = dodecahedron.getCaves().get(wumpusVertexId);
            } else {
                i--;
            }
        }
        

    }
    private void placePlayer() {
        int playerVertexId = (int) (Math.random() * dodecahedron.getCaves().size());
        boolean set = dodecahedron.getCaves().get(playerVertexId).setPlayer(true);
        if (!set) {
            placePlayer();
        }
        this.playerPostion = dodecahedron.getCaves().get(playerVertexId);

    }
    private void placePits() {
        for (int i = 0; i < MAX_PITS; i++) {
            int pitVertexId = (int) (Math.random() * dodecahedron.getCaves().size());
            boolean set = dodecahedron.getCaves().get(pitVertexId).setPit(true);
            if (set) {
                pitCaves[i] = dodecahedron.getCaves().get(pitVertexId);
            } else {
                i--;
            }
        }
    }
    public void placeBats() {
        for (int i = 0; i < MAX_BATS; i++) {
            int batVertexId = (int) (Math.random() * dodecahedron.getCaves().size());
            boolean set = dodecahedron.getCaves().get(batVertexId).setBat(true);
            if (set) {
                batCaves[i] = dodecahedron.getCaves().get(batVertexId);
            } else {
                i--;
            }
        }
    }
    public void movePlayer(int caveId) {
        if (caveId >= 0 && caveId < dodecahedron.getCaves().size()) {
            playerPostion.setPlayer(false);
            dodecahedron.getCaves().get(caveId).setPlayer(true);
            playerPostion = dodecahedron.getCaves().get(caveId);

            if(playerPostion.containsBat() && playerPostion.containsPit()){
                int randomChance = (int) (Math.random() );
                if(randomChance < .5){
                    playerPostion.setBat(false);
                    int newCaveId = (int) (Math.random() * dodecahedron.getCaves().size());
                    playerPostion.setPlayer(false);
                    dodecahedron.getCaves().get(newCaveId).setPlayer(true);
                    playerPostion = dodecahedron.getCaves().get(newCaveId);
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
                    int newCaveId = (int) (Math.random() * dodecahedron.getCaves().size());
                    playerPostion.setPlayer(false);
                    dodecahedron.getCaves().get(newCaveId).setPlayer(true);
                    playerPostion = dodecahedron.getCaves().get(newCaveId);
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
    }
    public void shootArrow(int caveId) {
        if (arrowCount > 0) {
            arrowCount--;
            if (wumpusVertex.getID() == caveId) {
                this.WumpusAlive = false;
                System.out.println("Wumpus hit!");
                this.gameOver = true;
            } else {
                System.out.println("Arrow missed.");
            }
        } else {
            this.gameOver = true;
        }
    }
    public int getArrowCount() {
        return arrowCount;
    }
    public Cave getPlayerCave() {
        return playerPostion;
    }
    public String getMessage(){
        ArrayList<Integer> cavesConnectedToPlayer = playerPostion.getEdgesToVertexId();
        StringBuilder message = new StringBuilder("You are in cave " + playerPostion.getID() + ".\n");
        if(!WumpusAlive){
            message = new StringBuilder("You have killed the Wumpus!\n");
            return message.toString();
        }
        if(playerPostion.containsBat()){
            message = new StringBuilder("You are in cave " + playerPostion.getID() + ".\n");
            return message.toString();
        }
        else if(playerPostion.containsWumpus()){
            message.append("You were eaten by the Wumpus\n");
            return message.toString();
        }
        else if(playerPostion.containsPit()){
            message.append("You fell into a bottomless pit\n");
            return message.toString();
        }
        else{
            for (int cID : cavesConnectedToPlayer){
                Cave c = dodecahedron.getCaves().get(cID);
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

    public boolean wumpusIsAlive() {
        return WumpusAlive;
    }
    public boolean isGameOver() {
        return gameOver;
    }

    public boolean cheatsEnabled() {
        return cheatsEnabled;
    }
    public void setCheats(){
        this.cheatsEnabled = !this.cheatsEnabled;
    }


}
