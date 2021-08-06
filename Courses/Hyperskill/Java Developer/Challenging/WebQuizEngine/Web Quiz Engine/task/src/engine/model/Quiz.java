package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collections;
import java.util.List;

@Entity(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @NotBlank
    private String title;

    @Column
    @NotBlank
    private String text;

    @ElementCollection
    @OrderColumn
    @NotNull
    @Size(min = 2)
    private String[] options;

    @ElementCollection
    @OrderColumn
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer = List.of();

    protected Quiz() {}

    public Quiz(Integer id, String title, String text, String[] options, List<Integer> answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public boolean solve(List<Integer> choice) {
        return answer.equals(choice);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        if (answer == null) {
            answer = List.of();
        } else {
            Collections.sort(answer);
            this.answer = answer;
        }
    }
}
