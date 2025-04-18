package Model.GameBoard;

public class Cave extends Vertex implements NodeInterface {
    private boolean player = false;
    private boolean wumpus = false;
    private boolean pit = false;
    private boolean bat = false;
    private boolean visited = false;

    public Cave(int id, int x, int y) {
        
        super(id, x, y);
    }
    private boolean checkValidity(){
        if(!pit && !wumpus && !bat && !player) {
            return true;
        } else {
            return false;
        }

    }
    private boolean checkBatValidity(){
        if(!wumpus && !player) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean setPlayer(boolean set) {
        if (set) {
            if (checkValidity()) {
                this.player = true;
                return true;
            } else {
                return false; 
            }
        } else {
            this.player = false;
            return true; 
        }

    }

    @Override
    public boolean setWumpus(boolean set) {
        if (set) {
            if (checkValidity()) {
                this.wumpus = true;
                return true;
            } else {
                return false; 
            }
        } else {
            this.wumpus = false;
            return true; 
        }
    }

    @Override
    public boolean setPit(boolean set) {
        if (set) {
            if (checkValidity()) {
                this.pit = true;
                return true;
            } else {
                return false; 
            }
        } else {
            this.pit = false;
            return true; 
        }
    }

    @Override
    public boolean setBat(boolean set) {
        if (set) {
            if (checkBatValidity()) {
                this.bat = true;
                return true;
            } else {
                return false; 
            }
        } else {
            this.bat = false;
            return true; 
        }
    }

    @Override
    public boolean setVisited(boolean set) {
        if (set) {
            this.visited = true;
            return true;
        } else {
            this.visited = false;
            return true; 
        }
    }

    @Override
    public boolean containsPlayer() {
        return this.player;
    }
    @Override
    public boolean containsWumpus() {
        return this.wumpus;
    }
    @Override
    public boolean containsPit() {
        return this.pit;
    }
    @Override
    public boolean containsBat() {
        return this.bat;
    }
    @Override
    public boolean isVisited() {
       return this.visited;
    }
    public boolean isSet() {
        return this.player || this.wumpus || this.pit || this.bat;
    }

   


}
