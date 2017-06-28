package com.rebel.nim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {

    private List<Integer> piles;
    private long depth;
    private int heuristicValue;

    private Node parent;
    private List<Node> childList;

    public Node(Integer... piles) {
        this.piles = new ArrayList<>(Arrays.asList(piles));
        this.childList = new ArrayList<>();
        this.depth = 0;
    }

    public Node(Node parent, List<Integer> piles) {
        this.parent = parent;
        parent.childList.add(this);
        this.piles = new ArrayList<>(piles);
        this.childList = new ArrayList<>();
        this.depth = parent.depth + 1;
    }

    public List<Integer> getPiles() {
        return new ArrayList<>(piles);
    }

    public List<Node> getChildList() {
        return childList;
    }

    public Node getParent() {
        return parent;
    }

    public long getDepth() {
        return depth;
    }

    public long gePilesNum() {
        return piles.size();
    }

    public long getNotEmptyPilesNum() {
        return piles.stream().filter(i -> i > 0).count();
    }

    public int getHeuristicValue() {
        return heuristicValue;
    }

    public void setHeuristicValue(int heuristicValue) {
        this.heuristicValue = heuristicValue;
    }

    public boolean isEmpty() {
        return piles.stream().mapToInt(i -> i).sum() == 0;
    }

    @Override
    public String toString() {
        return piles.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return piles != null ? piles.equals(node.piles) : node.piles == null;
    }

    @Override
    public int hashCode() {
        return piles != null ? piles.hashCode() : 0;
    }
}
