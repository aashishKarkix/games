package org.example.ticktacktoe;

import javax.swing.*;
import java.awt.*;

public class Game {
    private GameManager gameManager;

    public Game() {
        JFrame frame = new JFrame("Tic-Tac-Toe");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        JButton[] buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 80));
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(e -> gameManager.handleButtonClick((JButton) e.getSource()));
            panel.add(buttons[i]);
        }

        ScoreBoard scoreBoard = new ScoreBoard();
        gameManager = new GameManager(buttons, scoreBoard);

        JPanel scorePanel = scoreBoard.getScorePanel();
        JPanel modePanel = new JPanel();
        JButton changeModeButton = new JButton("Change Mode");
        changeModeButton.setFont(new Font("Arial", Font.BOLD, 20));
        changeModeButton.addActionListener(e -> gameManager.chooseMode());
        modePanel.add(changeModeButton);

        frame.add(scorePanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(modePanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        gameManager.chooseMode();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}
