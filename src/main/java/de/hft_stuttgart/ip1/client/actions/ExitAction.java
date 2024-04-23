package de.hft_stuttgart.ip1.client.actions;

import de.hft_stuttgart.ip1.client.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExitAction extends AbstractAction {
    private final MainFrame mainFrame;

    public ExitAction(MainFrame mainFrame) {
        super("Beenden");
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(mainFrame, "Exit");
        mainFrame.setVisible(false);
        mainFrame.dispose();
        System.exit(0);
    }
}
