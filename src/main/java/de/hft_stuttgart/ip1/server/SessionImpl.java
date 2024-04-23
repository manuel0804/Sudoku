package de.hft_stuttgart.ip1.server;

import de.hft_stuttgart.ip1.common.Highscore;
import de.hft_stuttgart.ip1.common.Player;
import de.hft_stuttgart.ip1.common.Server;
import de.hft_stuttgart.ip1.common.Session;
import de.hft_stuttgart.ip1.sudokuGen.Generator;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class SessionImpl extends UnicastRemoteObject implements Session {

    private Character[] board;

    private Boolean[] canChange;
    private Character[] boardLsg;

    private final Player[] players;

    int activePlayerIndex;

    private Boolean gameStarted = false;

    private final String sessionName;

    private Character[] guesses;

    private final Integer gameSize;

    private final Server server;
    private boolean gameFinished;


    protected SessionImpl(Integer size, Integer playerCount, String sessionName, Server server) throws RemoteException {
        super();
        createBoard(size);
        gameSize = size;
        players = new Player[playerCount];
        this.sessionName = sessionName;
        guesses = resetGuesses(size);
        this.server = server;
        this.gameFinished = false;
    }

    private Character[] resetGuesses(Integer size){
        Character[] guesses = new Character[size];
        for (int i = 0; i < size; i++) {
            guesses[i] = (char) (i + 65);
        }
        return guesses;
    }
    private Boolean useGuess(Character c){
        for (int i = 0; i < guesses.length; i++) {
            if (guesses[i].equals(c)){
                guesses[i] = '$';
                return true;
            }
        }
        return false;
    }

    @Override
    public Character[] getBoard() throws RemoteException {
        return board;
    }

    @Override
    public Boolean compare(Character given, Integer position) throws RemoteException {
        if (board[position] == null || !board[position].equals(boardLsg[position])) {
            var solution = boardLsg[position];
            if (given.equals(solution)) {
                var valid = useGuess(given);
                if (valid) {
                    board[position] = given;
                    players[activePlayerIndex].addScore(((int) given) - 64);
                    return true;
                }
            }
        }
            if (activePlayerIndex == players.length - 1) {
                activePlayerIndex = 0;
            } else {
                activePlayerIndex++;
            }
            guesses = resetGuesses(gameSize);
            return false;
    }
    @Override
    public Boolean isBoardSolved() throws RemoteException {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == null || !board[i].equals(boardLsg[i])) {
                return false;
            }
        }
        this.gameFinished = true;
        server.addHighscore(this);
        return true;
    }

    @Override
    public Boolean isFinished(){
        return gameFinished;
    }



    @Override
    public Boolean createBoard(Integer size) throws RemoteException {
        try {
            Generator sudoku = null;
            switch (size) {
                case 9:
                    sudoku = new Generator(9, 41);
                    break;
                case 16:
                    sudoku = new Generator(16, 146);
                    break;
                case 25:
                    sudoku = new Generator(25,303);
                    break;
                default:
                    System.out.println("Falsche Eingabe");
                    break;
            }
            sudoku.fillValues();
            boardLsg = sudoku.getCharSudoku();
            sudoku.removeKDigits();
            board = sudoku.getCharSudoku();
            canChange = new Boolean[board.length];
            for (int i = 0; i < canChange.length; i++) {
                if (board[i] == null) {
                    canChange[i] = true;
                } else {
                    canChange[i] = false;
                }
            }

            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean[] getChangeableBoard() throws RemoteException {
        return canChange;
    }

    @Override
    public Character[] getBoardLsg() throws RemoteException {
        return boardLsg;
    }

    @Override
    public Player getActivePlayer() throws RemoteException {
        return players[activePlayerIndex];
    }

    @Override
    public Boolean addPlayer(Player player) throws RemoteException {
        if(players[players.length-1] == null){
            for (int i = 0; i < players.length; i++) {
                if(players[i] == null){
                    players[i] = player;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean removePlayer(Player player) throws RemoteException {
        for (int i = 0; i < players.length; i++) {
            if(players[i].equals(player)){
                players[i] = null;
                return true;
            }
        }
        return false;
    }


    @Override
    public Boolean startGame() throws RemoteException {
        if (gameStarted || players[players.length-1] == null) {
            return false;
        }
        activePlayerIndex = 0;
        gameStarted = true;
        return true;
    }

    @Override
    public Boolean getGameStarted() throws RemoteException {
        return gameStarted;
    }

    @Override
    public Player getPlayer(String Username) throws RemoteException {
        for (int i = 0; i < players.length; i++) {
            if(players[i].getUsername().equals(Username)){
                return players[i];
            }
        }
        return null;
    }

    @Override
    public String getSessionName() throws RemoteException {
        return sessionName;
    }

    @Override
    public Session getSession(){
        return this;
    }

    @Override
    public String getPlayerScore() throws RemoteException {
        String ausgabe="";
        for(int i=0;i<players.length; i++){
            ausgabe += String.format("Player: %-20s Score: %d\n",players[i].getUsername(),players[i].getScore()) ;
        }
        return ausgabe;
    }

    @Override
    public List<Highscore> returnHighscore() throws RemoteException {
        List<Highscore> highscoreList = new ArrayList<>();
        for (Player player : players) {
            {
                highscoreList.add(new HighscoreImpl(player.getUsername(), gameSize, players.length, player.getScore()));

            }
            highscoreList.stream().sorted((a,b) -> {
                try {
                    return a.getScore().compareTo(b.getScore());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });

        }
        return highscoreList;
    }

    @Override
    public List<String> getPlayerList() throws RemoteException {
        List<String> playerList = new ArrayList<>();
        for (Player player : players) {
            {
                playerList.add(player.getUsername());
            }

        }
        return playerList;
    }

    @Override
    public String getGuesses() throws RemoteException {
        StringBuilder returnValue = new StringBuilder();
        for (Character c : guesses) {
            if(!c.equals('$')){
                returnValue.append(c).append(" ");
            }
        }
        return returnValue.toString();
    }
}