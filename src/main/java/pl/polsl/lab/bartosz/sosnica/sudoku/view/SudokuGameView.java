package pl.polsl.lab.bartosz.sosnica.sudoku.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SudokuGameView extends JFrame {

    private JPanel boardPanel;
    private JComboBox<String> difficultyComboBox;
    private JButton startButton;
    private JButton addValueButton;

    public SudokuGameView(){
        SudokuGUI();
    }

    public void SudokuGUI() {

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

        add(controlPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void addStartButtonListener(ActionListener listener){
        startButton.addActionListener(listener);
    }

    public void addAddValueButtonListener(ActionListener listener) {
        addValueButton.addActionListener(listener);
    }

    public JTextField getCellAt(int row, int col) {
        return (JTextField) boardPanel.getComponent(row * 9 + col);
    }

    public JPanel getBoardPanel(){
        return boardPanel;
    }

}
