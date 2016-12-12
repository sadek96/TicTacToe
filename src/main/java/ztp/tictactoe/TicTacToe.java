/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztp.tictactoe;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.text.DefaultFormatter;
import static ztp.tictactoe.Board.ZEROY;
import static ztp.tictactoe.Tile.TILESIZE;

/**
 *
 * @author Daniel
 *
 *
 *
 */
class ClearButtonAction implements ActionListener {

    Board board;

    public ClearButtonAction(Board board) {
        this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        board.clearBoard();
    }

}

class HostButtonAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        //Oczekiwanie na klienta czy cuś
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
        String text = textField.getText();
        AbstractFormatter formatter = textField.getFormatter();
        //Połączenie z serwerem
        //To narazie testuję wypisywanie textu w labelu
        try {
            byte[] ip = null;
            ip = (byte[]) formatter.stringToValue(text);
            text = formatter.valueToString(ip);
        } catch (ParseException pe) {
            text = pe.getMessage();
        } finally {
            label.setText(text);
            textField.setText("");
        }

    }

}

public class TicTacToe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("TicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Board board = new Board();
        JPanel contentPanel = new JPanel();
        JPanel connectionPanel = new JPanel();
        JPanel boardAndMessages = new JPanel();
        JPanel messagesPanel = new JPanel();
        JLabel messageLabel = new JLabel("Czekam na klienta...", JLabel.CENTER);

        JButton hostButton = new JButton("Host");
        hostButton.addActionListener(new HostButtonAction());

        JFormattedTextField ipField = new JFormattedTextField(new IPAddressFormatter());
        ipField.setToolTipText("Wprowadź IP hosta");

        JButton joinButton = new JButton("Join");
        joinButton.addActionListener(new JoinButtonAction(ipField, messageLabel));

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        connectionPanel.setLayout(gbl);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        connectionPanel.add(hostButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        connectionPanel.add(joinButton, gbc);

        ipField.setSize(125, 25);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        connectionPanel.add(ipField, gbc);

        messagesPanel.add(messageLabel);

        connectionPanel.setPreferredSize(new Dimension(TILESIZE * 2 - 2, TILESIZE * 3 - 2 + ZEROY));
        connectionPanel.setBackground(Color.DARK_GRAY);

        messagesPanel.setPreferredSize(new Dimension(TILESIZE * 3 - 2, TILESIZE / 2 - 2));
        messagesPanel.setBackground(Color.LIGHT_GRAY);

        boardAndMessages.setLayout(new BorderLayout());
        boardAndMessages.setBackground(Color.BLACK);

        board.setBackground(Color.WHITE);

        boardAndMessages.add(board, BorderLayout.NORTH);

        boardAndMessages.add(messagesPanel, BorderLayout.SOUTH);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(connectionPanel, BorderLayout.WEST);
        contentPanel.add(boardAndMessages, BorderLayout.EAST);

        contentPanel.setBackground(Color.BLACK);
        frame.add(contentPanel);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

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
