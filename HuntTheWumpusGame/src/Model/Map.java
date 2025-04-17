package Model;

import java.util.List;

public interface Map {
    public void initializeCaves();
    public List<Cave> getCaves();
    public List<Edge> getEdges();
    public void setCaves(List<Cave> caves);
    public void setEdges(List<Edge> edges);
    
}