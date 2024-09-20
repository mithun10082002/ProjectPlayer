package org.ProjectPlayer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Player player1 = new Player("Player1", true, scanner);
        Player player2 = new Player("Player2", false, scanner);

        player1.setOpponent(player2);
        player2.setOpponent(player1);

        Thread thread1 = new Thread(player1);
        Thread thread2 = new Thread(player2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Program finished. All messages exchanged.");
    }
}