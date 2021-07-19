package life;

import life.model.Generation;
import life.model.Universe;

import javax.swing.*;
import java.awt.*;

public class JGamePanel extends JPanel {

    Generation gameGen;

    JGamePanel() {
        setName("GamePanel");
        setBounds(5, 35, 500, 500);
    }

    public void changeGeneration(Generation u) {
        gameGen = u;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameGen == null) return;

        Color aliveColor = new Color(30, 30, 30);
        Color deadColor = new Color(230, 230, 230);
        int rectX = getWidth() / gameGen.getSize();
        int rectY = getHeight() / gameGen.getSize();

        g.setColor(new Color(0,0,0));
        for (int i = 0; i < gameGen.getSize(); i++) {
            for (int j = 0; j < gameGen.getSize(); j++) {
                g.drawRect(rectX * j, rectY * i, rectX, rectY);
            }
        }

        for (int i = 0; i < gameGen.getSize(); i++) {
            for (int j = 0; j < gameGen.getSize(); j++) {
                Color color = (gameGen.getUniverse().getCellAt(i, j) == Universe.Cell.ALIVE) ? aliveColor : deadColor;
                g.setColor(color);
                g.fillRect(rectX * j + 1, rectY * i + 1, rectX-1, rectY-1);
            }
        }

    }
}
