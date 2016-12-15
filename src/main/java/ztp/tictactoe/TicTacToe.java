/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztp.tictactoe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import java.awt.Dimension;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.text.ParseException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.text.DefaultFormatter;
import static ztp.tictactoe.Board.ZEROX;
import static ztp.tictactoe.Board.ZEROY;
import static ztp.tictactoe.Tile.TILESIZE;

/**
 *
 * @author Daniel
 *
 *
 *
 */
class HostButtonAction implements ActionListener {

    public HostButtonAction() {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Command clickCommand = new HostButtonClick();
        clickCommand.execute();
    }

}

class HostButtonClick implements Command {

    public HostButtonClick() {

    }

    @Override
    public void execute() {
        SocketProxy.makeHost();
        TicTacToe.wait = false;
    }

}

class JoinButtonAction implements ActionListener {

    JFormattedTextField textField;
    JLabel label;

    public JoinButtonAction(JFormattedTextField field, JLabel label) {
        this.textField = field;
        this.label = label;

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Command clickCommand = new JoinButtonClick(textField, label);
        clickCommand.execute();
    }

}

class BoardClick implements Command {

    Board board;
    Game game;
    Point p;

    public BoardClick(Board board, Game game, Point p) {
        this.board = board;
        this.game = game;
        this.p = p;
    }

    @Override
    public void execute() {

        int s = board.getTile(p).getState();
        if (s < 2 && game.isMyTurn()) {
            board.putX(p);
            board.repaint();
            game.setMyTurn(false);
            game.writeLine(p.x + "," + p.y);
            game.setMessage("Tura przeciwnika...");
        }
    }

}

class JoinButtonClick implements Command {

    JFormattedTextField textField;
    JLabel label;

    public JoinButtonClick(JFormattedTextField textField, JLabel label) {
        this.textField = textField;
        this.label = label;
    }

    @Override
    public void execute() {

        String text = textField.getText();
        AbstractFormatter formatter = textField.getFormatter();
        //Połączenie z serwerem
        //To narazie testuję wypisywanie textu w labelu
        try {
            byte[] ip = null;
            ip = (byte[]) formatter.stringToValue(text);
            text = formatter.valueToString(ip);
            label.setText(text);
            SocketProxy.makeClient();
            SocketProxy.setIp(text);
            TicTacToe.wait = false;

        } catch (ParseException pe) {
            text = pe.getMessage();
            label.setText(text);
        }

        textField.setText("");
    }

}

class BoardMouse extends MouseAdapter {

    Board board;
    Game game;

    public BoardMouse(Board board, Game game) {
        this.board = board;
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        Point p = new Point((me.getX() - ZEROX) / TILESIZE, (me.getY() - ZEROY) / TILESIZE);
        Command clickCommand = new BoardClick(board, game, p);
        clickCommand.execute();
    }
}

class ContentPanelBuilder implements PanelBuilder {

    private JPanel contentPanel;
    private JPanel boardAndMessages;
    private JPanel connectionPanel;

    public ContentPanelBuilder() {
        contentPanel = new JPanel(new BorderLayout());
    }

    public ContentPanelBuilder(JPanel boardAndMsg, JPanel comPanel) {
        contentPanel = new JPanel(new BorderLayout());
        this.boardAndMessages = boardAndMsg;
        this.connectionPanel = comPanel;
    }

    public ContentPanelBuilder(int vGap, int hGap) {
        contentPanel = new JPanel(new BorderLayout(vGap, hGap));
    }

    public void addSouthComponent(Component component) {
        contentPanel.add(component, BorderLayout.SOUTH);
    }

    public void addNorthComponent(Component component) {
        contentPanel.add(component, BorderLayout.NORTH);
    }

    public void addEastComponent(Component component) {
        contentPanel.add(component, BorderLayout.EAST);
    }

    public void addWestComponent(Component component) {
        contentPanel.add(component, BorderLayout.WEST);
    }

    public JPanel buildPanel() {
        addWestComponent(connectionPanel);
        addEastComponent(boardAndMessages);
        contentPanel.setBackground(Color.BLACK);
        return contentPanel;
    }
}

class ConnectionPanelBuilder implements PanelBuilder {

    JPanel panel;
    GridBagLayout layout;
    GridBagConstraints gbc;
    JButton hBtn;
    JButton jBtn;
    JFormattedTextField ipField;

    public ConnectionPanelBuilder(JButton hBtn, JButton jBtn, JFormattedTextField ipField) {
        this.panel = new JPanel();
        this.layout = new GridBagLayout();
        panel.setLayout(layout);
        this.gbc = new GridBagConstraints();
        this.jBtn = jBtn;
        this.hBtn = hBtn;
        this.ipField = ipField;
    }

    public JPanel buildPanel() {
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(hBtn, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(jBtn, gbc);

        ipField.setSize(125, 25);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(ipField, gbc);

        panel.setPreferredSize(new Dimension(TILESIZE * 2 - 2, TILESIZE * 3 - 2 + ZEROY));
        panel.setBackground(Color.DARK_GRAY);

        return panel;
    }
}

class BoardMsgPanelBuilder implements PanelBuilder {

    JPanel contentPanel;
    JPanel panel;
    JLabel label;
    Board board;

    public BoardMsgPanelBuilder(Board board, JLabel label) {
        contentPanel = new JPanel(new BorderLayout());
        panel = new JPanel();
        this.label = label;
        this.board = board;
    }

    @Override
    public JPanel buildPanel() {
        panel.add(label);
        panel.setPreferredSize(new Dimension(TILESIZE * 3 - 2, TILESIZE / 2 - 2));
        panel.setBackground(Color.LIGHT_GRAY);
        contentPanel.add(board, BorderLayout.NORTH);
        contentPanel.add(panel, BorderLayout.SOUTH);
        contentPanel.setBackground(Color.BLACK);
        return contentPanel;
    }

}

class PanelArchitect {

    JFrame frame;
    JPanel contentPanel;
    JPanel connectionPanel;
    JPanel boardAndMessages;
    JLabel messageLabel;
    JButton hostButton;
    JButton joinButton;
    JFormattedTextField ipField;
    Board board;
    PanelBuilder builder;

    public void construct() {
        frame = new JFrame("TicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board = new Board();

        messageLabel = new JLabel("Witaj...", JLabel.CENTER);

        hostButton = new JButton("Host");
        hostButton.addActionListener(new HostButtonAction());

        ipField = new JFormattedTextField(new IPAddressFormatter());
        ipField.setToolTipText("Wprowadź IP hosta");

        joinButton = new JButton("Join");
        joinButton.addActionListener(new JoinButtonAction(ipField, messageLabel));

        builder = new ContentPanelBuilder(new BoardMsgPanelBuilder(board, messageLabel).buildPanel(), new ConnectionPanelBuilder(hostButton, joinButton, ipField).buildPanel());
        contentPanel = builder.buildPanel();

        frame.add(contentPanel);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public JLabel getMsgLabel() {
        return this.messageLabel;
    }

    public Board getBoard() {
        return this.board;
    }

    public JButton getJoinButton() {
        return this.joinButton;
    }

    public JButton getHostButton() {
        return this.hostButton;
    }
}

public class TicTacToe {

    static boolean wait = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JLabel messageLabel;
        Board board;
        JButton joinButton, hostButton;

        PanelArchitect architect = new PanelArchitect();
        architect.construct();

        joinButton = architect.getJoinButton();
        messageLabel = architect.getMsgLabel();
        board = architect.getBoard();
        hostButton = architect.getHostButton();

        while (wait) {
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        SocketProxy socket = null;

        try {
            socket = SocketProxy.getInstance();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Game game = new Game(architect.getMsgLabel(), architect.getBoard(), socket);

        if (socket.isHost()) {
            game.setMyTurn(true);
            messageLabel.setText("Otrzymano połącznie...");
            joinButton.setEnabled(false);
        } else {
            hostButton.setEnabled(false);
            game.setMyTurn(false);
            messageLabel.setText("Połączono...");
        }

        board.addMouseListener(new BoardMouse(board, game));

        game.run();

    }

}

class IPAddressFormatter extends DefaultFormatter {

    public String valueToString(Object value) throws ParseException {
        if (!(value instanceof byte[])) {
            throw new ParseException("Not a byte[]", 0);
        }
        byte[] a = (byte[]) value;
        if (a.length != 4) {
            throw new ParseException("Length != 4", 0);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int b = a[i];
            if (b < 0) {
                b += 256;
            }
            builder.append(String.valueOf(b));
            if (i < 3) {
                builder.append('.');
            }
        }
        return builder.toString();
    }

    public Object stringToValue(String text) throws ParseException {
        StringTokenizer tokenizer = new StringTokenizer(text, ".");
        byte[] a = new byte[4];
        for (int i = 0; i < 4; i++) {
            int b = 0;
            if (!tokenizer.hasMoreTokens()) {
                throw new ParseException("Niepoprawny adres", 0);
            }
            try {
                b = Integer.parseInt(tokenizer.nextToken());
            } catch (NumberFormatException e) {
                throw new ParseException("Niepoprawny adres", 0);
            }
            if (b < 0 || b >= 256) {
                throw new ParseException("Niepoprawny adres", 0);
            }
            a[i] = (byte) b;
        }
        if (tokenizer.hasMoreTokens()) {
            throw new ParseException("Niepoprawny adres", 0);
        }
        return a;
    }
}
