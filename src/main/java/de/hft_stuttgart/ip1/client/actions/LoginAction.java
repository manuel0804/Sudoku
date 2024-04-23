package de.hft_stuttgart.ip1.client.actions;

import de.hft_stuttgart.ip1.client.MainFrame;
import de.hft_stuttgart.ip1.common.Server;
import de.hft_stuttgart.ip1.common.Session;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoginAction extends AbstractAction {
    private final MainFrame mainFrame;

    public LoginAction(MainFrame mainFrame) {
        super("Login");
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry("localhost", 28725);
            Server serverConnection = (Server) registry.lookup("Sudoku");
            if(serverConnection == null) {
                JOptionPane.showMessageDialog(mainFrame, "Server nicht gefunden");
            }
            else {
                mainFrame.setServerConnection(serverConnection);
            }
        } catch (RemoteException | NotBoundException e) {
            JOptionPane.showMessageDialog(mainFrame, "Server nicht gefunden");
        }

    }
}
