package com.rebel.nim;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Nim {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private Node state;

    public Nim(Node state) {
        this.state = state;
    }

    public static void main(String[] args) {
//        Node node = new Node(readPiles());
        Node node = new Node(5, 8, 13, 7, 5, 9);
        Nim nim = new Nim(node);

        nim.startGame();
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

    private static int minimax(Node node, int depth, boolean maximizingPlayer) {
        if (depth == 0 || node.isEmpty()) {
            return (maximizingPlayer ? 1 : -1) * heuristicEvaluation(node);
        }

        if (maximizingPlayer) {
            int bestValue = Integer.MIN_VALUE;
            for (Node child : generateChildNodes(node)) {
                int value = minimax(child, depth - 1, false);
                if (value > bestValue) {
                    bestValue = value;
                }
                child.setHeuristicValue(value);
                node.setHeuristicValue(bestValue);
            }
            return bestValue;
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (Node child : generateChildNodes(node)) {
                int value = minimax(child, depth - 1, true);
                if (value < bestValue) {
                    bestValue = value;
                }
                node.setHeuristicValue(bestValue);
                child.setHeuristicValue(value);
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

    private static List<Integer> readPiles() {
        boolean finished = false;
        List<Integer> result = new ArrayList<>();
        while (!finished) {
            try {
                System.out.println("Enter piles:");
                String[] pilesText = reader.readLine().split(", ");
                result.addAll(Arrays.stream(pilesText).map(Integer::parseInt).collect(Collectors.toList()));
                finished = true;
            } catch (Exception e) {
                System.out.println("Wrong input");
            }
        }
        return result;
    }

    public void startGame() {
        boolean userMove = true;

        while (true) {
            if (userMove) {
                userMove();
            } else {
                comupterMove();
            }
            if (state.isEmpty()) {
                System.out.println((userMove ? "User" : "Computer") + " wins");
                return;
            }
            System.out.println();
            userMove = !userMove;
        }
    }

    private void userMove() {
        System.out.println("----User move----");
        System.out.println("Current state: " + state);
        state = move();
        System.out.println("After move: " + state);
    }

    private void comupterMove() {
        System.out.println("----Computer move----");
        System.out.println("Current state: " + state);
        int minimax = minimax(state, 2, true);
//        System.out.println("Possible steps:");
//        state.getChildList().forEach(n -> System.out.print(n + " value " + n.getHeuristicValue() + "  "));
//        System.out.println();
        Optional<Node> perfectMove = state.getChildList().stream().filter(n -> n.getHeuristicValue() == minimax).findAny();
        state = perfectMove.orElse(state.getChildList().get(0));
        System.out.println("After move: " + state);
    }

    private Node move() {
        while (true) {
            int pile = readNum("Choose pile:") - 1;
            List<Integer> piles = state.getPiles();
            if (pile <= 0 || piles.size() < pile) {
                System.out.println("Wrong pile number");
                continue;
            }

            int amount = readNum("Amount pile:");
            if (amount <= 0 || piles.get(pile) == 0 || piles.get(pile) < amount) {
                System.out.println("Wrong amount");
                continue;
            }

            piles.set(pile, piles.get(pile) - amount);
            return new Node(state, piles);
        }
    }

    private int readNum(String text) {
        boolean finished = false;
        int result = 0;
        while (!finished) {
            try {
                System.out.println(text);
                result = Integer.parseInt(reader.readLine());
                finished = true;
            } catch (Exception e) {
                System.out.println("Wrong input");
            }
        }
        return result;
    }
}