import java.util.*;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    int inputLength = sc.nextInt();

    for(int c=0;c<inputLength;c++){
      Task newTask = new Task(sc.nextInt(), sc.nextInt());
      LoadBalancer.addTask(newTask);
    }

    LoadBalancer.showPrimaryDeque();
    LoadBalancer.showSecondaryDeque();

  }
}


class LoadBalancer{

  static private final Deque<Task> primaryDeque = new ArrayDeque<>();
  static private final Deque<Task> secondaryDeque = new ArrayDeque<>();

  static public void addTask(Task task){
    if(calculateDequeLoad(primaryDeque) > calculateDequeLoad(secondaryDeque)) {
      secondaryDeque.add(task);
      return;
    }
    primaryDeque.add(task);
  }

  static private int calculateDequeLoad(Deque<Task> deque){
    int load = 0;
    for(Task t: deque){
      load += t.getLoadIndicator();
    }
    return load;
  }

  static private void showDeque(Deque<Task> deque){
    for(Task t: deque){
      System.out.print(t.getId() + " ");
    }
    System.out.println();
  }

  static public void showPrimaryDeque(){
    showDeque(primaryDeque);
  }

  static public void showSecondaryDeque(){
    showDeque(secondaryDeque);
  }

}


class Task{

  private final int id;
  private final int loadIndicator;

  Task(int id, int loadIndicator){
    this.id = id;
    this.loadIndicator = loadIndicator;
  }

  public int getId() {
    return id;
  }

  public int getLoadIndicator() {
    return loadIndicator;
  }
}