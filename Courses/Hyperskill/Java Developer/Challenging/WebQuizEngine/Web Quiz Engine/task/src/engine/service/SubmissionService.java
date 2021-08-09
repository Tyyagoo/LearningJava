package engine.service;

import engine.model.Submission;
import engine.model.Submission.SubmissionDTO;
import engine.model.User;
import engine.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubmissionService {

    @Autowired
    UserService userService;

    @Autowired
    QuizService quizService;

    @Autowired
    SubmissionRepository submissionRepository;

    private Page<Submission> getAllSubmissionsFromUser(User user, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        return submissionRepository.findAllByUser(user, paging);
    }

    public Page<SubmissionDTO> getAllSubmissionsFromEmail(String email, Integer pageNo, Integer pageSize, String sortBy) {
        Optional<User> user = userService.retrieveUserByEmail(email);
        return user.map(value -> getAllSubmissionsFromUser(value, pageNo, pageSize, sortBy)
                .map(SubmissionDTO::getFrom)).orElse(Page.empty());
    }

    public Submission saveSubmission(Submission submission) {
        return submissionRepository.save(submission);
    }
}
