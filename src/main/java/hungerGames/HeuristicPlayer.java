package hungerGames;

import java.util.ArrayList;
import java.lang.Math.*;

public class HeuristicPlayer extends Player {

    ArrayList<Integer[]> path;
    static int r;

    public HeuristicPlayer() {

    }

    float playersDistance(Player p) {
        // Use Pythagoras' theorem to calculate the Eucledian distance.
        return (float) Math.sqrt(Math.pow(this.x - p.getX(), 2) + Math.pow(this.y - p.getY(), 2));
    }

}