package engine.controller;

import engine.model.AttemptWrapper;
import engine.model.Quiz;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
public class QuizController {

    @Autowired
    QuizService quizService;

    @GetMapping("/api/quizzes")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return new ResponseEntity<>(quizService.getAllQuizzes(), HttpStatus.OK);
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable @Min(1) Integer id) {
        Optional<Quiz> quiz = quizService.getQuizById(id);
        return quiz.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/api/quizzes")
    public ResponseEntity<Quiz> createQuiz(@Valid @RequestBody Quiz quiz) {
        Quiz newbyQuiz = quizService.save(quiz);
        return new ResponseEntity<>(newbyQuiz, HttpStatus.OK);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<?> solveQuiz(@PathVariable @Min(1) Integer id, @Valid @RequestBody AttemptWrapper attempt) {
        Optional<Quiz> quiz = quizService.getQuizById(id);
        return quiz.map(value -> {
            boolean result = value.solve(attempt.getAnswer());
            String feedback = result ? "Congratulations, you're right!" : "Wrong answer! Please, try again.";
            return new ResponseEntity<>(Map.of("success" , result, "feedback", feedback), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
