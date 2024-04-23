package de.hft_stuttgart.ip1.client.actions;

import de.hft_stuttgart.ip1.client.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StartGameAction extends AbstractAction {

    private final MainFrame mainFrame;
    public StartGameAction(MainFrame mainFrame) {
        super("Spiel starten");
        this.mainFrame = mainFrame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            var success = mainFrame.getSession().startGame();
            if (!success) {
                JOptionPane.showMessageDialog(mainFrame, "Spiel konnte nicht gestartet werden");
                return;
            }
            mainFrame.startGame();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainFrame, ex.getMessage());
        }
    }
}
