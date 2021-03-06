package hungerGames;

import java.util.ArrayList;
import java.lang.Math.*;
import java.util.*;
import java.util.Queue;
import java.util.Random;

public class HeuristicPlayer extends Player {

    ArrayList<Integer[]> path = new ArrayList<>();
    int r;
    // ! check this
    int R;
    char[][] matrix;
    boolean visited[][];
    int movesCount = 0;
    int newX, newY;

    void statistics() {
        Integer[] info = path.get(movesCount - 1);
        // System.out.println(toString(info[0]) + toString(info[1]) + toString(info[2])
        // + toString(info[3]));
        System.out.print(" he chose the die: " + info[0]);
        System.out.print(", he got " + info[1] + " points");
        System.out.print(", he got " + info[2] + " foods");
        System.out.print(", he got into " + info[3] + " traps");
        System.out.print(", he got " + info[4] + " weapons");

        System.out.print('\n');
    }

    int[] move(Player p) {
        movesCount += 1;
        int moves[][] = { { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, };

        Map<Integer, Double> movesEval = new HashMap<>();

        R = board.getR();

        int possibleMoves = 8;
        boolean isXEdge = this.x == -R || this.x == R;
        boolean isYEdge = this.y == -R || this.y == R;
        if (isXEdge && isYEdge) {
            possibleMoves = 3;
        } else if (isXEdge || isYEdge) {
            possibleMoves = 5;
        }

        int bestMove = 1;
        double bestEval = evaluate(bestMove, p);
        for (int die = 1; die <= possibleMoves; die++) {
            double evaluation = evaluate(die, p);
            movesEval.put(die, evaluation);
            if (evaluation > bestEval) {
                bestEval = evaluation;
                bestMove = die;
            }
        }

        if (bestEval == 0) {
            Random r = new Random();
            bestMove = r.nextInt(possibleMoves) + 1;
            bestEval = evaluate(bestMove, p);
        }

        int counter = 0;
        newX = this.x;
        newY = this.y;
        for (int i = 0; i < 8; i++) {
            newX = this.x + moves[i][0];
            newY = this.y + moves[i][1];

            if (newX == 0)
                newX += moves[i][0];
            if (newY == 0)
                newY += moves[i][1];
            if (newX >= -R && newX <= R && newY >= -R && newY <= R)
                counter++;
            if (counter == bestMove)
                break;
        }

        this.x = newX;
        this.y = newY;
        int pointsEarned = 0;

        // Weapons area
        int numOfWeapons = 0;
        for (Weapon w : board.getWeapons()) {
            int[] coords = new int[] { w.getX(), w.getY() };
            if (this.x == coords[0] && this.y == coords[1]) {
                if (this.id == w.getPlayerId()) {
                    System.out.println("You picked a weapon!");
                    switch (w.getType()) {
                    case "pistol":
                        this.pistol = w;
                    case "bow":
                        this.bow = w;
                    case "sword":
                        this.sword = w;
                    }
                    numOfWeapons++;
                    w.setX(0);
                    w.setY(0);
                }
            }
        }

        // Food area
        int numOfFoods = 0;
        for (Food f : board.getFood()) {
            int[] coords = new int[] { f.getX(), f.getY() };
            if (this.x == coords[0] && this.y == coords[1]) {
                System.out.println("You got some food!");
                this.score += f.getPoints();
                pointsEarned += f.getPoints();
                f.setX(0);
                f.setY(0);
                numOfFoods++;
            }
        }

        // Trap area
        int numOfTraps = 0;
        for (Trap t : board.getTraps()) {
            int[] coords = new int[] { t.getX(), t.getY() };
            if (this.x == coords[0] && this.y == coords[1]) {
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
                pointsEarned += t.getPoints();
            }
        }

        path.add(new Integer[] { bestMove, pointsEarned, numOfFoods, numOfTraps, numOfWeapons });

        return new int[] { newX, newY };
    }

    public HeuristicPlayer(int id, String n, Board b, int s, int x, int y, Weapon bow, Weapon pistol, Weapon sword,
            int r) {
        this.id = id;
        this.name = n;
        this.board = b;
        this.score = s;
        this.x = x;
        this.y = y;
        this.bow = bow;
        this.pistol = pistol;
        this.sword = sword;
        this.R = board.getR();
        this.matrix = new char[2 * R][2 * R];
        this.visited = new boolean[2 * R][2 * R];
        this.r = r;
        this.newX = x;
        this.newY = y;
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

        this.matrix = new char[2 * R][2 * R];
        this.visited = new boolean[2 * R][2 * R];

        Queue<int[]> q = new LinkedList<>();
        int[] myCoords = new int[] { newX, newY, 0 };
        translateCoordinates(myCoords);

        q.add(myCoords);
        visited[myCoords[0]][myCoords[1]] = true;

        int[] p2Coords = new int[] { p2.getX(), p2.getY() };
        translateCoordinates(p2Coords);
        matrix[p2Coords[0]][p2Coords[1]] = 'd';

        while (!q.isEmpty()) {
            int[] p = q.poll();

            if (p[2] > r)
                return -1;

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
        newX = this.x;
        newY = this.y;
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
        int pistolGained = 0;
        // Check weapons
        for (Weapon w : board.getWeapons()) {
            int[] coords = new int[] { w.getX(), w.getY() };
            if (newX == coords[0] && newY == coords[1] && this.id == w.getPlayerId()) {
                switch (w.getType()) {
                case "pistol":
                    weaponsGained++;
                    pistolGained++;
                case "bow":
                    weaponsGained++;
                case "sword":
                    weaponsGained++;
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
                if (t.getType() == "rope" && sword == null)
                    pointsLost += t.getPoints();
                if (t.getType() == "animals" && bow == null)
                    pointsLost += t.getPoints();
            }
        }

        int forceKill = 0;
        int dist = playersDistance(p);

        if (dist < 3 && this.pistol != null) {
            forceKill = 10;
        }
        evaluation = weaponsGained * 0.2 + pointsGained * 0.5 + pointsLost * 2 + forceKill + pistolGained * 5;
        return evaluation;
    }

    boolean kill(Player player1, Player player2, float d) {
        boolean dead = false;
        if (playersDistance(player2) < d && player1.pistol != null) {
            dead = true;
        }

        return dead;
    }
}