package analyzer.sp;

public class Context {
    private Strategy strategy;

    public Context () {}

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        if (strategy != null) {
            long start = System.nanoTime();
            strategy.execute();
            long finish = System.nanoTime();
            System.out.printf("It took %1.3f seconds%n", (finish - start) / 1000000.0);
        }
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
