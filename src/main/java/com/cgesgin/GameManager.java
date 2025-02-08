package com.cgesgin;

import java.util.Random;
import java.util.Scanner;

public class GameManager {

    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 100;
    private static final int EASY_CHANCES = 10;
    private static final int MEDIUM_CHANCES = 5;
    private static final int HARD_CHANCES = 3;

    private int easyScore = 100;
    private int mediumHighScore = 100;
    private int hardHighScore = 100;

    private void getInfo() {
        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I'm thinking of a number between 1 and 100.");
        System.out.println("You have a limited number of chances to guess the correct number.");

        System.out.println("\nPlease select the difficulty level:");
        System.out.println("1. Easy (10 chances)");
        System.out.println("2. Medium (5 chances)");
        System.out.println("3. Hard (3 chances)");
        System.out.println("4. Exit game");

        System.out.print("Enter your choice: ");
    }

    private void checkGuess(int chances, int userguess, int randomNumber, int chanceUsed, long startTime) {
        if (randomNumber == userguess) {
            long endTime = System.currentTimeMillis();
            long timeElapsed = endTime - startTime;
            double seconds = timeElapsed / 1000.0;
            System.out.println("Congratulations! You guessed the correct number in " + chanceUsed + " attempts.");
            System.out.printf("Time taken: %.2f seconds (%d ms)%n", seconds, timeElapsed);
        } else {
            giveHint(randomNumber, userguess);
            int remainingChances = chances - chanceUsed;
            if (remainingChances > 0) {
                System.out.println("You have " + remainingChances + " chances left.");
            } else {
                System.out.println("Sorry, you've run out of chances! The correct number was: " + randomNumber);
            }
        }
    }

    private void giveHint(int randomNumber, int userguess) {
        if (randomNumber > userguess) {
            System.out.println("Incorrect! The number is greater than " + userguess + ".");
        } else {
            System.out.println("Incorrect! The number is less than " + userguess + ".");
        }
    }

    private int getRandomNumber() {
        return new Random().nextInt(MAX_NUMBER) + MIN_NUMBER;
    }

    private void playGame(int chances, int randomNumber) {
        Scanner scanner = new Scanner(System.in);
        int userguess;
        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= chances; i++) {
            System.out.print("Enter your guess: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number!");
                scanner.next();
                System.out.print("Enter your guess: ");
            }
            userguess = scanner.nextInt();

            if (userguess < MIN_NUMBER || userguess > MAX_NUMBER) {
                System.out.println("Please enter a number between " + MIN_NUMBER + " and " + MAX_NUMBER);
                i--;
                continue;
            }

            checkGuess(chances, userguess, randomNumber, i, startTime);

            if (userguess == randomNumber) {
                highScore(chances, i);
                break;
            }
        }
    }

    public void highScore(int chanse, int guess) {
        if (chanse == EASY_CHANCES && easyScore > guess) {
            easyScore = guess;
            System.out.println("\nYour new prediction score : " + easyScore);
        } else if (chanse == MEDIUM_CHANCES && mediumHighScore > guess) {
            mediumHighScore = guess;
            System.out.println("\nYour new prediction score : " + mediumHighScore);
        } else if (chanse == HARD_CHANCES && hardHighScore > guess) {
            hardHighScore = guess;
            System.out.println("\nYour new prediction score : " + hardHighScore);
        }
    }

    public void gameStart() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            getInfo();

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number!");
                scanner.next();
                getInfo();
            }
            int choice = scanner.nextInt();

            int randomNumber = getRandomNumber();
            int chances = 0;

            switch (choice) {
                case 1:
                    chances = EASY_CHANCES;
                    System.out.println("Great! You have selected the Easy difficulty level.");
                    playGame(chances, randomNumber);
                    break;
                case 2:
                    chances = MEDIUM_CHANCES;
                    System.out.println("Great! You have selected the Medium difficulty level.");
                    playGame(chances, randomNumber);
                    break;
                case 3:
                    chances = HARD_CHANCES;
                    System.out.println("Great! You have selected the Hard difficulty level."+randomNumber);
                    playGame(chances, randomNumber);
                    break;
                case 4:
                    System.out.println("Thank you for playing! Goodbye.");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }

            if (!exit) {
                System.out.println("\nDo you want to play again? (Y/N)");
                String playAgain = scanner.next();
                if (playAgain.equalsIgnoreCase("N")) {
                    exit = true;
                    System.out.println("Thank you for playing! Goodbye.");
                }
            }
        }
    }
}