package de.hft_stuttgart.ip1.client.actions;

import de.hft_stuttgart.ip1.client.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ToggleModeAction extends AbstractAction {
    private final MainFrame mainFrame;

    public ToggleModeAction(MainFrame mainFrame) {
        super("Darkmode aktivieren/deaktivieren");
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        mainFrame.changeDarkModeStatus();
    }
}
