package life;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        final Runnable swingApp = GameOfLife::new;
        Thread appThread = new Thread(() -> {
            try {
                SwingUtilities.invokeAndWait(swingApp);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Finished on " + Thread.currentThread());
        });
        appThread.start();
    }
}
