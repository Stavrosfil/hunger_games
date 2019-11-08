package hungerGames;

import java.util.Random;

public final class Game {
    int round;
  
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

        Player p1 = new Player(1, "Yiannis", board, 0, -10, -10, null, null, null);
        Player p2 = new Player(2, "Stavros", board, 0, 10, 10, null, null, null);

        Random r = new Random();
        int begins = r.nextInt(6) + 1; //Dice roll
        int count = 1;
        if(begins % 2 == 0){
            do{
                System.out.println(p1.name + " plays first!");
                g.setRound(count);
                System.out.println("Round " + g.getRound());
                p1.move();
                p2.move();
                System.out.println(p1.name + " is now at: (" + p1.getX() + "," + p1.getY() + ")");
                System.out.println(p2.name + " is now at: (" + p2.getX() + "," + p2.getY() + ")");
                count++;
                if(g.getRound() % 3 == 0) 
                    board.resizeBoard(p1,p2);
                
                System.out.print(board.getStringRepresentation());
            }while(board.getN() != 4 && board.getM() != 4);    
        }
        
        else {
            do{
                System.out.println(p2.name + " plays first!");
                g.setRound(count);
                System.out.println("Round " + g.getRound());
                p2.move();
                p1.move();
                System.out.println(p2.name + " is now at: (" + p2.getX() + "," + p2.getY() + ")");
                System.out.println(p1.name + " is now at: (" + p1.getX() + "," + p1.getY() + ")");
                count++;
                if(g.getRound() % 3 == 0) 
                    board.resizeBoard(p1,p2);
                
                System.out.print(board.getStringRepresentation());
            }while(board.getN() != 4 && board.getM() != 4); 
        }

        System.out.print("The hunger games are over!");
        if(p1.score > p2.score) {
            System.out.println(p1.name + " is the 2020 winner! With " + p1.score + " points!");
        }
        else if(p2.score > p1.score) {
            System.out.println(p2.name + " is the 2020 winner! With " + p2.score + " points!");
        }
    }

public int getRound() {
    return this.round;
}

public void setRound(int round) {
    this.round = round;
} 
}
