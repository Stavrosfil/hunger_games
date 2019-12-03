package hungerGames;

class Food {

    int id;
    int x;
    int y;
    int points;

    public Food() {

    }

    public Food(int id, int x, int y, int points) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.points = points;
    }

    public Food(Food food) {
        this.id = food.id;
        this.x = food.x;
        this.y = food.y;
        this.points = food.points;
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

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}