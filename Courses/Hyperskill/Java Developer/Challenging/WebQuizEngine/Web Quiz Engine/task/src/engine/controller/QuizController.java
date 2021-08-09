package engine.controller;

import engine.model.AttemptWrapper;
import engine.model.Quiz;
import engine.model.User;
import engine.service.QuizService;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.*;

@RestController
@Validated
public class QuizController {

    @Autowired
    QuizService quizService;

    @Autowired
    UserService userService;

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
        Optional<User> owner = userService.retrieveUserByEmail(getUserEmailByContext());

        return owner.map(
                value -> {
                    quiz.setOwner(value);
                    Optional<Set<Quiz>> currentQuizzes = Optional.ofNullable(value.getQuizzes());
                    Set<Quiz> quizzes = currentQuizzes.map(
                        oldQuizzes -> {
                            Set<Quiz> quizSet = new HashSet<>();
                            quizSet.add(quiz);
                            quizSet.addAll(oldQuizzes);
                            return quizSet;
                        }
                    ).orElse(Set.of(quiz));
                    Quiz newbyQuiz = quizService.save(quiz);
                    value.setQuizzes(quizzes);
                    userService.saveUser(value);
                    return new ResponseEntity<>(newbyQuiz, HttpStatus.OK);
                }
        ).orElse(new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable @Min(1) Integer id) {
        Optional<User> owner = userService.retrieveUserByEmail(getUserEmailByContext());
        Optional<Quiz> quiz = quizService.getQuizById(id);

        return quiz.map(
            q -> owner.map(
                    o -> {
                        if (o.getId() == q.getOwner().getId()) {
                            quizService.deleteQuizById(q.getId());
                            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
                        } else {
                            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
                        }
                    }
            ).orElse(new ResponseEntity<>(null, HttpStatus.BAD_REQUEST))
        ).orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

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

    private String getUserEmailByContext() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }
}
