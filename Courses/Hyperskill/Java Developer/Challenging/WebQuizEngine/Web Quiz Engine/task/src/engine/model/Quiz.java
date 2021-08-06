package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Quiz {
    private Integer id;
    private String title;
    private String text;
    private String[] options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer answer;

    public Quiz(Integer id, String title, String text, String[] options, Integer answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public boolean solve(Integer choice) {
        if (answer == null || choice == null) return false;
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

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public static class Builder {
        private Integer id;
        private String title;
        private String text;
        private String[] options;
        private Integer answer;

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setOptions(String[] options) {
            this.options = options;
            return this;
        }

        public Builder setAnswer(Integer answer) {
            this.answer = answer;
            return this;
        }

        public Quiz build() {
            return new Quiz(id, title, text, options, answer);
        }
    }
}
