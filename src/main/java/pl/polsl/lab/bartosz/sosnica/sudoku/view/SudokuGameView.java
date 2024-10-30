package pl.polsl.lab.bartosz.sosnica.sudoku.view;

import pl.polsl.lab.bartosz.sosnica.sudoku.controller.BoardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGameView extends JFrame {

    private BoardController boardController;
    private JPanel boardPanel;
    private JComboBox<String> difficultyComboBox;
    private JButton startButton;
    private JButton addValueButton;

    public void SudokuGUI(BoardController boardController) {
        this.boardController = boardController;
        setTitle("Sudoku Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(9, 9));

        JPanel controlPanel = new JPanel();
        startButton = new JButton("Set board");
        addValueButton = new JButton("Add Value");
        controlPanel.add(startButton);
        controlPanel.add(addValueButton);

        for (int i = 0; i < 81; i++) {
            JTextField cell = new JTextField();
            cell.setHorizontalAlignment(JTextField.CENTER);
            cell.setFont(new Font("Arial", Font.PLAIN, 20));
            cell.setText("[ ]");
            boardPanel.add(cell);
        }

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardController.settingUpSudoku();
                updateBoard();
            }
        });

        add(controlPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void updateBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField cell = (JTextField) boardPanel.getComponent(i * 9 + j);
                String value = boardController.getBoardModel().getBoard()[i][j].equals("[ ]") ? "" : boardController.getBoardModel().getBoard()[i][j];
                cell.setText(value);
                cell.setEditable(value.isEmpty());
            }
        }
    }

}
