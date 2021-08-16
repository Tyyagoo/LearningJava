package analyzer;

import java.util.Objects;

public class FilePattern implements Comparable<FilePattern> {
    private final int priority;
    private final String pattern;
    private final String type;

    public FilePattern(int priority, String pattern, String type) {
        this.priority = priority;
        this.pattern = pattern;
        this.type = type;
    }

    public FilePattern(String[] tokens) {
        this(Integer.parseInt(tokens[0]), tokens[1], tokens[2]);
    }

    @Override
    public int compareTo(FilePattern o) {
        // compare by priority
        return priority - o.getPriority();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilePattern that = (FilePattern) o;

        if (priority != that.priority) return false;
        if (!Objects.equals(pattern, that.pattern)) return false;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        int result = priority;
        result = 31 * result + (pattern != null ? pattern.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public static FilePattern fromString(String serialized) {
        return new FilePattern(serialized.replace("\"", "").split(";"));
    }

    public int getPriority() {
        return priority;
    }

    public String getPattern() {
        return pattern;
    }

    public String getType() {
        return type;
    }
}
