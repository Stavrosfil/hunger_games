package hungerGames;

//import java.util.Random;

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

    // int[2] getRandomMove() {

    // for(int i = 1; i <= 8; i++) {

    // }

    // return null;
    // }

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