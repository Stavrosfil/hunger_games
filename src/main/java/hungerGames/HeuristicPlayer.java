package hungerGames;

import java.util.ArrayList;
import java.lang.Math.*;

public class HeuristicPlayer extends Player {

    ArrayList<Integer[]> path;
    static int r;

    public HeuristicPlayer() {

    }

    float playersDistance(Player p) {

        return 0; // oxi 0
    }

    double evaluate(int dice, Player p) {
        double evaluation = 0;
        int moves[][] = { { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, };

        int radius = board.getR();

        int counter = 0;
        int newX = this.x;
        int newY = this.y;
        int die = dice;
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

        int weaponsGained = 0;
        int pointsGained = 0;
        int pointsLost = 0;

        // Check weapons
        for (Weapon w : board.getWeapons()) {
            int[] coords = new int[] { w.getX(), w.getY() };
            if (newX == coords[0] && newY == coords[1]) {
                if (this.id == w.getPlayerId()) {
                    switch (w.getType()) {
                    case "pistol":
                        weaponsGained++;
                    case "bow":
                        weaponsGained++;
                    case "sword":
                        weaponsGained++;
                    }
                }
            }
        }

        // Check food
        for (Food f : board.getFood()) {
            int[] coords = new int[] { f.getX(), f.getY() };
            if (newX == coords[0] && newY == coords[1]) {
                pointsGained += f.getPoints();
            }
        }

        // Check traps
        for (Trap t : board.getTraps()) {
            int[] coords = new int[] { t.getX(), t.getY() };
            if (newX == coords[0] && newY == coords[1]) {
                if (t.getType() == "rope") {
                    if (sword == null) {
                        pointsLost += t.getPoints();
                    }
                }
                if (t.getType() == "animals") {
                    if (bow == null) {
                        pointsLost += t.getPoints();
                    }
                }
            }
        }

        int forceKill = 0;
        if (playersDistance(p) < r && this.pistol != null) {
            forceKill = 10000;
        }
        evaluation = weaponsGained * 0.35 + pointsGained * 0.2 + pointsLost * 0.15 + forceKill * 0.3;
        return evaluation;
    }

    boolean kill(Player player1, Player player2, float d) {
        boolean dead = false;
        if (player1.id == this.id) {
            if (playersDistance(player2) < d && this.pistol != null) {
                System.out.println(player2.name + " got killed by " + this.name);
                dead = true;
            }
        } else {
            if (playersDistance(player1) < d && this.pistol != null) {
                System.out.println(player1.name + " got killed by " + this.name);
                dead = true;
            }
        }

        return dead;
    }
}