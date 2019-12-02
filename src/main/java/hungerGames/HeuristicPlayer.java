package hungerGames;

import java.util.ArrayList;
import java.lang.Math.*;
import java.util.*;
import java.util.Queue;

public class HeuristicPlayer extends Player {

    ArrayList<Integer[]> path;
    static int r;
    int R = 10;
    char[][] matrix = new char[2 * R][2 * R];
    boolean visited[][] = new boolean[2 * R][2 * R];

    public HeuristicPlayer(int id, String n, Board b, int s, int x, int y, Weapon bow, Weapon pistol, Weapon sword) {
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

    void translateCoordinates(int[] coords) {
        for (int i = 0; i < 2; i++) {
            if (coords[i] < 0)
                coords[i] += R;
            else
                coords[i] += R - 1;
        }
    }

    void createMatrix() {

        // Initialize arrays for bfs
        for (char[] i : matrix)
            Arrays.fill(i, '1');

        for (boolean[] i : visited)
            Arrays.fill(i, false);

        for (Trap t : this.board.getTraps()) {
            int[] coords = new int[] { t.getX(), t.getY() };
            translateCoordinates(coords);
            matrix[coords[1]][coords[0]] = '0';
        }
        for (Food f : this.board.getFood()) {
            int[] coords = new int[] { f.getX(), f.getY() };
            translateCoordinates(coords);
            matrix[coords[1]][coords[0]] = '0';
        }
        for (Weapon w : this.board.getWeapons()) {
            int[] coords = new int[] { w.getX(), w.getY() };
            translateCoordinates(coords);
            matrix[coords[1]][coords[0]] = '0';
        }
    }

    boolean isLegit(int x, int y) {
        if (x >= 0 && x < this.R * 2 && y >= 0 && y < this.R * 2)
            return true;
        return false;
    }

    int playersDistance(Player p2) {
        Queue<int[]> q = new LinkedList<>();
        int[] myCoords = new int[] { this.x, this.y, 0 };
        translateCoordinates(myCoords);

        q.add(myCoords);
        visited[myCoords[0]][myCoords[1]] = true;

        int[] p2Coords = new int[] { p2.getX(), p2.getY() };
        translateCoordinates(p2Coords);
        matrix[p2Coords[0]][p2Coords[1]] = 'd';

        while (!q.isEmpty()) {
            int[] p = q.poll();

            // Destination found;
            if (matrix[p[0]][p[1]] == 'd')
                return p[2];

            // moving left
            if (isLegit(p[0] - 1, p[1]) && visited[p[0] - 1][p[1]] == false) {
                q.add(new int[] { p[0] - 1, p[1], p[2] + 1 });
                visited[p[0] - 1][p[1]] = true;
            }

            // moving right
            if (isLegit(p[0] + 1, p[1]) && visited[p[0] + 1][p[1]] == false) {
                q.add(new int[] { p[0] + 1, p[1], p[2] + 1 });
                visited[p[0] + 1][p[1]] = true;
            }

            // moving up
            if (isLegit(p[0], p[1] - 1) && visited[p[0]][p[1] - 1] == false) {
                q.add(new int[] { p[0], p[1] - 1, p[2] + 1 });
                visited[p[0]][p[1] - 1] = true;
            }

            // moving down
            if (isLegit(p[0], p[1] + 1) && visited[p[0]][p[1] + 1] == false) {
                q.add(new int[] { p[0], p[1] + 1, p[2] + 1 });
                visited[p[0]][p[1] + 1] = true;
            }

            // moving diagonally
            if (isLegit(p[0] + 1, p[1] + 1) && visited[p[0] + 1][p[1] + 1] == false) {
                q.add(new int[] { p[0] + 1, p[1] + 1, p[2] + 1 });
                visited[p[0] + 1][p[1] + 1] = true;
            }

            if (isLegit(p[0] - 1, p[1] + 1) && visited[p[0] - 1][p[1] + 1] == false) {
                q.add(new int[] { p[0] - 1, p[1] + 1, p[2] + 1 });
                visited[p[0] - 1][p[1] + 1] = true;
            }

            if (isLegit(p[0] + 1, p[1] - 1) && visited[p[0] + 1][p[1] - 1] == false) {
                q.add(new int[] { p[0] + 1, p[1] - 1, p[2] + 1 });
                visited[p[0] + 1][p[1] - 1] = true;
            }

            if (isLegit(p[0] - 1, p[1] - 1) && visited[p[0] - 1][p[1] - 1] == false) {
                q.add(new int[] { p[0] - 1, p[1] - 1, p[2] + 1 });
                visited[p[0] - 1][p[1] - 1] = true;
            }

        }

        return -1;
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