package hungerGames;

public class Trap {

    int id;
    int x;
    int y;
    String type;
    int points;

    public Trap() {

    }

    public Trap(int id, int x, int y, String type, int points) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
        this.points = points;
    }

    public Trap(Trap trap) {
        this.id = trap.id;
        this.x = trap.x;
        this.y = trap.y;
        this.type = trap.type;
        this.points = trap.points;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}