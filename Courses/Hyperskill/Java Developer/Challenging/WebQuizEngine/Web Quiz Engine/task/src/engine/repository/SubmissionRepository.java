package engine.repository;

import engine.model.Submission;
import engine.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SubmissionRepository extends PagingAndSortingRepository<Submission, Integer> {
    Page<Submission> findAllByUser(User user, Pageable pageable);
}
