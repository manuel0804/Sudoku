package de.hft_stuttgart.ip1.client.actions;

import de.hft_stuttgart.ip1.client.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

public class CreateGameAction  extends AbstractAction {

    private final MainFrame mainFrame;

    public CreateGameAction(MainFrame mainFrame) {
        super("Spiel erstellen");
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var gameName = JOptionPane.showInputDialog(mainFrame, "Spielname:");
        var boardSize = JOptionPane.showInputDialog(mainFrame, "Spielfeldgröße: (9, 16, 25)");
        var playerCount = JOptionPane.showInputDialog(mainFrame, "Spieleranzahl:");
        var server = mainFrame.getServerConnection();
        try {
            var session = server.newSession(gameName, Integer.parseInt(boardSize), Integer.parseInt(playerCount));
            mainFrame.createGame(session);

        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }
}
