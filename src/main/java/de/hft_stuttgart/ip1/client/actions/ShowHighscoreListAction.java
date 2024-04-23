package de.hft_stuttgart.ip1.client.actions;

import de.hft_stuttgart.ip1.client.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

public class ShowHighscoreListAction extends AbstractAction {
    private final MainFrame mainFrame;

    public ShowHighscoreListAction(MainFrame mainFrame) {
        super("Highscore anzeigen");
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        var gameSize = -1;
        var playerCount = -1;
        gameSize = Integer.parseInt(JOptionPane.showInputDialog("Spielgröße"));
        playerCount = Integer.parseInt(JOptionPane.showInputDialog("Spieleranzahl"));
        try {
            var highscore = mainFrame.getServerConnection().getHighScore(gameSize, playerCount);
            mainFrame.showHighscore(highscore);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
