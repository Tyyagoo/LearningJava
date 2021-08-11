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
        if (args.length < 3) {
            System.exit(0);
        }

        String folder = args[0];
        String pattern = args[1];
        String type = args[2];

        Path path = Path.of(folder);
        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            ExecutorService executorService = Executors.newFixedThreadPool(result.size());
            List<Callable<String>> callables = new ArrayList<>(result.size());

            for (Path filepath: result) {
                Callable<String> callable = () -> {
                    try {
                        byte[] allBytes = Files.readAllBytes(filepath);
                        Context context = new Context();
                        Strategy strategy = new KmpStrategy(allBytes, pattern, type);
                        context.setStrategy(strategy);
                        return String.format("%s: %s", filepath.getFileName(), context.execute());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return String.format("File %s doesn't exists.", filepath.getFileName());
                };

                callables.add(callable);
            }

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
}
