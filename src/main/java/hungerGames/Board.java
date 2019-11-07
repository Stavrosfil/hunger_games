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
    Weapon[] weapons;
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
        weapons = new Weapon[W];
        food = new Food[F];
        traps = new Trap[T];
    }

    public Board (Board board) {
        this.N = board.N;
        this.M = board.M;
        this.W = board.W;   
        this.F = board.F;
        this.T = board.T;
        weapons = new Weapon[W];
        food = new Food[F];
        traps = new Trap[T];    
    }

    void getRandomCoordinates(int[][] arr, int n) {

    }

    // Initialize weapons
    void createRandomWeapon() {

        for(int i = 0; i < this.W; i++) {
            weapons[i] = new Weapon();
            weapons[i].id = i + 1;
            if(i % 2 == 0){
                weapons[i].playerId = 1;
            }
            else {
                weapons[i].playerId = 2;
            }
            int decide = i;
            switch(decide % 3) {
                case 0:
                    weapons[i].type = "pistol";
                    break;
                case 1:
                    weapons[i].type = "bow";
                    break;
                case 2:
                    weapons[i].type = "sword";
                    break;
                default:
                    System.out.println("You messed up moron");
                    break;    
            }

        }
    }

    // Initialize traps
    void createRandomTrap() {
        Random r = new Random();
        
        for(int i = 0; i < this.T; i++) {
            traps[i] = new Trap();
            traps[i].id = i + 1;
            int decide = r.nextInt(10) + 1;
            if(decide%2 == 0) {
                traps[i].type = "ropes";
            }
            else {
                traps[i].type = "animals";
            }
            traps[i].points = r.nextInt(10) - 10;
        }
    }

    // Initialize food
    void createRandomFood() {
        Random r = new Random();

        for(int i = 0; i < this.F; i++) {
            food[i] = new Food();
            food[i].id = i + 1;
            food[i].points = r.nextInt(10) + 1;
        }
    }

    // Initialize board
    void createBoard() {

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