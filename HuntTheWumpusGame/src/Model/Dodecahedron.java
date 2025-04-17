package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Dodecahedron implements Map {
    private final List<Cave> caves = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();
    final int[][] coor = {
    {342, 25}, // Node 0
    {616, 215}, // Node 1, 
    {493, 536},  // Node 2,
    {173, 536}, // Node 3, 
    {66, 227}, // Node 4
    {140, 261}, // Node 5, 
    {237, 160}, // Node 6, 
    {330, 143},// Node 7, 
    {429, 160}, // Node 8, 
    {493, 263},// Node 9,
    {495, 369},  // Node 10, 
    {430, 450}, // Node 11, 
    {328, 487},  // Node 12, 
    {237, 455}, // Node 13, 
    {177, 366}, // Node 14,
    {259, 331},  // Node 15, 
    {279, 230}, // Node 16, 
    {392, 230}, // Node 17, 
    {423, 327},  // Node 18, 
    {339, 386}, // Node 19};
    };
    // Coordinates for the vertices of the dodecahedron
    // These coordinates are used to position the caves on the screen
    final int[][] edgesList = {
        {1, 4, 7},       // Node 0 
        {0, 2, 9},      // Node 1 
        {1, 3, 11},      // Node 2 
        {2, 4, 13},      // Node 3 
        {0, 3, 5},      // Node 4 
        {4, 6, 14},      // Node 5 
        {5, 7, 16},      // Node 6 
        {0, 6, 8},      // Node 7 
        {7, 9, 17},      // Node 8 
        {8, 1, 10},     // Node 9 
        {11, 9, 18},      // Node 10
        {12, 2, 10},     // Node 11
        {13, 11, 19},     // Node 12
        {3, 12, 14},     // Node 13
        {5, 13, 15},     // Node 14
        {14, 16, 19},     // Node 15
        {6, 15, 17},     // Node 16
        {8, 16, 18},     // Node 17
        {10, 17, 19},     // Node 18
        {12, 15, 18}     // Node 19
    };

    public Dodecahedron() {
        initializeCaves();
    }
    @Override
    public void initializeCaves(){ 
        // Initialize the caves with their coordinates
        for (int i = 0; i < 20; i++) {
            Cave cave = new Cave(i, coor[i][0], coor[i][1]);
            
            cave.edgesToVertexId = new ArrayList<>();
            for (int j = 0; j < edgesList[i].length; j++) {
                cave.edgesToVertexId.add(edgesList[i][j]);
            }

            caves.add(cave);
            
        }
    }
    @Override
    public List<Cave> getCaves() {
        return caves;
    }
    @Override
    public List<Edge> getEdges() {
        return edges;
    }
    @Override
    public void setCaves(List<Cave> caves) {
        this.caves.clear();
        this.caves.addAll(caves);
    }
    @Override
    public void setEdges(List<Edge> edges) {
        this.edges.clear();
        this.edges.addAll(edges);
    }

}
