package hungerGames;

import java.util.Random;

public final class Game {
    int round;
    static int killed = 0;

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

        HeuristicPlayer p1 = new HeuristicPlayer(1, "Yiannis", board, 0, -10, -10, null, null, null, 3);
        Player p2 = new Player(2, "Stavros", board, 0, 10, 10, null, null, null);

        play(p1, p2, board, g);

        System.out.print("The hunger games are over! ");
        if (killed == 1) {
            System.out.println(p1.getName() + " is the 2020 winner!");
        } else if (killed == 2) {
            System.out.println(p2.getName() + " is the 2020 winner!");
        } else {
            if (p1.getScore() > p2.getScore()) {
                System.out.println(p1.getName() + " is the 2020 winner! With " + p1.getScore() + " points VS "
                        + p2.getScore() + " points.");
            } else if (p2.getScore() > p1.getScore()) {
                System.out.println(p2.getName() + " is the 2020 winner! With " + p2.getScore() + " points VS "
                        + p1.getScore() + " points.");
            }
        }
    }

    static void play(HeuristicPlayer p1, Player p2, Board board, Game g) {
        int count = 1;
        killed = 0;
        System.out.println(p1.getName() + " plays first!");
        do {
            g.setRound(count);
            System.out.println("Round " + g.getRound());
            p1.move(p2);
            System.out.println(p1.getName() + " is now at: (" + p1.getX() + "," + p1.getY() + ")");
            p1.statistics();
            if (p1.kill(p1, p2, 2)) {
                System.out.println(p1.getName() + " killed " + p2.getName());
                killed = 1;
            }
            if (killed == 0) {
                p2.move();
                System.out.println(p2.getName() + " is now at: (" + p2.getX() + "," + p2.getY() + ")");
                if (p1.kill(p2, p1, 2)) {
                    System.out.println(p2.getName() + " killed " + p1.getName());
                    killed = 2;
                }
            }
            for (String[] i : board.getStringRepresentation()) {
                for (String j : i) {
                    System.out.print(j);
                }
                System.out.println();
            }
            System.out.println("--------------------------------------------------------------");
            if (killed == 0) {
                if (g.getRound() % 3 == 0)
                    board.resizeBoard(p1, p2);
                count++;
            } else {
                break;
            }
        } while (board.getR() > 2);
    }

    public int getRound() {
        return this.round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
