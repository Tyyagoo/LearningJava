package engine.model;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

public class AttemptWrapper {

    @NotNull
    private List<Integer> answer;

    public AttemptWrapper() {}

    public AttemptWrapper(List<Integer> answer) {
        this.answer = answer;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        Collections.sort(answer);
        this.answer = answer;
    }
}
