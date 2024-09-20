package org.ProjectPlayer;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Player implements Runnable {
    private final String name;
    private final BlockingQueue<String> inbox = new LinkedBlockingQueue<>();
    private Player opponent;
    private int messageCounter = 0;
    private static final int MAX_MESSAGES = 10;
    private final Scanner scanner;
    private final boolean isInitiator;

    public Player(String name, boolean isInitiator, Scanner scanner) {
        this.name = name;
        this.isInitiator = isInitiator;
        this.scanner = scanner;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    private void sendMessage() {
        try {
            System.out.println(name + ": Enter a message to send to " + opponent.name + ":");
            String message = scanner.nextLine();
            opponent.inbox.put(message);
            messageCounter++;
            System.out.println(name + " sent message " + messageCounter);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Failed to send message.");
        }
    }

    private void receiveMessage() {
        try {
            inbox.take();
            messageCounter++;
            System.out.println(name + " received message " + messageCounter);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Failed to receive message.");
        }
    }

    @Override
    public void run() {
        while (messageCounter < MAX_MESSAGES) {
            if (isInitiator) {
                sendMessage();
                receiveMessage();
            } else {
                receiveMessage();
                sendMessage();
            }
        }
        System.out.println(name + " has finished after sending and receiving " + messageCounter + " messages.");
    }
}

