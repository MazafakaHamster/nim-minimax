package com.rebel.nim;

import java.util.*;

public class Nim {

    private static Set<Node> generated = new HashSet<>();

    public static void main(String[] args) {
        Node node = new Node(8, 13, 7, 5, 9);
        generateTree(node);
        minimax(node, true);

        node.getChildList().forEach(n -> System.out.println(n + " value " + n.getHeuristicValue()));
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

    private static void generateTree(Node root) {
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node node = queue.pollFirst();
            if (generated.contains(node))
                continue;
            generated.add(node);
            queue.addAll(generateChildNodes(node));
        }
    }

    private static int minimax(Node node, boolean maximizingPlayer) {
        if (node.isEmpty()) {
            return heuristicEvaluation(node);
        }

        if (maximizingPlayer) {
            int bestValue = Integer.MIN_VALUE;
            for (Node child : node.getChildList()) {
                int v = minimax(child, false);
                bestValue = Math.max(bestValue, v);
                child.setHeuristicValue(bestValue);
            }
            return bestValue;
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (Node child : node.getChildList()) {
                int v = minimax(child, true);
                bestValue = Math.min(bestValue, v);
                child.setHeuristicValue(bestValue);
            }
            return bestValue;
        }
    }

    private static int heuristicEvaluation(Node node) {
        return nimSum(node) != 0 ? 1 : -1;
    }

    private static int nimSum(Node node) {
        int xor = 0;
        for (Integer pile : node.getPiles()) {
            xor ^= pile;
        }
        return xor;
    }
}