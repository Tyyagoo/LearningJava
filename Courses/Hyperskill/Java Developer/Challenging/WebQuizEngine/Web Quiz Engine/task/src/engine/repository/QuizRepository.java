package engine.repository;

import engine.model.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizRepository extends PagingAndSortingRepository<Quiz, Integer> { }
