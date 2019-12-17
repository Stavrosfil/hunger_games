package hungerGames;

import java.util.ArrayList;
import java.lang.Math.*;
import java.util.*;
import java.util.Queue;
import java.util.Random;

public class MinMaxPlayer extends Player {

    Player opponent;

    public MinMaxPlayer(int id, String n, Board b, int s, int x, int y, Weapon bow, Weapon pistol, Weapon sword,
            Player opponent) {
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

        for (int die = 0; die < possibleMoves; die++) {

            // return new int[] { newX, newY, pointsEarned, numOfWeapons };
            int[] moveData = move(xOpponentPos, yOpponentPos, xCurrentPos, yCurrentPos, b, die);
            // int[] moveData = move(player, opponent, b, die);

            Node child = new Node();
            child.setNodeEvaluation(evaluate(die, moveData[2], moveData[3], opponent));
            child.setNodeBoard(b);
            child.setNodeDepth(depth);
            child.setNodeMove(new int[] { moveData[0], moveData[1], die });
            child.setParent(root);
            root.addChild(child);

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

        for (int die = 0; die < possibleMoves; die++) {

            // ! Check if deep copy is happening
            // Deep copy of the board to clone it and simulate movement.
            Board b = new Board(root.getNodeBoard());

            // return new int[] { newX, newY, pointsEarned, numOfWeapons };
            int[] moveData = move(xCurrentPos, yCurrentPos, xOpponentPos, yOpponentPos, b, die);

            Node child = new Node();
            child.setNodeEvaluation(evaluate(die, moveData[2], moveData[3], opponent));
            child.setNodeBoard(b);
            child.setNodeDepth(depth);
            child.setNodeMove(new int[] { moveData[0], moveData[1], die });
            child.setParent(root);
            root.addChild(child);

            createOpponentSubtree(child, depth + 1, b, moveData[0], moveData[1], xOpponentPos, yOpponentPos);

        }

    }

    int chooseMinMaxMove(Node root) {
        createSubTree(root, 1, this.x, this.y, opponent.getX(), opponent.getY());

        return 0;
    }

    double evaluate(int die, int x, int y, Player opponent) {
        Random r = new Random();
        return r.nextDouble();
    }

    // Simulates player movement acording to given die. New coordinates are retured.
    int[] move(int xCurrentPos, int yCurrentPos, int xOpponentPos, int yOpponentPos, Board board, int die) {
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

        // this.x = newX;
        // this.y = newY;
        int pointsEarned = 0;

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
                // this.score += f.getPoints();
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
                        // this.score += t.getPoints();
                    }
                }
                if (t.getType() == "animals") {
                    if (bow != null) {
                        System.out.println("Congrats you killed the animal!");
                    } else if (bow == null) {
                        System.out.println("Oh no you don't have a bow and now this animal will attack you...");
                        // ! we do not add points to the player as of now
                        // this.score += t.getPoints();
                    }
                }
                pointsEarned += t.getPoints();
            }
        }

        path.add(new Integer[] { bestMove, pointsEarned, numOfFoods, numOfTraps, numOfWeapons });

        return new int[] { newX, newY, pointsEarned, numOfWeapons };

        // return new int[] { newX, newY };
    }

    ArrayList<Integer[]> path = new ArrayList<>();
    int r;
    // ! check this
    int R;
    char[][] matrix;
    boolean visited[][];
    int movesCount = 0;
    int newX, newY;

}