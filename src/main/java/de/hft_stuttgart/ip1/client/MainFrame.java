package de.hft_stuttgart.ip1.client;

import de.hft_stuttgart.ip1.client.actions.*;
import de.hft_stuttgart.ip1.client.gui.HighScorePanel;
import de.hft_stuttgart.ip1.client.gui.PlayerListPanel;
import de.hft_stuttgart.ip1.client.gui.SudokuPanel;
import de.hft_stuttgart.ip1.common.Player;
import de.hft_stuttgart.ip1.common.Server;
import de.hft_stuttgart.ip1.common.Session;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainFrame extends JFrame {
    private JPanel contentPane;
    private JButton aAButton;
    private JButton aBButton;
    private JButton aCButton;
    private JButton aDButton;
    private JButton aEButton;
    private JButton aFButton;
    private JButton aGButton;
    private JButton aHButton;
    private JButton aIButton;
    private JButton aJButton;
    private JButton aKButton;
    private JButton aLButton;
    private JButton aMButton;
    private JButton aNButton;
    private JButton aOButton;
    private JButton aPButton;
    private JButton aQButton;
    private JButton aRButton;
    private JButton aSButton;
    private JButton aTButton;
    private JButton aUButton;
    private JButton aVButton;
    private JButton aWButton;
    private JButton aXButton;
    private JButton aYButton;
    private JPanel sudokuPanel;
    private JPanel playerListPanel;
    private JPanel upperPane;
    private JPanel lowerPane;
    private JPanel highScorePanel;


    private Server serverConnection;
    private Player player;

    private Session session;

    private boolean loggedIn = false;



    private boolean darkMode = false;

    private ScheduledExecutorService scheduler;


    public MainFrame() {
        $$$setupUI$$$();
        setContentPane($$$getRootComponent$$$());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu serverMenu = new JMenu("Server");
        menuBar.add(serverMenu);
        serverMenu.add(new LoginAction(MainFrame.this));
        serverMenu.add(new LogoutAction(MainFrame.this));
        serverMenu.add(new ShowPlayerListAction(MainFrame.this));
        serverMenu.add(new ShowSessionsAction(MainFrame.this));
        serverMenu.add(new ShowHighscoreListAction(MainFrame.this));
        JMenu gameMenu = new JMenu("Spiel");
        gameMenu.add(new CreateGameAction(MainFrame.this));
        gameMenu.add(new JoinGameAction(MainFrame.this));
        menuBar.add(gameMenu);
        JMenu lobby = new JMenu("Lobby");
        lobby.add(new StartGameAction(MainFrame.this));
        menuBar.add(lobby);
        JMenu settingsMenu = new JMenu("Einstellungen");
        settingsMenu.add(new ToggleModeAction(MainFrame.this));
        menuBar.add(settingsMenu);

        aAButton.setAction(new ButtonAction('A'));
        aBButton.setAction(new ButtonAction('B'));
        aCButton.setAction(new ButtonAction('C'));
        aDButton.setAction(new ButtonAction('D'));
        aEButton.setAction(new ButtonAction('E'));
        aFButton.setAction(new ButtonAction('F'));
        aGButton.setAction(new ButtonAction('G'));
        aHButton.setAction(new ButtonAction('H'));
        aIButton.setAction(new ButtonAction('I'));
        aJButton.setAction(new ButtonAction('J'));
        aKButton.setAction(new ButtonAction('K'));
        aLButton.setAction(new ButtonAction('L'));
        aMButton.setAction(new ButtonAction('M'));
        aNButton.setAction(new ButtonAction('N'));
        aOButton.setAction(new ButtonAction('O'));
        aPButton.setAction(new ButtonAction('P'));
        aQButton.setAction(new ButtonAction('Q'));
        aRButton.setAction(new ButtonAction('R'));
        aSButton.setAction(new ButtonAction('S'));
        aTButton.setAction(new ButtonAction('T'));
        aUButton.setAction(new ButtonAction('U'));
        aVButton.setAction(new ButtonAction('V'));
        aWButton.setAction(new ButtonAction('W'));
        aXButton.setAction(new ButtonAction('X'));
        aYButton.setAction(new ButtonAction('Y'));


        aAButton.setVisible(false);
        aBButton.setVisible(false);
        aCButton.setVisible(false);
        aDButton.setVisible(false);
        aEButton.setVisible(false);
        aFButton.setVisible(false);
        aGButton.setVisible(false);
        aHButton.setVisible(false);
        aIButton.setVisible(false);
        aJButton.setVisible(false);
        aKButton.setVisible(false);
        aLButton.setVisible(false);
        aMButton.setVisible(false);
        aNButton.setVisible(false);
        aOButton.setVisible(false);
        aPButton.setVisible(false);
        aQButton.setVisible(false);
        aRButton.setVisible(false);
        aSButton.setVisible(false);
        aTButton.setVisible(false);
        aUButton.setVisible(false);
        aVButton.setVisible(false);
        aWButton.setVisible(false);
        aXButton.setVisible(false);
        aYButton.setVisible(false);

        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (session == null || serverConnection == null || !session.getGameStarted()) {
                    return;
                }
                session = session.getSession();
                serverConnection.updateSession(session);
                if(session.isFinished()){
                    JOptionPane.showMessageDialog(null,"Game is finished\n"+session.getPlayerScore());
                }
                var activePlayerUsername = session.getActivePlayer().getUsername();
                var board = session.getBoard();
                var guesses = session.getGuesses();
                ((SudokuPanel) sudokuPanel).setPlayerCount(session.getPlayerList().size());
                ((SudokuPanel) sudokuPanel).updateGuesses(guesses);
                ((SudokuPanel) sudokuPanel).updateBoard(board);
                ((SudokuPanel) sudokuPanel).setActivePlayer(activePlayerUsername, player.getUsername());
                ((SudokuPanel) sudokuPanel).showScore(player.getScore());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setMinimumSize(new Dimension(480, 320));
        contentPane.setPreferredSize(new Dimension(480, 320));
        upperPane = new JPanel();
        upperPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(upperPane, BorderLayout.CENTER);
        upperPane.add(sudokuPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        sudokuPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        lowerPane = new JPanel();
        lowerPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 10, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(lowerPane, BorderLayout.SOUTH);
        aAButton = new JButton();
        aAButton.setText("A");
        lowerPane.add(aAButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        lowerPane.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        aBButton = new JButton();
        aBButton.setText("B");
        lowerPane.add(aBButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aCButton = new JButton();
        aCButton.setText("C");
        lowerPane.add(aCButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aDButton = new JButton();
        aDButton.setText("D");
        lowerPane.add(aDButton, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aEButton = new JButton();
        aEButton.setText("E");
        lowerPane.add(aEButton, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aFButton = new JButton();
        aFButton.setText("F");
        lowerPane.add(aFButton, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aGButton = new JButton();
        aGButton.setText("G");
        lowerPane.add(aGButton, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aHButton = new JButton();
        aHButton.setText("H");
        lowerPane.add(aHButton, new com.intellij.uiDesigner.core.GridConstraints(0, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aIButton = new JButton();
        aIButton.setText("I");
        lowerPane.add(aIButton, new com.intellij.uiDesigner.core.GridConstraints(0, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        sudokuPanel = new SudokuPanel(upperPane);
        playerListPanel = new PlayerListPanel(upperPane,this);
        highScorePanel = new HighScorePanel(this);
    }

    public void startGame() {
        try {
            var activePlayerUsername = session.getActivePlayer().getUsername();
            ((SudokuPanel) sudokuPanel).setActivePlayer(activePlayerUsername, this.player.getUsername());
            ((SudokuPanel) sudokuPanel).showScore(player.getScore());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private class ButtonAction extends AbstractAction {
        private final Character c;

        public ButtonAction(Character c) {
            this.c = c;
            this.putValue(Action.NAME, c.toString());
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            try {
                var running = session.getGameStarted();
                if(running){
                    var activePlayerUsername = session.getActivePlayer().getUsername();
                    ((SudokuPanel) sudokuPanel).setActivePlayer(activePlayerUsername, player.getUsername());
                    if(!activePlayerUsername.equals(player.getUsername())){
                        JOptionPane.showMessageDialog(null, "Du bist nicht am Zug!");
                        return;
                    }
                    var position = ((SudokuPanel) sudokuPanel).getPosition();
                    if (position == -1) {
                        return;
                    }
                    var tryOk = session.compare(c, position);
                    if (tryOk) {
                        ((SudokuPanel) sudokuPanel).setData(c);
                        player = session.getPlayer(player.getUsername());
                        var guesses = session.getGuesses();
                        ((SudokuPanel) sudokuPanel).updateGuesses(guesses);
                        ((SudokuPanel) sudokuPanel).showScore(player.getScore());
                        JOptionPane.showMessageDialog(null, "Richtig!");
                    }
                    if(session.isBoardSolved()){
                        JOptionPane.showMessageDialog(null,"Game is finished\n"+session.getPlayerScore());
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Das Spiel ist noch nicht gestartet!");
                }

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setBoard(Character[] board) throws RemoteException {
        var size = (int) Math.sqrt(board.length);
        ((SudokuPanel)sudokuPanel).setDataField(board, size, session.getChangeableBoard());

        if(size >= 9){
            aAButton.setVisible(true);
            aBButton.setVisible(true);
            aCButton.setVisible(true);
            aDButton.setVisible(true);
            aEButton.setVisible(true);
            aFButton.setVisible(true);
            aGButton.setVisible(true);
            aHButton.setVisible(true);
            aIButton.setVisible(true);

        }
        if (size >= 16) {
            aJButton.setVisible(true);
            aKButton.setVisible(true);
            aLButton.setVisible(true);
            aMButton.setVisible(true);
            aNButton.setVisible(true);
            aOButton.setVisible(true);
            aPButton.setVisible(true);
        }
        if(size == 25){
            aQButton.setVisible(true);
            aRButton.setVisible(true);
            aSButton.setVisible(true);
            aTButton.setVisible(true);
            aUButton.setVisible(true);
            aVButton.setVisible(true);
            aWButton.setVisible(true);
            aXButton.setVisible(true);
            aYButton.setVisible(true);
        }
    }

    public void setServerConnection(Server serverConnection) {
        if (loggedIn) {
            JOptionPane.showMessageDialog(this, "Bereits eingeloggt");
            return;
        }
        this.serverConnection = serverConnection;
        try {
            var username = JOptionPane.showInputDialog(this, "Benutzername: ");
            var id = serverConnection.login(username);
            if (id == null) {
                JOptionPane.showMessageDialog(this, "Benutzername bereits vergeben");
            }
            else{
                this.player = id;
                this.setTitle("Sudoku - " + username);
                loggedIn = true;
            }
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Login");
        }
    }

        public Server getServerConnection () {
            return serverConnection;
        }

        public Session getSession() {
            return session;
        }

        public void createGame(Session session) throws RemoteException {
            this.session = session;
            this.setBoard(session.getBoard());
            session.addPlayer(player);
        }

        public void joinGame(Session session) throws RemoteException {
            this.session = session;
            this.setBoard(session.getBoard());
        }

        public void logout() throws RemoteException {
            if (loggedIn) {
                var success = serverConnection.logout(player.getUsername());
                if (!success) {
                    JOptionPane.showMessageDialog(this, "Fehler beim Logout");
                    return;
                }
                loggedIn = false;
                this.setTitle("Sudoku");
                this.session = null;
                this.player = null;
                this.serverConnection = null;
                ((SudokuPanel) sudokuPanel).clear();
            }
            else{
                JOptionPane.showMessageDialog(this, "Nicht eingeloggt");
            }
        }

        public Player getPlayer() {
            return player;
        }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void changeDarkModeStatus() {
        darkMode = !isDarkMode();
        ((SudokuPanel)sudokuPanel).toggleDarkMode(darkMode);
        ((PlayerListPanel)playerListPanel).setDarkMode(darkMode);
    }

    public void showPlayerList(List<String> playerList, Boolean lobby){
        if(lobby){
            ((PlayerListPanel)playerListPanel).setLobbyPlayerList(playerList);
        }
        else{
            ((PlayerListPanel)playerListPanel).setPlayerList(playerList);
        }
        if(sudokuPanel.isVisible()) {
            sudokuPanel.setVisible(false);
            playerListPanel.setVisible(true);
        }else{
            sudokuPanel.setVisible(true);
            playerListPanel.setVisible(false);
        }
    }

    public void showHighscore(List<String> scores){
        ((HighScorePanel)highScorePanel).setHighscore(scores);
        if(sudokuPanel.isVisible()) {
            sudokuPanel.setVisible(false);
            highScorePanel.setVisible(true);
        }else{
            sudokuPanel.setVisible(true);
            highScorePanel.setVisible(false);
        }
    }

    public JPanel getSudokuPanel() {
        return sudokuPanel;
    }

}
