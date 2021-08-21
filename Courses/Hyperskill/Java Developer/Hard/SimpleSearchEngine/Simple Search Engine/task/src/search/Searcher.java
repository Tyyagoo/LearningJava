package search;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Searcher {
    private static final Map<String, List<Integer>> invertedIndexMap = new HashMap<>();

    public static final Strategy simpleSearch = (data, pattern) -> {
        List<String> list = new ArrayList<>();
        for (String line: data) {
            if (line.toLowerCase().contains(pattern)) {
                list.add(line);
            }
        }
        return list;
    };

    public static final Strategy indexMapSearch = (data, pattern) -> {
        List<Integer> indexes = invertedIndexMap.getOrDefault(pattern, List.of());
        return indexes.stream().map(data::get).collect(Collectors.toList());
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
