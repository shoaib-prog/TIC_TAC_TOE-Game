import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private boolean playerX = true;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private int scoreX = 0, scoreO = 0;

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for status & score
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        statusLabel = new JLabel("Player X's turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel = new JLabel("Score - X: 0  |  O: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        topPanel.add(statusLabel);
        topPanel.add(scoreLabel);
        add(topPanel, BorderLayout.NORTH);

        // Board panel
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        Font btnFont = new Font("Arial", Font.BOLD, 50);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(btnFont);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(Color.LIGHT_GRAY);
                buttons[i][j].addActionListener(this);
                boardPanel.add(buttons[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // Bottom panel with buttons
        JPanel bottomPanel = new JPanel();
        JButton newGameBtn = new JButton("New Game");
        JButton resetScoreBtn = new JButton("Reset Score");

        newGameBtn.setFont(new Font("Arial", Font.BOLD, 16));
        resetScoreBtn.setFont(new Font("Arial", Font.BOLD, 16));

        newGameBtn.addActionListener(e -> resetBoard(false));
        resetScoreBtn.addActionListener(e -> resetBoard(true));

        bottomPanel.add(newGameBtn);
        bottomPanel.add(resetScoreBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (!btn.getText().equals("")) {
            return; // already clicked
        }

        if (playerX) {
            btn.setForeground(Color.BLUE);
            btn.setText("X");
            statusLabel.setText("Player O's turn");
        } else {
            btn.setForeground(Color.RED);
            btn.setText("O");
            statusLabel.setText("Player X's turn");
        }

        if (checkWin()) {
            String winner = playerX ? "X" : "O";
            statusLabel.setText("Player " + winner + " wins!");
            if (winner.equals("X")) scoreX++;
            else scoreO++;
            updateScore();
            disableButtons();
        } else if (isBoardFull()) {
            statusLabel.setText("It's a Draw!");
        }

        playerX = !playerX;
    }

    private boolean checkWin() {
        String symbol = playerX ? "X" : "O";

        // Rows & Columns
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(symbol) &&
                buttons[i][1].getText().equals(symbol) &&
                buttons[i][2].getText().equals(symbol)) {
                highlightWin(i, 0, i, 1, i, 2);
                return true;
            }
            if (buttons[0][i].getText().equals(symbol) &&
                buttons[1][i].getText().equals(symbol) &&
                buttons[2][i].getText().equals(symbol)) {
                highlightWin(0, i, 1, i, 2, i);
                return true;
            }
        }

        // Diagonals
        if (buttons[0][0].getText().equals(symbol) &&
            buttons[1][1].getText().equals(symbol) &&
            buttons[2][2].getText().equals(symbol)) {
            highlightWin(0, 0, 1, 1, 2, 2);
            return true;
        }
        if (buttons[0][2].getText().equals(symbol) &&
            buttons[1][1].getText().equals(symbol) &&
            buttons[2][0].getText().equals(symbol)) {
            highlightWin(0, 2, 1, 1, 2, 0);
            return true;
        }

        return false;
    }

    private void highlightWin(int r1, int c1, int r2, int c2, int r3, int c3) {
        buttons[r1][c1].setBackground(Color.GREEN);
        buttons[r2][c2].setBackground(Color.GREEN);
        buttons[r3][c3].setBackground(Color.GREEN);
    }

    private boolean isBoardFull() {
        for (JButton[] row : buttons) {
            for (JButton btn : row) {
                if (btn.getText().equals("")) return false;
            }
        }
        return true;
    }

    private void disableButtons() {
        for (JButton[] row : buttons) {
            for (JButton btn : row) {
                btn.setEnabled(false);
            }
        }
    }

    private void updateScore() {
        scoreLabel.setText("Score - X: " + scoreX + "  |  O: " + scoreO);
    }

    private void resetBoard(boolean resetScore) {
        for (JButton[] row : buttons) {
            for (JButton btn : row) {
                btn.setText("");
                btn.setEnabled(true);
                btn.setBackground(Color.LIGHT_GRAY);
            }
        }
        playerX = true;
        statusLabel.setText("Player X's turn");
        if (resetScore) {
            scoreX = 0;
            scoreO = 0;
            updateScore();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
