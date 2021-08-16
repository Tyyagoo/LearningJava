package analyzer;

import analyzer.sp.Context;
import analyzer.sp.KmpStrategy;
import analyzer.sp.NaiveStrategy;
import analyzer.sp.Strategy;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.exit(0);
        }

        String folder = args[0];
        List<FilePattern> patterns = fetchFilePatterns(args[1]);


        Path path = Path.of(folder);
        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            List<Callable<String>> callables = createCallables(result, patterns);

            ExecutorService executorService = Executors.newFixedThreadPool(result.size());
            List<Future<String>> futures = executorService.invokeAll(callables);
            futures.forEach(f -> {
                if (f.isDone()) {
                    try {
                        System.out.println(f.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static List<FilePattern> fetchFilePatterns(String filename) {
        List<FilePattern> patterns = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(filename));
            for (String line: lines) {
                patterns.add(FilePattern.fromString(line));
            }
            Collections.sort(patterns);
            Collections.reverse(patterns);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return patterns;
    }

    public static List<Callable<String>> createCallables(List<Path> paths, List<FilePattern> patterns) {
        List<Callable<String>> callables = new ArrayList<>(paths.size());

        for (Path filepath: paths) {
            Callable<String> callable = () -> {
                try {
                    byte[] allBytes = Files.readAllBytes(filepath);
                    Context context = new Context();

                    for (FilePattern pattern: patterns) {
                        Strategy strategy = new KmpStrategy(allBytes, pattern);
                        context.setStrategy(strategy);
                        if (context.execute()) {
                            return String.format("%s: %s", filepath.getFileName(), pattern.getType());
                        }
                    }
                    return String.format("%s: Unknown file type", filepath.getFileName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return String.format("File %s doesn't exists.", filepath.getFileName());
            };

            callables.add(callable);
        }

        return callables;
    }
}
