package de.hft_stuttgart.ip1.client.actions;

import de.hft_stuttgart.ip1.client.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class JoinGameAction extends AbstractAction {

    private final MainFrame mainFrame;

    public JoinGameAction(MainFrame mainFrame) {
        super("Spiel beitreten");
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String sessionName = JOptionPane.showInputDialog(mainFrame, "Spielname eingeben");
        if (sessionName != null) {
            try {
                var session = mainFrame.getServerConnection().joinSession(sessionName, mainFrame.getPlayer());
                mainFrame.joinGame(session);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, ex.getMessage());
            }
        }

    }
}
