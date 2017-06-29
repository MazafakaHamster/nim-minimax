package com.rebel.nim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {

    private List<Integer> piles;
    private int heuristicValue;

    private List<Node> childList;

    public Node(Integer... piles) {
        this.piles = new ArrayList<>(Arrays.asList(piles));
        this.childList = new ArrayList<>();
    }

    public Node(Node parent, List<Integer> piles) {
        parent.childList.add(this);
        this.piles = new ArrayList<>(piles);
        this.childList = new ArrayList<>();
    }

    public List<Integer> getPiles() {
        return new ArrayList<>(piles);
    }

    public List<Node> getChildList() {
        return childList;
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
