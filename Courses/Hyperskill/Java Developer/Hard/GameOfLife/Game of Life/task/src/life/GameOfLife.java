package life;

import life.model.Generation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameOfLife extends JFrame {

    private JLabel generationLabel = new JLabel("Generation #0");
    private JLabel aliveLabel = new JLabel("Alive: 0");
    private JGamePanel gamePanel;

    Generation gameGen = Generation.getInitialGeneration(20);

    public GameOfLife() {
        setTitle("Game Of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

        generationLabel.setName("GenerationLabel");
        generationLabel.setBounds(5, 5, 150, 10);
        add(generationLabel);

        aliveLabel.setName("AliveLabel");
        aliveLabel.setBounds(5, 20, 150, 10);
        add(aliveLabel);


        gamePanel = new JGamePanel();
        gamePanel.changeGeneration(gameGen);
        add(gamePanel);

        ActionListener refresh = e -> {
            changeGeneration(gameGen.getNextGeneration());
            setVisible(true);
        };
        int UPDATE_SPEED = 700;
        new Timer(UPDATE_SPEED, refresh).start();
    }

    public void changeGeneration(Generation u) {
        gameGen = u;
        gamePanel.changeGeneration(u);
        generationLabel.setText(String.format("Generation #%d", gameGen.getId()));
        aliveLabel.setText(String.format("Alive: %d", gameGen.getPopulation()));
        repaint();
    }

}
