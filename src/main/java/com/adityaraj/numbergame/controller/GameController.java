package com.adityaraj.numbergame.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Random;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")  // Allow frontend to call
public class GameController {

    private int targetNumber;
    private int attemptCount;
    private final int maxAttempts = 5;

    public GameController() {
        generateRandomNumber();
    }

    private void generateRandomNumber() {
        Random random = new Random();
        targetNumber = random.nextInt(99) + 1; // 1 to 99
        attemptCount = 0;
    }

    @PostMapping("/guess")
    public String guessNumber(@RequestParam int number) {
        if (attemptCount >= maxAttempts) {
            int answer = targetNumber;
            generateRandomNumber();
            return "❌ Attempts over! The correct number was " + answer + ". New game started.";
        }

        attemptCount++;

        int difference = Math.abs(number - targetNumber);
        String hint = "";

        if (difference == 0) {
            String result = "🎉 Correct! You guessed the number in " + attemptCount + " attempts!";
            generateRandomNumber();
            return result;
        } else if (difference <= 10) {
            hint = (number < targetNumber) ? "You're very close! A little higher!" : "You're very close! A little lower!";
        } else if (difference <= 30) {
            hint = (number < targetNumber) ? "You're high!" : "You're low!";
        } else {
            hint = (number < targetNumber) ? "You're too low!" : "You're too high!";
        }

        if (attemptCount == maxAttempts) {
            int answer = targetNumber;
            generateRandomNumber();
            return "❌ Attempts over! The correct number was " + answer + ". New game started.";
        }

        return hint + " Attempts left: " + (maxAttempts - attemptCount);
    }

    @GetMapping("/restart")
    public String startNewGame() {
        generateRandomNumber();
        return "🔄 New game started! Guess a number between 1 and 99.";
    }
}
