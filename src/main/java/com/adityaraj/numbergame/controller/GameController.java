package com.numbergame.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Random;

@RestController
@RequestMapping("/api/game")
@CrossOrigin
public class GameController {

    private int numberToGuess;
    private int attemptsLeft;
    private boolean gameOver;

    public GameController() {
        startNewGame();
    }

    @GetMapping("/start")
    public String startGame() {
        startNewGame();
        return "✅ New game started! Guess a number between 1 and 99.";
    }

    @PostMapping("/guess")
    public String submitGuess(@RequestParam int number) {
        if (gameOver) {
            return "❌ Game over! Please start a new game.";
        }

        if (number < 1 || number > 99) {
            return "⚠️ Please guess a number between 1 and 99.";
        }

        attemptsLeft--;

        if (number == numberToGuess) {
            gameOver = true;
            return "🎉 Correct! You guessed the number!";
        } else {
            String hint = generateHint(number);

            if (attemptsLeft == 0) {
                gameOver = true;
                return hint + " ❌ Game over! The correct number was " + numberToGuess + ".";
            }

            return hint + " Attempts left: " + attemptsLeft;
        }
    }

    private void startNewGame() {
        Random random = new Random();
        numberToGuess = random.nextInt(99) + 1;
        attemptsLeft = 5;
        gameOver = false;
    }

    private String generateHint(int guess) {
        int difference = Math.abs(guess - numberToGuess);
        if (guess > numberToGuess) {
            if (difference <= 10) return "🔴 You're very close! A little lower!";
            else if (difference <= 30) return "🔴 You're high!";
            else return "🔴 You're too high!";
        } else {
            if (difference <= 10) return "🔵 You're very close! A little higher!";
            else if (difference <= 30) return "🔵 You're low!";
            else return "🔵 You're too low!";
        }
    }
}
