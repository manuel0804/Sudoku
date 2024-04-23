package de.hft_stuttgart.ip1.client.gui;

import de.hft_stuttgart.ip1.client.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class HighScorePanel extends JPanel {
    private final MainFrame mainFrame;
    List<String> Highscores = new ArrayList<String>();
    public void setHighscore(List<String> highscore) {
        this.Highscores = highscore;
    }

    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.drawString("Highscore", 10, 10);
        int y = 30;
        for (String highscore : Highscores) {
            g.drawString(highscore, 10, y);
            y += 20;
        }
    }
    public HighScorePanel(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        JButton button = new JButton("Zurück");
        button.setBounds(getWidth() - 100, 10, 90, 30);
        add(button);
        button.addActionListener(e -> {this.setVisible(false);
            mainFrame.getSudokuPanel().setVisible(true);});
    }
}
