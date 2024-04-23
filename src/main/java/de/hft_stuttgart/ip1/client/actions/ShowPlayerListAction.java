package de.hft_stuttgart.ip1.client.actions;

import de.hft_stuttgart.ip1.client.MainFrame;
import de.hft_stuttgart.ip1.common.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.List;

public class ShowPlayerListAction extends AbstractAction {
    private final MainFrame mainFrame;

    public ShowPlayerListAction(MainFrame mainFrame) {
        super("Online Spieler anzeigen");
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            var server = mainFrame.getServerConnection();
            var session = mainFrame.getSession();
            if (server == null) {
                return;
            }
            List<String> playerList = server.getPlayerList();
            mainFrame.showPlayerList(playerList, false);
            if (session == null) {
                return;
            }
            List<String> sessionPlayerList = session.getPlayerList();
            mainFrame.showPlayerList(sessionPlayerList, true);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
