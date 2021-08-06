package engine.controller;

import engine.model.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class QuizController {

    private Quiz quiz = new Quiz("The Java Logo",
            "What is depicted on the Java logo?",
            new String[]{"Robot", "Tea leaf", "Cup of coffee", "Bug"});

    @GetMapping("/api/quiz")
    public Quiz getQuiz() {
        return quiz;
    }

    @PostMapping("/api/quiz")
    public ResponseEntity<Map<String, Object>> answerQuiz(@RequestParam String answer) {
        if ("2".equals(answer)) {
            return new ResponseEntity<>(Map.of("success", true,
                    "feedback", "Congratulations, you're right!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("success", false,
                    "feedback", "Wrong answer! Please, try again."), HttpStatus.OK);
        }
    }
}
