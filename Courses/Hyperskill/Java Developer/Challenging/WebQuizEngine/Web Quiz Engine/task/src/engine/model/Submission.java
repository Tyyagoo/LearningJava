package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Submission implements Comparable<Submission> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @DateTimeFormat
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name="quiz_id", nullable=false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Submission() {}

    public Submission(Quiz quiz, User user) {
        this.quiz = quiz;
        this.user = user;
        this.completedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public int compareTo(Submission o) {
        return completedAt.compareTo(o.getCompletedAt());
    }

    public static class SubmissionDTO implements Comparable<SubmissionDTO> {

        @JsonProperty(value = "completedAt", required = true)
        private final LocalDateTime completedAt;

        @JsonProperty(value = "id", required = true)
        private final Integer quizId;

        private SubmissionDTO(LocalDateTime completedAt, Quiz quiz) {
            this.completedAt = completedAt;
            this.quizId = quiz.getId();
        }

        public LocalDateTime getCompletedAt() {
            return completedAt;
        }

        public Integer getQuizId() {
            return quizId;
        }

        public static SubmissionDTO getFrom(Submission submission) {
            return new SubmissionDTO(submission.getCompletedAt(), submission.getQuiz());
        }

        @Override
        public int compareTo(SubmissionDTO o) {
            return completedAt.compareTo(o.getCompletedAt());
        }
    }
}
