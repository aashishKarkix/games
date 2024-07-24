package org.example.ticktacktoe;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ScoreBoard {
    private final JPanel scorePanel;
    private final JLabel playerXNameLabel;
    private final JLabel playerONameLabel;
    private final JLabel playerXScoreLabel;
    private final JLabel playerOScoreLabel;
    private final JLabel drawLabel;
    private final Color backgroundColor = new Color(0x002D72);
    private final Border border = BorderFactory.createLineBorder(Color.WHITE, 4);

    public ScoreBoard() {
        scorePanel = new JPanel();
        scorePanel.setLayout(new GridBagLayout());
        scorePanel.setBackground(backgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font nameFont = new Font("Arial", Font.BOLD, 24);
        Color nameColor = Color.WHITE;
        playerXNameLabel = createLabel("Player X", nameColor, nameFont);
        playerONameLabel = createLabel("Player O", nameColor, nameFont);
        Font scoreFont = new Font("Digital-7", Font.BOLD, 48);
        Color scoreColorX = Color.RED;
        playerXScoreLabel = createLabel("0", scoreColorX, scoreFont);
        Color scoreColorO = Color.BLACK;
        playerOScoreLabel = createLabel("0", scoreColorO, scoreFont);
        Color drawColor = Color.WHITE;
        drawLabel = createLabel("Draws: 0", drawColor, nameFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        scorePanel.add(playerXNameLabel, gbc);

        gbc.gridx = 1;
        scorePanel.add(drawLabel, gbc);

        gbc.gridx = 2;
        scorePanel.add(playerONameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        scorePanel.add(playerXScoreLabel, gbc);

        gbc.gridx = 2;
        scorePanel.add(playerOScoreLabel, gbc);
    }

    private JLabel createLabel(String text, Color textColor, Font font) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(textColor);
        label.setOpaque(true);
        label.setBackground(backgroundColor);
        label.setBorder(border);
        return label;
    }

    public JPanel getScorePanel() {
        return scorePanel;
    }

    public void updateScores(int playerXScore, int playerOScore, int draws) {
        playerXScoreLabel.setText(String.valueOf(playerXScore));
        playerOScoreLabel.setText(String.valueOf(playerOScore));
        drawLabel.setText("Draws: " + draws);
    }

    public void updatePlayerNames(String playerXName, String playerOName) {
        playerXNameLabel.setText(playerXName);
        playerONameLabel.setText(playerOName);
    }
}
