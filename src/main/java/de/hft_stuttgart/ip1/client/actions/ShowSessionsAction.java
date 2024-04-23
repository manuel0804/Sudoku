package de.hft_stuttgart.ip1.client.actions;

import de.hft_stuttgart.ip1.client.MainFrame;
import de.hft_stuttgart.ip1.common.Session;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.List;

public class ShowSessionsAction extends AbstractAction {
    private final MainFrame mainFrame;

    public ShowSessionsAction(MainFrame mainFrame) {
        super("Offene Spiele anzeigen");
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            List<String> sessions = mainFrame.getServerConnection().getSessionList();
            JOptionPane.showMessageDialog(mainFrame, sessions);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
