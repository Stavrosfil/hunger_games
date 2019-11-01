package hungerGames;

import java.util.Random;

public class Board {

    // NxM table
    int N, M;
    // Weapons, food, traps
    int W, F, T;
    int[][] weaponAreaLimits = new int[4][2];
    int[][] foodAreaLimits = new int[4][2];
    int[][] trapAreaLimits = new int[4][2];
    // TODO: add weapons class
    // Weapon[] weapons;
    Food[] food;
    Trap[] traps;

    public Board() {

    }

    public Board(int N, int M, int W, int F, int T) {
        this.N = N;
        this.M = M;
        this.W = W;
        this.F = F;
        this.T = T;
        // weapons = new Weapon[W];
        food = new Food[F];
        traps = new Trap[T];
    }

    void getRandomCoordinates(int[][] arr, int n) {

    }

    // Initialize weapons
    void createRandomWeapon() {

    }

    // Initialize traps
    void createRandomTrap() {
        // Random random = new Random();
        // for (int i = 0; i < this.T; i++) {

        // Trap trap = new Trap();

        // }
    }

    // Initialize food
    void createRandomFood() {

    }

    // Initialize board
    void createBoard() {

    }

    // TODO: getters and setters

}