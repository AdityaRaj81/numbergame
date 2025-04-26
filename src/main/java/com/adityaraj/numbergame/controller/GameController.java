package com.adityaraj.numbergame.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Random;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")  // VERY IMPORTANT for frontend communication
public class GameController {

    private int targetNumber;
    private int attemptCount;

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
        attemptCount++;
        if (number == targetNumber) {
            String result = "🎉 Correct! You guessed the number in " + attemptCount + " attempts.";
            generateRandomNumber(); // Reset game after correct guess
            return result;
        } else if (number < targetNumber) {
            return "Too low! Try a higher number.";
        } else {
            return "Too high! Try a lower number.";
        }
    }

    @GetMapping("/start")
    public String startNewGame() {
        generateRandomNumber();
        return "New game started! Guess a number between 1 and 100.";
    }
}
