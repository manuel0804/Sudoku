
package de.hft_stuttgart.ip1.client;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String args[]) {
        EventQueue.invokeLater(()->{
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        });
    }
}

