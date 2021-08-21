package search;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Searcher {
    private static final Map<String, List<Integer>> invertedIndexMap = new HashMap<>();

    public static final Strategy simpleSearch = (data, pattern) -> {
        int matches = 0;
        for (String line: data) {
            if (line.toLowerCase().contains(pattern)) {
                System.out.println(line);
                matches++;
            }
        }

        if (matches == 0) {
            System.out.println("No matching people found.");
        }
    };

    public static final Strategy indexMapSearch = (data, pattern) -> {
        List<Integer> indexes = invertedIndexMap.getOrDefault(pattern, List.of());
        if (indexes.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            System.out.printf("%d persons found:%n", indexes.size());
            indexes.forEach(index -> System.out.println(data.get(index)));
        }
    };

    public static void buildInvertedIndexMap(List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            String[] pieces = data.get(i).toLowerCase().split("\\s+");
            for (String key: pieces) {
                if (invertedIndexMap.containsKey(key)) {
                    invertedIndexMap.get(key).add(i);
                } else {
                    List<Integer> indexes = new ArrayList<>();
                    indexes.add(i);
                    invertedIndexMap.put(key, indexes);
                }
            }
        }
    }
}
