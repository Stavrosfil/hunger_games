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
        parent = null;
        children = null;
        nodeDepth = -1;
        nodeMove = new int[3];
        nodeBoard = null;
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

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public ArrayList<Node> getChildren() {
        return this.children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public int getNodeDepth() {
        return this.nodeDepth;
    }

    public void setNodeDepth(int nodeDepth) {
        this.nodeDepth = nodeDepth;
    }

    public int[] getNodeMove() {
        return this.nodeMove;
    }

    public void setNodeMove(int[] nodeMove) {
        this.nodeMove = nodeMove;
    }

    public Board getNodeBoard() {
        return this.nodeBoard;
    }

    public void setNodeBoard(Board nodeBoard) {
        this.nodeBoard = nodeBoard;
    }

    public double getNodeEvaluation() {
        return this.nodeEvaluation;
    }

    public void setNodeEvaluation(double nodeEvaluation) {
        this.nodeEvaluation = nodeEvaluation;
    }

    public Node parent(Node parent) {
        this.parent = parent;
        return this;
    }

    public Node children(ArrayList<Node> children) {
        this.children = children;
        return this;
    }

    public Node nodeDepth(int nodeDepth) {
        this.nodeDepth = nodeDepth;
        return this;
    }

    public Node nodeMove(int[] nodeMove) {
        this.nodeMove = nodeMove;
        return this;
    }

    public Node nodeBoard(Board nodeBoard) {
        this.nodeBoard = nodeBoard;
        return this;
    }

    public Node nodeEvaluation(double nodeEvaluation) {
        this.nodeEvaluation = nodeEvaluation;
        return this;
    }
}