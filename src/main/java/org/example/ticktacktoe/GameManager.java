package org.example.ticktacktoe;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;
import java.util.Random;

public class GameManager {
    private final JButton[] buttons;
    private final ScoreBoard scoreBoard;
    private boolean playerX = true;
    private boolean playWithCPU = false;
    private boolean isPlayerXStarting = true;
    private String difficulty = "Easy";
    private String playerXName = "Player X";
    private String playerOName = "Player O";
    private int playerXScore = 0;
    private int playerOScore = 0;
    private int drawScore = 0;
    private final Random random = new Random();

    public GameManager(JButton[] buttons, ScoreBoard scoreBoard) {
        this.buttons = buttons;
        this.scoreBoard = scoreBoard;
    }

    public void handleButtonClick(JButton buttonClicked) {
        if (buttonClicked.getText().isEmpty()) {
            if (playerX) {
                buttonClicked.setText("X");
                buttonClicked.setForeground(Color.RED);
            } else {
                buttonClicked.setText("O");
                buttonClicked.setForeground(Color.BLACK);
            }
            playerX = !playerX;
            checkForWin();

            if (playWithCPU && !playerX) {
                cpuMove();
                checkForWin();
            }
        }
    }

    public void chooseMode() {
        String[] options = {"Play with Human", "Play with CPU"};
        ImageIcon icon = new ImageIcon("src/main/java/org/example/ticktacktoe/images/ticktacktoe.png");
        Image image = icon.getImage();
        Image img = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        int response = JOptionPane.showOptionDialog(
            null,
            "Choose an option:",
            "Tic-Tac-Toe",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            icon,
            options,
            options[0]);
        playWithCPU = response == 1;
        if (playWithCPU) {
            String[] difficultyOptions = {"Easy", "Medium", "Hard"};
            icon = new ImageIcon("src/main/java/org/example/ticktacktoe/images/robot.png");
            image = icon.getImage();
            img = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
            difficulty = (String) JOptionPane.showInputDialog(
                null,
                "Select difficulty level:",
                "Difficulty",
                JOptionPane.QUESTION_MESSAGE,
                icon,
                difficultyOptions,
                difficultyOptions[0]
            );
            scoreBoard.updatePlayerNames(playerXName, playerOName);
        } else {
            icon = new ImageIcon("src/main/java/org/example/ticktacktoe/images/robot.png");
            image = icon.getImage();
            img = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
            playerXName = (String) JOptionPane.showInputDialog(
                null,
                "Enter name for Player X:",
                "Player Name Input",
                JOptionPane.PLAIN_MESSAGE,
                icon,
                null,
                "Player X"
            );
            playerOName = (String) JOptionPane.showInputDialog(
                null,
                "Enter name for Player O:",
                "Player Name Input",
                JOptionPane.PLAIN_MESSAGE,
                icon,
                null,
                "Player O"
            );
            playerXName = Optional.ofNullable(playerXName).orElse("Player X");
            playerOName = Optional.ofNullable(playerOName).orElse("Player O");
            scoreBoard.updatePlayerNames(playerXName, playerOName);
        }
        resetBoard();
    }

    private void cpuMove() {
        int move = switch (difficulty) {
            case "Easy" -> randomMove();
            case "Medium" -> mediumMove();
            case "Hard" -> minimaxMove();
            default -> throw new IllegalStateException("Unexpected value: " + difficulty);
        };
        buttons[move].setText("O");
        buttons[move].setForeground(Color.BLACK);
        playerX = true;
    }

    private int randomMove() {
        int move;
        do {
            move = random.nextInt(9);
        } while (!buttons[move].getText().isEmpty());
        return move;
    }

    private int mediumMove() {
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().isEmpty()) {
                buttons[i].setText("O");
                if (checkWinCondition("O")) {
                    buttons[i].setText("");
                    return i;
                }
                buttons[i].setText("");
            }
        }
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().isEmpty()) {
                buttons[i].setText("X");
                if (checkWinCondition("X")) {
                    buttons[i].setText("");
                    return i;
                }
                buttons[i].setText("");
            }
        }
        return randomMove();
    }

    private int minimaxMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().isEmpty()) {
                buttons[i].setText("O");
                int score = minimax(false);
                buttons[i].setText("");
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    private int minimax(boolean isMaximizing) {
        if (checkWinCondition("O")) {
            return 1;
        } else if (checkWinCondition("X")) {
            return -1;
        } else if (isBoardFull()) {
            return 0;
        }

        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (buttons[i].getText().isEmpty()) {
                    buttons[i].setText("O");
                    int score = minimax(false);
                    buttons[i].setText("");
                    bestScore = Math.max(score, bestScore);
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (buttons[i].getText().isEmpty()) {
                    buttons[i].setText("X");
                    int score = minimax(true);
                    buttons[i].setText("");
                    bestScore = Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }

    private boolean checkWinCondition(String player) {
        String[][] board = new String[3][3];
        for (int i = 0; i < 9; i++) {
            board[i / 3][i % 3] = buttons[i].getText();
        }

        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player)) {
                return true;
            }
            if (board[0][i].equals(player) && board[1][i].equals(player) && board[2][i].equals(player)) {
                return true;
            }
        }
        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) {
            return true;
        }
        return board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player);
    }

    private boolean isBoardFull() {
        for (JButton button : buttons) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void checkForWin() {
        if (checkWinCondition("X")) {
            JOptionPane.showMessageDialog(null, playerXName + " Wins!");
            playerXScore++;
            resetBoard();
        } else if (checkWinCondition("O")) {
            JOptionPane.showMessageDialog(null, playerOName + " Wins!");
            playerOScore++;
            resetBoard();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(null, "It's a Draw!");
            drawScore++;
            resetBoard();
        }
        scoreBoard.updateScores(playerXScore, playerOScore, drawScore);
    }

    private void resetBoard() {
        for (JButton button : buttons) {
            button.setText("");
        }
        isPlayerXStarting = !isPlayerXStarting;
        playerX = isPlayerXStarting;

        String currentPlayer = playerX ? "Player X" : "Player O";
        JOptionPane.showMessageDialog(null, currentPlayer + "'s turn", "Turn Information", JOptionPane.INFORMATION_MESSAGE);

        if (playWithCPU && !playerX) {
            cpuMove();
        }
    }
}
