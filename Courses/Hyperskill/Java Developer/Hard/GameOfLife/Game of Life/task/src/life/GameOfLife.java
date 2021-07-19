package life;

import life.model.Generation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class GameOfLife extends JFrame {

    private JLabel generationLabel = new JLabel("Generation #0");
    private JLabel aliveLabel = new JLabel("Alive: 0");
    private JButton resetButton = new JButton("Reset");
    private JToggleButton pauseButton = new JToggleButton("Play/Pause");
    private JGamePanel gamePanel;

    Generation gameGen = Generation.getInitialGeneration(50);

    public GameOfLife() {
        setTitle("Game Of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

        generationLabel.setName("GenerationLabel");
        generationLabel.setBounds(5, 5, 100, 10);
        add(generationLabel);

        aliveLabel.setName("AliveLabel");
        aliveLabel.setBounds(5, 20, 100, 10);
        add(aliveLabel);

        pauseButton.setName("PlayToggleButton");
        pauseButton.setBounds(100, 5, 100, 25);
        add(pauseButton);

        ActionListener reset = (event) -> {
            changeGeneration(Generation.getInitialGeneration(gameGen.getSize()));
            setVisible(true);
        };

        resetButton.setName("ResetButton");
        resetButton.setBounds(250, 5, 100, 25);
        resetButton.addActionListener(reset);
        add(resetButton);

        gamePanel = new JGamePanel();
        gamePanel.changeGeneration(gameGen);
        add(gamePanel);

        ActionListener refresh = e -> {
            if (!pauseButton.isSelected()) return;
            changeGeneration(gameGen.getNextGeneration());
            setVisible(true);
        };


        int UPDATE_SPEED = 100;
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
