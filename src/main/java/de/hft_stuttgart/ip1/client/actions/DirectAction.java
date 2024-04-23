package de.hft_stuttgart.ip1.client.actions;

import de.hft_stuttgart.ip1.client.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DirectAction extends AbstractAction {
    private final MainFrame mainFrame;

    public DirectAction(MainFrame mainFrame) {
        super("Direkt!");
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(mainFrame, "Direct");
    }
}
