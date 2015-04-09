package model;

public class Tile {

    private Status status;
    private boolean marked;
    private int neighbourCount;

    public Tile(Status status) {
        this.status = status;
        this.marked = false;
        neighbourCount = 0;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isMarked() {
        return marked;
    }

    public int getNeighbourCount() {
        return neighbourCount;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public void incNeighbourCount() {
        this.neighbourCount++;
    }
}
