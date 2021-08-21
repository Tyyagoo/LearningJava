package search;

import java.util.List;

public interface Strategy {
    void find(List<String> data, String pattern);
}
