import java.util.*;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        LoadBalancer loadBalancer = new LoadBalancer();
        int numberOfTasks = scanner.nextInt();
        for (int i = 0; i < numberOfTasks; i++) {
            Task task = new Task(scanner.nextInt(), scanner.nextInt());
            loadBalancer.offer(task);
        }
        loadBalancer.showQueues();
    }
}

class LoadBalancer {

    private Deque<Task> firstQueue = new ArrayDeque<>();
    private Deque<Task> secondQueue = new ArrayDeque<>();
    private int firstQueueLoad = 0;
    private int secondQueueLoad = 0;


    public void offer(Task task) {
        if (firstQueueLoad > secondQueueLoad) {
            secondQueue.offerLast(task);
            secondQueueLoad += task.getLoad();
        } else {
            firstQueue.offerLast(task);
            firstQueueLoad += task.getLoad();
        }
    }

    public void showQueues() {
        firstQueue.forEach((n) -> System.out.printf("%d ", n.getIdentifier()));
        System.out.println();
        secondQueue.forEach((n) -> System.out.printf("%d ", n.getIdentifier()));
        System.out.println();
    }
}

class Task {
    private final int identifier;
    private final int load;

    public Task(int id, int l) {
        this.identifier = id;
        this.load = l;
    }

    public int getIdentifier() {
        return identifier;
    }

    public int getLoad() {
        return load;
    }
}