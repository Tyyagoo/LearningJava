package analyzer.sp;

public class Context {
    private Strategy strategy;

    public Context () {}

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public String execute() {
        return (strategy != null) ? strategy.execute() : "";
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
