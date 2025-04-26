package com.aditya.numbergame;

import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameController {

    private int target;
    private int attemptsLeft;
    private static final int MAX_ATTEMPTS = 5;
    private boolean gameOver;

    public GameController() {
        reset();
    }

    private void reset() {
        this.target = new Random().nextInt(99) + 1;
        this.attemptsLeft = MAX_ATTEMPTS;
        this.gameOver = false;
    }

    @GetMapping("/start")
    public String start() {
        reset();
        return "✅ New game started! Guess a number between 1 and 99.";
    }

    @PostMapping("/guess")
    public String guess(@RequestParam int number) {
        if (gameOver) {
            return "❌ Game over! Please /start a new game.";
        }
        if (number < 1 || number > 99) {
            return "⚠️ Number must be between 1 and 99.";
        }
        attemptsLeft--;

        if (number == target) {
            gameOver = true;
            return "🎉 Correct! You got it in " + (MAX_ATTEMPTS - attemptsLeft) + " attempts.";
        }
        String hint;
        int diff = Math.abs(number - target);
        if (diff <= 10) {
            hint = number > target ? "🔴 Very close! A little lower." : "🔵 Very close! A little higher.";
        } else if (diff <= 30) {
            hint = number > target ? "🔴 A bit high." : "🔵 A bit low.";
        } else {
            hint = number > target ? "🔴 Too high." : "🔵 Too low.";
        }

        if (attemptsLeft == 0) {
            gameOver = true;
            return hint + " ❌ Game over! The number was " + target + ".";
        }
        return hint + " (Attempts left: " + attemptsLeft + ")";
    }
}
