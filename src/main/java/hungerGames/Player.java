package hungerGames;

import java.util.Random;

public class Player {

    int id;
    String name;
    Board board;
    int score;
    int x;
    int y;
    Weapon bow;
    Weapon pistol;
    Weapon sword;

    public Player() {

    }

    public Player(int id, String n, Board b, int s, int x, int y, Weapon bow, Weapon pistol, Weapon sword) {
        this.id = id;
        this.name = n;
        this.board = b;
        this.score = s;
        this.x = x;
        this.y = y;
        this.bow = bow;
        this.pistol = pistol;
        this.sword = sword;
    }

    public Player(Player player) {
        this.id = player.id;
        this.name = player.name;
        this.board = player.board;
        this.score = player.score;
        this.x = player.x;
        this.y = player.y;
        this.bow = player.bow;
        this.pistol = player.pistol;
        this.sword = player.sword;
    }

    public int[] getRandomMove() {
        Random random = new Random();

        // Vector array of all the available moves
        int moves[][] = { { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, };

        int radius = board.getR();

        int possibleMoves = 8;
        boolean isXEdge = x == -radius || x == radius;
        boolean isYEdge = y == -radius || y == radius;
        if (isXEdge && isYEdge) {
            possibleMoves = 3;
        } else if (isXEdge || isYEdge) {
            possibleMoves = 5;
        }

        int counter = 0;
        int newX = this.x;
        int newY = this.y;
        int die = random.nextInt(possibleMoves) + 1;
        for (int i = 0; i < 8; i++) {
            newX = this.x + moves[i][0];
            newY = this.y + moves[i][1];

            if (newX == 0)
                newX += moves[i][0];
            if (newY == 0)
                newY += moves[i][1];
            if (newX >= -radius && newX <= radius && newY >= -radius && newY <= radius)
                counter++;
            if (counter == die)
                break;
        }

        return new int[] { newX, newY };
    }

    int[] move() {
        int[] coordinates;
        coordinates = getRandomMove();
        int posX = coordinates[0];
        int posY = coordinates[1];
        this.x = posX;
        this.y = posY;
        int[] info = new int[5];
        info[0] = posX;
        info[1] = posY;

        // Weapons area
        int numOfWeapons = 0;
        for (Weapon w : board.getWeapons()) {
            int[] coords = new int[] { w.getX(), w.getY() };
            if (coordinates[0] == coords[0] && coordinates[1] == coords[1]) {
                if (this.id == w.getPlayerId()) {
                    System.out.println("You picked a weapon!");
                    switch (w.getType()) {
                    case "pistol":
                        this.pistol = w;
                        w.setX(0);
                        w.setY(0);
                        numOfWeapons++;
                        break;
                    case "bow":
                        this.bow = w;
                        w.setX(0);
                        w.setY(0);
                        numOfWeapons++;
                        break;
                    case "sword":
                        this.sword = w;
                        w.setX(0);
                        w.setY(0);
                        numOfWeapons++;
                        break;
                    default:
                        break;
                    }
                }
            }
        }
        info[2] = numOfWeapons;

        // Food area
        int numOfFoods = 0;
        for (Food f : board.getFood()) {
            int[] coords = new int[] { f.getX(), f.getY() };
            if (coordinates[0] == coords[0] && coordinates[1] == coords[1]) {
                System.out.println("You got some food!");
                this.score += f.getPoints();
                f.setX(0);
                f.setY(0);
                numOfFoods++;
            }
        }
        info[3] = numOfFoods;

        // Trap area
        int numOfTraps = 0;
        for (Trap t : board.getTraps()) {
            int[] coords = new int[] { t.getX(), t.getY() };
            if (coordinates[0] == coords[0] && coordinates[1] == coords[1]) {
                numOfTraps++;
                if (t.getType() == "rope") {
                    if (sword != null) {
                        System.out.println("Congrats you cut the rope!");
                    } else if (sword == null) {
                        System.out.println("Oh no you got trapped in a rope and you dont have a sword...");
                        this.score += t.getPoints();
                    }
                }
                if (t.getType() == "animals") {
                    if (bow != null) {
                        System.out.println("Congrats you killed the animal!");
                    } else if (bow == null) {
                        System.out.println("Oh no you don't have a bow and now this animal will attack you...");
                        this.score += t.getPoints();
                    }
                }
            }
        }
        info[4] = numOfTraps;

        return info;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public Weapon getBow() {
        return this.bow;
    }

    public void setBow(Weapon bow) {
        this.bow = bow;
    }

    public Weapon getPistol() {
        return this.pistol;
    }

    public void setPistol(Weapon pistol) {
        this.pistol = pistol;
    }

    public Weapon getSword() {
        return this.sword;
    }

    public void setSword(Weapon sword) {
        this.sword = sword;
    }

}