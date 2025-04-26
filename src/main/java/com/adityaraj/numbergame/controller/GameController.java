package com.adityaraj.numbergame.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Random;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")  // Allow frontend to communicate
public class GameController {

    private int targetNumber;
    private int attemptCount;
    private static final int MAX_ATTEMPTS = 5;

    public GameController() {
        generateRandomNumber();
    }

    private void generateRandomNumber() {
        Random random = new Random();
        targetNumber = random.nextInt(100) + 1;  // Random number between 1 and 100
        attemptCount = 0;
    }

    @PostMapping("/guess")
    public String guessNumber(@RequestParam int number) {
        if (attemptCount >= MAX_ATTEMPTS) {
            return "❌ You've exceeded maximum attempts! Please start a new game.";
        }

        attemptCount++;
        int difference = Math.abs(number - targetNumber);

        if (number == targetNumber) {
            String result = "🎉 Correct! You guessed the number in " + attemptCount + " attempts.";
            generateRandomNumber(); // Reset game after correct guess
            return result;
        } else {
            String hint;
            if (difference <= 10) {
                hint = (number < targetNumber) ? "Very close, but a little low! 🔥" : "Very close, but a little high! 🔥";
            } else if (difference <= 30) {
                hint = (number < targetNumber) ? "You are low! 📉" : "You are high! 📈";
            } else {
                hint = (number < targetNumber) ? "Too low! ⬇️" : "Too high! ⬆️";
            }
            return hint + " Attempts left: " + (MAX_ATTEMPTS - attemptCount);
        }
    }


    @GetMapping("/start")
    public String startNewGame() {
        generateRandomNumber();
        return "🆕 New game started! Guess a number between 1 and 100.";
    }
}
