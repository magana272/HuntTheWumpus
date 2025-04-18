
package Model.GameBoard;

import java.util.ArrayList;

public class Vertex {
    private final int id;

    private final int X;
    private final int Y;

    private ArrayList<Integer> edgesToVertexId;

    public Vertex(int id, int x, int y) {
        this.id = id;
        this.X = x;
        this.Y = y;
    }
   
    public int getX() {
        return this.X;
    }
    public int getY() {
        return this.Y;
    }
    public int getID() {
        return this.id;
    }
    public ArrayList<Integer> getEdgesToVertexId() {
        return edgesToVertexId;
    }
    public void setEdgesToVertexId(ArrayList<Integer> edgesToVertexId) {
        this.edgesToVertexId = edgesToVertexId;
    }


 
}
