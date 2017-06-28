package com.rebel.nim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Nim {
    public static void main(String[] args) {
        Node node = new Node(8, 13, 7, 5, 9);
        generateChildNodes(node).forEach(n -> System.out.println(n + " = " + nimSum(n)));
    }

    private static List<Node> generateChildNodes(Node node) {
        if (node.isEmpty()) {
            return Collections.emptyList();
        }

        List<Node> nodes = new ArrayList<>();
        List<Integer> piles = node.getPiles();

        for (int i = 0; i < piles.size(); i++) {
            List<Integer> temp = node.getPiles();
            while (temp.get(i) > 0) {
                temp.set(i, temp.get(i) - 1);
                Node child = new Node(node, temp);
                nodes.add(child);
            }
        }
        return nodes;
    }

    private static int nimSum(Node current) {
        int xor = 0;
        for (Integer pile : current.getPiles()) {
            xor ^= pile;
        }
        return xor;
    }
}
