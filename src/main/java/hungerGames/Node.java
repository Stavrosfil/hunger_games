package hungerGames;

import java.util.ArrayList;

public class Node {

    Node parent;
    ArrayList<Node> children;
    int nodeDepth;
    int[] nodeMove;
    Board nodeBoard;
    double nodeEvaluation;

    public Node() {
        parent = new Node();
        children = new ArrayList<Node>();
        nodeDepth = -1;
        nodeMove = new int[3];
        nodeBoard = new Board();
        nodeEvaluation = -1;
    }

    public Node(Node parent, ArrayList<Node> children, int nodeDepth, int[] nodeMove, Board nodeBoard,
            double nodeEvaluation) {
        this.parent = parent;
        this.children = children;
        this.nodeDepth = nodeDepth;
        this.nodeMove = nodeMove;
        this.nodeBoard = nodeBoard;
        this.nodeEvaluation = nodeEvaluation;
    }

}