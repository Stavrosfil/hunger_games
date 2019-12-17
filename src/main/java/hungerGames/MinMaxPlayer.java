package hungerGames;

import java.util.ArrayList;
import java.lang.Math.*;
import java.util.*;
import java.util.Queue;
import java.util.Random;

public class MinMaxPlayer extends Player {

    HeuristicPlayer opponent;

    ArrayList<Integer[]> path = new ArrayList<>();
    int R;
    char[][] matrix;
    boolean visited[][];
    int movesCount = 0;
    int newX, newY;

    public MinMaxPlayer(int id, String n, Board b, int s, int x, int y, Weapon bow, Weapon pistol, Weapon sword,
            HeuristicPlayer opponent) {
        super(id, n, b, s, x, y, bow, pistol, sword);
        this.opponent = opponent;
    }

    void createOpponentSubtree(Node root, int depth, Board b, int xCurrentPos, int yCurrentPos, int xOpponentPos,
            int yOpponentPos) {

        int possibleMoves = 8;
        R = root.getNodeBoard().getR();
        boolean isXEdge = xOpponentPos == -R || xOpponentPos == R;
        boolean isYEdge = yOpponentPos == -R || yOpponentPos == R;
        if (isXEdge && isYEdge) {
            possibleMoves = 3;
        } else if (isXEdge || isYEdge) {
            possibleMoves = 5;
        }

        for (int die = 1; die <= possibleMoves; die++) {

            // ! Check if deep copy is happening
            // Deep copy of the board to clone it and simulate movement.
            Board newB = new Board(root.getNodeBoard());

            // return new int[] { newX, newY, pointsEarned, numOfWeapons };
            int[] moveData = virtualMove(xOpponentPos, yOpponentPos, xCurrentPos, yCurrentPos, newB, die);

            Node child = new Node();
            child.setNodeEvaluation(root.getNodeEvaluation() - opponent.evaluate(die, this));
            child.setNodeBoard(newB);
            child.setNodeDepth(depth);
            child.setNodeMove(new int[] { moveData[0], moveData[1], die });
            child.setParent(root);
            root.addChild(child);
        }

        // ! check initialization
        double minEvaluation = root.getChildren().get(0).getNodeEvaluation();

        for (Node n : root.getChildren()) {
            if (minEvaluation > n.getNodeEvaluation()) {
                minEvaluation = n.getNodeEvaluation();
                root.setNodeEvaluation(minEvaluation);
            }
        }

    }

    void createSubTree(Node root, int depth, int xCurrentPos, int yCurrentPos, int xOpponentPos, int yOpponentPos) {

        int possibleMoves = 8;
        R = root.getNodeBoard().getR();
        boolean isXEdge = xCurrentPos == -R || yCurrentPos == R;
        boolean isYEdge = xCurrentPos == -R || yCurrentPos == R;
        if (isXEdge && isYEdge) {
            possibleMoves = 3;
        } else if (isXEdge || isYEdge) {
            possibleMoves = 5;
        }

        for (int die = 1; die <= possibleMoves; die++) {

            // ! Check if deep copy is happening
            // Deep copy of the board to clone it and simulate movement.
            Board b = new Board(root.getNodeBoard());

            // return new int[] { newX, newY, pointsEarned, numOfWeapons };
            int[] moveData = virtualMove(xCurrentPos, yCurrentPos, xOpponentPos, yOpponentPos, b, die);

            Node child = new Node();
            child.setNodeEvaluation(evaluate(moveData[0], moveData[1], opponent));
            child.setNodeBoard(b);
            child.setNodeDepth(depth);
            child.setNodeMove(new int[] { moveData[0], moveData[1], die });
            child.setParent(root);
            root.addChild(child);

            createOpponentSubtree(child, depth + 1, b, moveData[0], moveData[1], xOpponentPos, yOpponentPos);

        }

        double maxEvaluation = root.getChildren().get(0).getNodeEvaluation();
        root.setNodeMove(root.getChildren().get(0).getNodeMove());

        for (Node n : root.getChildren()) {
            if (maxEvaluation < n.getNodeEvaluation()) {
                root.setNodeMove(n.getNodeMove());
                maxEvaluation = n.getNodeEvaluation();
                root.setNodeEvaluation(maxEvaluation);
            }
        }
    }

    int chooseMinMaxMove(Node root) {
        createSubTree(root, 1, this.x, this.y, opponent.getX(), opponent.getY());
        System.out.println(root.getNodeMove()[2]);

        return 0;
    }

    // Simulates player movement acording to given die. New coordinates are retured.
    int[] virtualMove(int xCurrentPos, int yCurrentPos, int xOpponentPos, int yOpponentPos, Board board, int die) {
        int moves[][] = { { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, };
        R = board.getR();

        int bestMove = die;

        int counter = 0;
        newX = xCurrentPos;
        newY = yCurrentPos;
        for (int i = 0; i < 8; i++) {
            newX = xCurrentPos + moves[i][0];
            newY = yCurrentPos + moves[i][1];

            if (newX == 0)
                newX += moves[i][0];
            if (newY == 0)
                newY += moves[i][1];
            if (newX >= -R && newX <= R && newY >= -R && newY <= R)
                counter++;
            if (counter == bestMove)
                break;
        }

        // Weapons area
        for (Weapon w : board.getWeapons()) {
            int[] coords = new int[] { w.getX(), w.getY() };
            if (newX == coords[0] && newY == coords[1]) {
                if (this.id == w.getPlayerId()) {
                    w.setX(0);
                    w.setY(0);
                }
            }
        }

        // Food area
        for (Food f : board.getFood()) {
            int[] coords = new int[] { f.getX(), f.getY() };
            if (newX == coords[0] && newY == coords[1]) {
                f.setX(0);
                f.setY(0);
            }
        }

        return new int[] { newX, newY };

        // return new int[] { newX, newY };
    }

    // Moves player according to die. New coordinates are retured.
    int[] move(int xCurrentPos, int yCurrentPos, int xOpponentPos, int yOpponentPos, Board board, int die) {
        int moves[][] = { { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, };
        R = board.getR();

        int bestMove = die;
        int pointsEarned = 0;

        int counter = 0;
        newX = xCurrentPos;
        newY = yCurrentPos;
        for (int i = 0; i < 8; i++) {
            newX = xCurrentPos + moves[i][0];
            newY = yCurrentPos + moves[i][1];

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

        // Weapons area
        int numOfWeapons = 0;
        for (Weapon w : board.getWeapons()) {
            int[] coords = new int[] { w.getX(), w.getY() };
            if (newX == coords[0] && newY == coords[1]) {
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
            if (newX == coords[0] && newY == coords[1]) {
                System.out.println("You got some food!");
                // ! we do not add points to the player as of now
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
            if (newX == coords[0] && newY == coords[1]) {
                numOfTraps++;
                if (t.getType() == "rope") {
                    if (sword != null) {
                        System.out.println("Congrats you cut the rope!");
                    } else if (sword == null) {
                        System.out.println("Oh no you got trapped in a rope and you dont have a sword...");
                        // ! we do not add points to the player as of now
                        this.score += t.getPoints();
                    }
                }
                if (t.getType() == "animals") {
                    if (bow != null) {
                        System.out.println("Congrats you killed the animal!");
                    } else if (bow == null) {
                        System.out.println("Oh no you don't have a bow and now this animal will attack you...");
                        // ! we do not add points to the player as of now
                        this.score += t.getPoints();
                    }
                }
                pointsEarned += t.getPoints();
            }
        }

        path.add(new Integer[] { bestMove, pointsEarned, numOfFoods, numOfTraps, numOfWeapons });

        return new int[] { newX, newY };
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

    int playersDistance(int xStart, int yStart, int xItem, int yItem) {

        this.matrix = new char[2 * R][2 * R];
        this.visited = new boolean[2 * R][2 * R];

        Queue<int[]> q = new LinkedList<>();
        int[] myCoords = new int[] { xStart, yStart, 0 };
        translateCoordinates(myCoords);

        q.add(myCoords);
        visited[myCoords[0]][myCoords[1]] = true;

        int[] p2Coords = new int[] { xItem, yItem };
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

    // ! check if we need a new board for the function
    double evaluate(int x, int y, Player opponent) {

        double evaluation = 0;

        newX = x;
        newY = y;

        int weaponsGained = 0;
        int pointsGained = 0;
        int pointsLost = 0;
        int pistolGained = 0;
        int pistolDistance = 0;

        // Check weapons
        for (Weapon w : board.getWeapons()) {
            int[] coords = new int[] { w.getX(), w.getY() };

            if (this.id == w.getPlayerId()) {
                if (newX == coords[0] && newY == coords[1]) {
                    if (w.getType() == "pistol")
                        pistolGained++;
                    weaponsGained++;
                } else if (w.getType() == "pistol") {
                    pistolDistance += playersDistance(x, y, w.getX(), w.getY());
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
        int dist = playersDistance(x, y, opponent.getX(), opponent.getY());

        if (dist < 3 && this.pistol != null) {
            forceKill = 10;
        }

        evaluation = weaponsGained * 0.2 + pointsGained * 0.5 + pointsLost * 2 + forceKill + pistolGained * 5
                + pistolDistance * -10;
        return evaluation;
    }

    boolean kill(Player player1, Player player2, float d) {
        boolean dead = false;
        if (playersDistance(x, y, player2.getX(), player2.getY()) < d && player1.pistol != null) {
            dead = true;
        }

        return dead;
    }

}