package hungerGames;

import java.util.Random;

public final class Game {
    int round;

    public Game() {
    }

    public Game(int round) {
        this.round = round;
    }

    public Game(Game game) {
        this.round = game.round;
    }

    public static void main(String[] args) {

        System.out.println("May the odds be ever in your favor!");
        Game g = new Game();
        g.setRound(1);
        Board board = new Board(20, 20, 6, 10, 8);
        board.createBoard();

        Player p1 = new Player(1, "Yiannis", board, 0, -10, -10, null, null, null);
        Player p2 = new Player(2, "Stavros", board, 0, 10, 10, null, null, null);

        Random r = new Random();
        int begins = r.nextInt(6) + 1; // Dice roll

        if (begins % 2 == 0) {
            play(p1, p2, board, g);
        } else {
            play(p2, p1, board, g);
        }

        System.out.print("The hunger games are over! ");
        if (p1.getScore() > p2.getScore()) {
            System.out.println(p1.getName() + " is the 2020 winner! With " + p1.getScore() + " points VS " + p2.getScore()
                    + " points.");
        } else if (p2.getScore() > p1.getScore()) {
            System.out.println(p2.getName() + " is the 2020 winner! With " + p2.getScore() + " points VS " + p1.getScore()
                    + " points.");
        }
    }

    static void play(Player p1, Player p2, Board board, Game g) {
        int count = 1;
        System.out.println(p1.getName() + " plays first!");
        do {
            g.setRound(count);
            System.out.println("Round " + g.getRound());
            p1.move();
            p2.move();
            System.out.println(p1.getName() + " is now at: (" + p1.getX() + "," + p1.getY() + ")");
            System.out.println(p2.getName() + " is now at: (" + p2.getX() + "," + p2.getY() + ")");
            count++;
            if (g.getRound() % 3 == 0)
                board.resizeBoard(p1, p2);
            for (String[] i : board.getStringRepresentation()) {
                for (String j : i) {
                    System.out.print(j);
                }
                System.out.println();
            }
            System.out.println("--------------------------------------------------------------");
        } while (board.getR() > 2);
    }

    public int getRound() {
        return this.round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
