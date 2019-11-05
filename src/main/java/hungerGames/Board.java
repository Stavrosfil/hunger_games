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
        Boolean[][] weaponCheck = new Boolean[3][3]; //A boolean array in order to avoid collisions on weapon positions
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                weaponCheck[i][j] = true;   //All set true at first
            }
        }

        Random random = new Random();

        for(int i = 0; i < this.W; i++) {
            weapons[i] = new Weapon();

            weapons[i].id = i+1;
            do{
                int decide = random.nextInt(4) + 1; //Assigns a random number from 1 to 4
                switch(decide) { 
                    case 1:
                        weapons[i].x = random.nextInt(2) + 1;
                        weapons[i].y = random.nextInt(2) + 1;
                        break;
                    case 2:
                        weapons[i].x = random.nextInt(2) + 1;
                        weapons[i].y = random.nextInt(2) + -2;
                        break;
                    case 3:
                        weapons[i].x = random.nextInt(2) + -2;
                        weapons[i].y = random.nextInt(2) + 1;
                        break;
                    case 4:
                        weapons[i].x = random.nextInt(2) + -2;
                        weapons[i].y = random.nextInt(2) + -2;
                        break;
                    default:
                        System.out.println("You messed up");
                        break;
                }
                
            }while(!weaponCheck[weapons[i].x][weapons[i].y] && Math.abs(weapons[i].x)==Math.abs(weapons[i].y));  //Checks if this particular position is free or taken and if its in the edges

            weaponCheck[weapons[i].x][weapons[i].y] = false; //Marks the coordinates of the last put weapon as taken
            
            int wtype = random.nextInt(3)+1; //Random type
            switch(wtype) {
                case 1:
                    weapons[i].type = "pistol";
                    break;
                case 2:
                    weapons[i].type = "bow";
                    break;
                case 3:
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
        Boolean[][] trapCheck = new Boolean[5][5]; //A boolean array in order to avoid collisions on trap positions
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                trapCheck[i][j] = true;   //All set true at first
            }
        }

        Random random = new Random();

        for(int i = 0; i < this.T; i++) {
            traps[i] = new Trap();
            traps[i].id = i+1;
        
        do{
            int decide = random.nextInt(4)+1;
            switch(decide) {
                case 1:
                    traps[i].x = -4;
                    do{
                    traps[i].y = random.nextInt(6) - 3;
                    }while(traps[i].y == 0);
                    break;
                case 2: 
                    do{
                    traps[i].x = random.nextInt(6) - 3;
                    }while(traps[i].x == 0);
                    traps[i].y = -4;
                    break;
                case 3:
                    traps[i].x = 4;
                    do{
                    traps[i].y = random.nextInt(6) - 3;
                    }while(traps[i].y == 0);
                    break;
                case 4:
                    do{
                    traps[i].x = random.nextInt(6) - 3;
                    }while(traps[i].x == 0);
                    traps[i].y = 4;
                    break;
                default:
                    System.out.println("You did sth wrong..");
                    break;        
            }
        }while(!trapCheck[traps[i].x][traps[i].y]); 

        trapCheck[traps[i].x][traps[i].y] = false;

        int ttype = random.nextInt(100) + 1;
        if(ttype%2 == 0) {
            traps[i].type = "ropes";
        }
        else {
            traps[i].type = "animals";
        }
        traps[i].points = random.nextInt(10) - 10;
        }
    }

    // Initialize food
    void createRandomFood() {

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