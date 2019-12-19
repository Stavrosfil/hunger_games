package hungerGames;

import java.util.*;

public class Board {

    // NxM table
    int N, M, R;
    // Weapons, food, traps
    int W, F, T;
    int[][] trapAreaLimits = { { -4, -4 }, { 4, -4 }, { 4, -4 }, { 4, -4 } };
    int[][] foodAreaLimits = { { -3, -3 }, { 3, -3 }, { 3, -3 }, { 3, -3 } };
    int[][] weaponAreaLimits = { { -2, -2 }, { 2, -2 }, { 2, -2 }, { 2, -2 } };
    Weapon[] weapons;
    Food[] food;
    Trap[] traps;
    Map<String, Integer[]> typeLimits = new HashMap<>();
    Map<String, boolean[]> hasItem = new HashMap<>();
    // boolean[] hasTrap;

    public Board() {

    }

    /**
     * @param N rows of the board
     * @param M columns of the board
     * @param W number of weapons
     * @param F number of foods
     * @param T number of traps
     * @return
     */
    public Board(int N, int M, int W, int F, int T) {
        this.N = N;
        this.M = M;
        this.W = W;
        this.F = F;
        this.T = T;
        weapons = new Weapon[W];
        food = new Food[F];
        traps = new Trap[T];
        this.R = N / 2;
    }

    public Board(Board board) {
        this.N = board.getN();
        this.M = board.getM();
        this.W = board.getW();
        this.F = board.getF();
        this.T = board.getT();
        weapons = new Weapon[W];
        for (int i = 0; i < W; i++) {
            weapons[i] = new Weapon(board.getWeapons()[i]);
        }
        food = new Food[F];
        for (int i = 0; i < F; i++) {
            food[i] = new Food(board.getFood()[i]);
        }
        traps = new Trap[T];
        for (int i = 0; i < T; i++) {
            traps[i] = new Trap(board.getTraps()[i]);
        }
        this.R = board.getR();
    }

    // Initialize weapons
    void createRandomWeapon() {

        String[] types = { "pistol", "bow", "sword" };

        for (int i = 0; i < this.W; i++) {
            weapons[i] = new Weapon();
            weapons[i].id = i + 1;
            weapons[i].playerId = i % 2 + 1;
            weapons[i].type = types[i % 3];
        }
    }

    // Initialize traps
    void createRandomTrap() {

        Random r = new Random();
        String[] types = { "ropes", "animals" };

        for (int i = 0; i < this.T; i++) {
            traps[i] = new Trap();
            traps[i].id = i + 1;
            traps[i].type = types[(r.nextInt(10) + 1) % 2];
            traps[i].points = r.nextInt(10) - 10;
        }
    }

    // Initialize food
    void createRandomFood() {
        Random r = new Random();

        for (int i = 0; i < this.F; i++) {
            food[i] = new Food();
            food[i].id = i + 1;
            food[i].points = r.nextInt(10) + 1;
        }
    }

    int[] getItemCoordinates(String itemName, int position, int L) {

        int availableSpots = 4 * (L - 1);
        int counter = 1;
        int x = 0, y = 0;

        // Every time we lose 8 positions (one from 0 index, and one from repeated
        // corner boxes)
        for (int i = 0; i < availableSpots + 8; i++) {
            // 5 2
            int adjIndex = i % (L + 1) - (L / 2);
            // Skip zero index and corners
            if (adjIndex != 0 && i % (L + 1) != L && !hasItem.get(itemName)[i]) {
                if (counter != position) {
                    counter++;
                }
                // If we reach the desirable position, place the item
                else {
                    if (i <= L) {
                        // Put something in top line of rectangle
                        x = (adjIndex);
                        y = (-1 * L / 2);
                    } else if (i <= L * 2 + 1) {
                        x = (L / 2);
                        y = (adjIndex);
                    } else if (i <= L * 3 + 1) {
                        x = (adjIndex * -1);
                        y = (L / 2);
                    } else {
                        x = (-1 * L / 2);
                        y = (adjIndex * -1);
                    }
                    hasItem.get(itemName)[i] = true;
                    return (new int[] { x, y });
                }
            }
        }
        return getItemCoordinates("weapon2", position - counter + 1, 2);
    }

    // Initialize board
    void createBoard() {
        createRandomFood();
        createRandomTrap();
        createRandomWeapon();

        Random randomPosition = new Random();

        // HashMap containing the radious of each item
        typeLimits.put("trap", new Integer[] { 4 });
        typeLimits.put("food", new Integer[] { 3 });
        typeLimits.put("weapon", new Integer[] { 2, 1 });

        int L = typeLimits.get("weapon")[0] * 2;
        int L2 = typeLimits.get("weapon")[1] * 2;
        int availableSpots = 4 * (L - 1) + 4 * (L2 - 1);
        hasItem.put("weapon", new boolean[availableSpots + 8]);
        hasItem.put("weapon2", new boolean[4 + 8]);
        for (int i = 0; i < this.W; i++) {
            int[] coordinates = getItemCoordinates("weapon", randomPosition.nextInt(availableSpots) + 1, L);
            this.weapons[i].setX(coordinates[0]);
            this.weapons[i].setY(coordinates[1]);
            availableSpots--;
        }
        L = typeLimits.get("food")[0] * 2;
        availableSpots = 4 * (L - 1);
        hasItem.put("food", new boolean[availableSpots + 8]);
        for (int i = 0; i < this.F; i++) {
            int[] coordinates = getItemCoordinates("food", randomPosition.nextInt(availableSpots) + 1, L);
            this.food[i].setX(coordinates[0]);
            this.food[i].setY(coordinates[1]);
            availableSpots--;
        }
        L = typeLimits.get("trap")[0] * 2;
        availableSpots = 4 * (L - 1);
        hasItem.put("trap", new boolean[availableSpots + 8]);
        for (int i = 0; i < this.T; i++) {
            int[] coordinates = getItemCoordinates("trap", randomPosition.nextInt(availableSpots) + 1, L);
            this.traps[i].setX(coordinates[0]);
            this.traps[i].setY(coordinates[1]);
            availableSpots--;
        }

    }

    void translateCoordinates(int[] coords) {
        for (int i = 0; i < 2; i++) {
            if (coords[i] < 0)
                coords[i] += R;
            else
                coords[i] += R - 1;
        }
    }

    String[][] getStringRepresentation() {

        String[][] result = new String[2 * R][2 * R];
        for (String[] i : result) {
            Arrays.fill(i, "___");
        }
        for (Trap t : traps) {
            int[] coords = new int[] { t.getX(), t.getY() };
            translateCoordinates(coords);
            String s = "T" + t.getId();
            result[coords[1]][coords[0]] = s;
        }
        for (Food f : food) {
            int[] coords = new int[] { f.getX(), f.getY() };
            translateCoordinates(coords);
            String s = "F" + f.getId();
            result[coords[1]][coords[0]] = s;
        }
        for (Weapon w : weapons) {
            int[] coords = new int[] { w.getX(), w.getY() };
            translateCoordinates(coords);
            String s = "W" + w.getPlayerId() + w.getId();
            result[coords[1]][coords[0]] = s;
        }

        return result;
    }

    void resizeBoard(Player p1, Player p2) {
        if (Math.abs(p1.getX()) < R && Math.abs(p1.getY()) < R && Math.abs(p2.getX()) < R && Math.abs(p2.getY()) < R)
            if (R > 4)
                R--;
            else if (R == 4) {
                for (Trap t : this.traps) {
                    t.setX(0);
                    t.setY(0);
                }
                R--;
            } else if (R == 3) {
                for (Food f : this.food) {
                    f.setX(0);
                    f.setY(0);
                }
                R--;
            }
    }

    public int getR() {
        return this.R;
    }

    public void setR(int R) {
        this.R = R;
    }

    public int getN() {
        return this.N;
    }

    public void setN(int N) {
        this.N = N;
    }

    public int getM() {
        return this.M;
    }

    public void setM(int M) {
        this.M = M;
    }

    public int getW() {
        return this.W;
    }

    public void setW(int W) {
        this.W = W;
    }

    public int getF() {
        return this.F;
    }

    public void setF(int F) {
        this.F = F;
    }

    public int getT() {
        return this.T;
    }

    public void setT(int T) {
        this.T = T;
    }

    public int[][] getWeaponAreaLimits() {
        return this.weaponAreaLimits;
    }

    public void setWeaponAreaLimits(int[][] weaponAreaLimits) {
        this.weaponAreaLimits = weaponAreaLimits;
    }

    public int[][] getFoodAreaLimits() {
        return this.foodAreaLimits;
    }

    public void setFoodAreaLimits(int[][] foodAreaLimits) {
        this.foodAreaLimits = foodAreaLimits;
    }

    public int[][] getTrapAreaLimits() {
        return this.trapAreaLimits;
    }

    public void setTrapAreaLimits(int[][] trapAreaLimits) {
        this.trapAreaLimits = trapAreaLimits;
    }

    public Weapon[] getWeapons() {
        return this.weapons;
    }

    public void setWeapons(Weapon[] weapons) {
        this.weapons = weapons;
    }

    public Food[] getFood() {
        return this.food;
    }

    public void setFood(Food[] food) {
        this.food = food;
    }

    public Trap[] getTraps() {
        return this.traps;
    }

    public void setTraps(Trap[] traps) {
        this.traps = traps;
    }
}