package de.hft_stuttgart.ip1.client.gui;

import de.hft_stuttgart.ip1.client.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlayerListPanel extends JPanel {

    private final JPanel parent;
    private JList<String> serverPlayerJList;
    private JList<String> lobbyPlayerJList;
    private final Font PLAYER_FONT = new Font("Arial", Font.BOLD, 24);
    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 30);
    private JButton backButton;
    private JLabel serverLabel, lobbyLabel;
    private MainFrame mainFrame;

    public PlayerListPanel(JPanel upperPane,MainFrame mainFrame) {
        this.parent = upperPane;
        this.setLayout(new GridBagLayout());
        setupUI();
        this.mainFrame = mainFrame;
    }

    private void setupUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        //gbc.fill = GridBagConstraints.NONE;  // Unverändert
        gbc.anchor = GridBagConstraints.NORTHEAST;  // Oben rechts ausrichten

        backButton = new JButton("Zurück");
        backButton.addActionListener(e -> {
            // Aktion für Zurück-Button
            this.setVisible(false);
            mainFrame.getSudokuPanel().setVisible(true);
        });
        this.add(backButton, gbc);

        serverLabel = new JLabel("Server");
        serverLabel.setFont(TITLE_FONT);
        gbc.gridy++;
        this.add(serverLabel, gbc);

        serverPlayerJList = new JList<>();
        serverPlayerJList.setFont(PLAYER_FONT);
        JScrollPane serverScrollPane = new JScrollPane(serverPlayerJList);
        gbc.gridy++;
        gbc.weighty = 0.4;
        this.add(serverScrollPane, gbc);

        lobbyLabel = new JLabel("Lobby");
        lobbyLabel.setFont(TITLE_FONT);
        gbc.gridy++;
        gbc.weighty = 0.1;
        this.add(lobbyLabel, gbc);

        lobbyPlayerJList = new JList<>();
        lobbyPlayerJList.setFont(PLAYER_FONT);
        JScrollPane lobbyScrollPane = new JScrollPane(lobbyPlayerJList);
        gbc.gridy++;
        gbc.weighty = 0.4;
        this.add(lobbyScrollPane, gbc);
    }

    public void setPlayerList(List<String> playerList) {
        serverPlayerJList.setListData(playerList.toArray(new String[0]));

    }

    public void setLobbyPlayerList(List<String> lobbyPlayerList) {
        lobbyPlayerJList.setListData(lobbyPlayerList.toArray(new String[0]));
        var componentList = this.getComponents();
        for (int i = 0; i < componentList.length; i++) {
            if(componentList[i].getName().equalsIgnoreCase("lobbyPlayerJList")){
                componentList[i] = new JScrollPane(lobbyPlayerJList);
            }
        }
    }

    public void setDarkMode(boolean darkMode) {
        Color backgroundColor = darkMode ? Color.DARK_GRAY : Color.WHITE;
        Color textColor = darkMode ? Color.WHITE : Color.DARK_GRAY;

        this.setBackground(backgroundColor);
        backButton.setForeground(textColor);
        backButton.setBackground(backgroundColor);
        serverLabel.setForeground(textColor);
        serverPlayerJList.setForeground(textColor);
        serverPlayerJList.setBackground(backgroundColor);
        lobbyLabel.setForeground(textColor);
        lobbyPlayerJList.setForeground(textColor);
        lobbyPlayerJList.setBackground(backgroundColor);
    }
}
