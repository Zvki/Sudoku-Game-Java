package pl.polsl.lab.bartosz.sosnica.sudoku.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SudokuGameView extends JFrame {

    private JPanel mainBoardPanel;
    private JComboBox<String> difficultyComboBox;
    private JButton startButton;
    private JButton addValueButton;

    public SudokuGameView() {
        SudokuGUI();
    }

    public void SudokuGUI() {
        setTitle("Sudoku Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainBoardPanel = new JPanel();
        mainBoardPanel.setLayout(new GridLayout(3, 3, 5, 5)); // Główna siatka 3x3 z odstępami między blokami

        JPanel controlPanel = new JPanel();
        startButton = new JButton("Set board");
        startButton.setToolTipText("Set up the Sudoku board"); // Tooltip
        startButton.getAccessibleContext().setAccessibleDescription("Button to set up the Sudoku board"); // Opis kontekstowy
        startButton.setMnemonic('S'); // Skrót klawiszowy (Alt + S)

        addValueButton = new JButton("Add Value");
        addValueButton.setToolTipText("Add a value to the Sudoku board"); // Tooltip
        addValueButton.getAccessibleContext().setAccessibleDescription("Button to add a value to the Sudoku board"); // Opis kontekstowy
        addValueButton.setMnemonic('A'); // Skrót klawiszowy (Alt + A)

        controlPanel.add(startButton);
        controlPanel.add(addValueButton);

        // Dodaj mniejsze panele 3x3 do głównego panelu planszy
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                JPanel subGrid = new JPanel(new GridLayout(3, 3)); // Panel 3x3 dla każdego bloku Sudoku
                subGrid.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Dodaje obramowanie wokół bloku

                for (int i = 0; i < 9; i++) {
                    JTextField cell = new JTextField();
                    cell.setHorizontalAlignment(JTextField.CENTER);
                    cell.setFont(new Font("Arial", Font.PLAIN, 20));
                    cell.setToolTipText("Row " + (blockRow * 3 + i / 3 + 1) + ", Column " + (blockCol * 3 + i % 3 + 1)); // Tooltip
                    subGrid.add(cell); // Dodawanie JTextField do subGrid
                }
                mainBoardPanel.add(subGrid); // Dodawanie subGrid do mainBoardPanel
            }
        }

        add(controlPanel, BorderLayout.NORTH);
        add(mainBoardPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Dodawanie nasłuchiwaczy dla przycisków
    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void addAddValueButtonListener(ActionListener listener) {
        addValueButton.addActionListener(listener);
    }

    // Metoda do pobrania konkretnego pola planszy
    public JTextField getCellAt(int row, int col) {
        // Sprawdzamy, czy row i col są w zakresie
        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            throw new IndexOutOfBoundsException("Invalid cell index");
        }

        int blockRow = row / 3;
        int blockCol = col / 3;
        int cellIndexInBlock = (row % 3) * 3 + (col % 3);
        JPanel subGrid = (JPanel) mainBoardPanel.getComponent(blockRow * 3 + blockCol);

        // Tu upewniamy się, że uzyskujemy JTextField
        return (JTextField) subGrid.getComponent(cellIndexInBlock);
    }

    public JPanel getBoardPanel() {
        return mainBoardPanel;
    }
}
