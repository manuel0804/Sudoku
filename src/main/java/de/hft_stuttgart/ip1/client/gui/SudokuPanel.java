package de.hft_stuttgart.ip1.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Objects;

public class SudokuPanel extends JPanel {
    private int gridCount = 10;
    private int widthDivisor = 10;
    private int heightDivisor = 12;
    private Dimension size;
    private int startX = 0;
    private int startY = 0;
    private final JPanel parent;
    private int boxX = -1;
    private int boxY = -1;
    private Character dataField[];
    private Boolean changeableField[];

    private int baordSize = 0;

    private boolean darkMode = false;
    private String activePlayer = "";
    private int score = -1;
    private String guesses;
    private Integer playerCount;

    public SudokuPanel(JPanel upperPane) {
        this.parent = upperPane;
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                computeSize();
            }
        });

    }

    public void newField(int size){
        dataField = new Character[size*size];
        baordSize = size;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Point point = e.getPoint();
                boxX = boxY = -1;
                outer:
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if ( getX(i)<=point.x && getX(i+1)>point.x &&
                                getY(j)<=point.y && getY(j+1)>point.y ) {
                            boxX = i;
                            boxY = j;
                            break outer;
                        }
                    }
                }

                repaint();
            }
        });
    }
    public void setDataField(Character[] dataField, int size, Boolean[] changeableField) {
        newField(size);
        this.dataField = dataField;
        this.changeableField = changeableField;
        repaint();
    }

    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        if ( size == null ) {
            computeSize();
        }
        Graphics2D g2d = (Graphics2D) gc;
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        Rectangle2D rcTitle = g2d.getFontMetrics().getStringBounds("Sudoku", g2d);
        g2d.drawString("Sudoku", (int)(size.width/2-rcTitle.getWidth()/2),
                (int)(5+rcTitle.getHeight()));

        if(playerCount != null && playerCount > 1){
            if (!activePlayer.equals("")){
                if(activePlayer.equals("Du")){
                    g2d.drawString(activePlayer + " bist am Zug!", 10,
                            (int)(size.getHeight()-20));
                    g2d.drawString("Freie Eingaben: " + guesses, 10,
                            (int)(size.getHeight()-40));
                }
                else{
                    g2d.drawString(activePlayer +" ist am Zug!", 10,
                            (int)(size.getHeight()-20));
                }
            }
        }

        if (score != -1){
            g2d.drawString("Deine Punktzahl: " + score, (int)(size.getWidth()-300),
                    (int)(size.getHeight()-20));
        }

        var squareBoardSize = Math.sqrt(baordSize);
        Stroke thinStroke = new BasicStroke(1.0f);
        Stroke thickStroke = new BasicStroke(2.0f);
        for (int i = 0; i< baordSize +1; i++) {
            g2d.setStroke(i%squareBoardSize==0?thickStroke:thinStroke);
            g2d.drawLine(getX(0), getY(i),
                    getX(baordSize), getY(i));
        }
        for (int i = 0; i< baordSize +1; i++) {
            g2d.setStroke(i%squareBoardSize==0?thickStroke:thinStroke);
            g2d.drawLine(getX(i), getY(0),
                        getX(i), getY(baordSize));
        }
        if ( boxX != -1 && boxY != -1 ) {
            Rectangle rcBox = new Rectangle(getX(boxX)+2, getY(boxY)+2,
                    getX(1)-getX(0)-4, getY(1)-getY(0)-4);
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(rcBox.x, rcBox.y, rcBox.width, rcBox.height);
        }

        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        for (int y = 0; y < baordSize; y++) {
            for (int x = 0; x < baordSize; x++) {
                if ( dataField[baordSize *y+x] != null) {
                    var changeable = changeableField[baordSize *y+x];
                    String data = dataField[baordSize *y+x].toString();
                    Rectangle rcBox = new Rectangle(getX(x)+2, getY(y)+2,
                            getX(1)-getX(0)-4, getY(1)-getY(0)-4);
                    Rectangle2D rcText = g2d.getFontMetrics().getStringBounds( data, g2d);
                    if(changeable)
                        g2d.setColor(Color.BLUE);
                    else
                        g2d.setColor(Color.BLACK);
                    g2d.drawString(data,
                            rcBox.x+rcBox.width/2-(int)rcText.getWidth()/2,
                            rcBox.y+5*rcBox.height/6);
                }
            }
        }

    }

    public Integer getPosition() {
        var position = -1;
        if ( boxX != -1 && boxY != -1 ) {
            if ( !changeableField[baordSize *boxY+boxX] ) {
                return -1;
            }
            position = baordSize *boxY+boxX;
        }
        return position;
    }

    public Integer setData(Character c){
        var position = -1;
        if ( boxX != -1 && boxY != -1 ) {
            if ( !changeableField[baordSize *boxY+boxX] ) {
                return -1;
            }
            if (Objects.equals(dataField[9*boxY+boxX], c) ) {
                dataField[baordSize * boxY + boxX] = null;
            }
            else {
                dataField[baordSize *boxY+boxX] = c;
                position = baordSize *boxY+boxX;
            }
            repaint();
        }
        return position;
    }

    public void updateBoard(Character[] board){
        dataField = board;
        repaint();
    }

    private void computeSize() {
        size = getSize();
        startX = size.width/(widthDivisor+2);
        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        Rectangle2D rcTitle = g2d.getFontMetrics().getStringBounds("Sudoku", g2d);
        startY = size.height/(heightDivisor-1)+(int)rcTitle.getHeight();
        g2d.dispose();
    }

    public void clear() {
        dataField = new Character[baordSize * baordSize];
        repaint();
    }

    private int getX(int i) {
        if (baordSize == 9) {
            return startX + i * size.width / 11;
        } else if (baordSize == 16) {
            return startX+i*size.width/19;
        }
        else{
            return startX + i * size.width / 30;
        }

    }

    private int getY(int i) {
        if (baordSize == 9) {
            return startY+i*size.height/12;
        } else if (baordSize == 16) {
            return startY+i*size.height/20;
        }
        else{
            return startY+i*size.height/31;
        }
    }


    public void toggleDarkMode(boolean mode) {
        darkMode = mode;
        var background = darkMode ? Color.DARK_GRAY : Color.WHITE;
        var foreground = darkMode ? Color.WHITE : Color.DARK_GRAY;
        setBackground(background);
        setForeground(foreground);
        repaint();
    }

    public void setActivePlayer(String player, String thisPlayer) {
        activePlayer = player.equals(thisPlayer) ? "Du" : player;
        repaint();
    }

    public void showScore(int score){
        this.score =  score;
    }

    public void updateGuesses(String guesses){
        this.guesses = guesses;
        repaint();
    }

    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }
}
