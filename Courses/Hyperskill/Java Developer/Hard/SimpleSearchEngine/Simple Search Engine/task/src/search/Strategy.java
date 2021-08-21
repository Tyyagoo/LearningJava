package search;

import java.util.List;

public interface Strategy {
    List<String> find(List<String> data, String pattern);
}
