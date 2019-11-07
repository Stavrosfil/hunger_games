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

}