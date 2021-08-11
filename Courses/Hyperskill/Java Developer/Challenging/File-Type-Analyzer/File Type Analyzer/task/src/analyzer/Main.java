package analyzer;

import analyzer.sp.Context;
import analyzer.sp.KmpStrategy;
import analyzer.sp.NaiveStrategy;
import analyzer.sp.Strategy;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.exit(0);
        }

        String algorithm = args[0];
        String filename = args[1];
        String pattern = args[2];
        String type = args[3];

        try {
            byte[] allBytes = Files.readAllBytes(Paths.get(filename));
            Context context = new Context();
            Strategy strategy = "--naive".equals(algorithm) ?
                    new NaiveStrategy(allBytes, pattern, type) :
                    new KmpStrategy(allBytes, pattern, type);

            context.setStrategy(strategy);
            context.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
