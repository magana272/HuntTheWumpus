package Model;

public interface NodeInterface {

    private void initializeVertices(){
        return;
    }
    private void initializeEdges(){
        return;
    }
    boolean setPlayer(boolean set);
    boolean setWumpus(boolean set);
    boolean setPit(boolean set);
    boolean setBat(boolean set);
    boolean setVisited(boolean set);
    public boolean containsPlayer();
    public boolean containsWumpus();
    public boolean containsPit();
    public boolean containsBat();
    public boolean isVisited();
    
} 