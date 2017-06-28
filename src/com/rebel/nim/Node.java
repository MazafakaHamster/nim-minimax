package com.rebel.nim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {

    private List<Integer> piles;

    private Node parent;
    private List<Node> childList;

    public Node(Integer... piles) {
        this.piles = new ArrayList<>(Arrays.asList(piles));
        this.childList = new ArrayList<>();
    }

    public Node(Node parent, List<Integer> piles) {
        this.parent = parent;
        parent.childList.add(this);
        this.piles = new ArrayList<>(piles);
        this.childList = new ArrayList<>();
    }

    public List<Integer> getPiles() {
        return new ArrayList<>(piles);
    }

    public long gePilesNum() {
        return piles.size();
    }

    public long getNotEmptyPilesNum() {
        return piles.stream().filter(i -> i > 0).count();
    }

    public boolean isEmpty() {
        return piles.stream().mapToInt(i -> i).sum() == 0;
    }

    @Override
    public String toString() {
        return piles.toString();
    }
}
