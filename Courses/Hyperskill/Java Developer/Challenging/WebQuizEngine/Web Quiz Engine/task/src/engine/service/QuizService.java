package engine.service;

import engine.model.Quiz;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getAllQuizzes() {
        return StreamSupport
                .stream(quizRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Quiz> getQuizById(Integer id) {
        return quizRepository.findById(id);
    }

    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }
}
