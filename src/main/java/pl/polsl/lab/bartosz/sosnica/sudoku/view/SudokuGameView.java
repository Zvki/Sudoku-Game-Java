package pl.polsl.lab.bartosz.sosnica.sudoku.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

/**
 * <p>
 * The SudokuGameView class represents the graphical user interface for the Sudoku game.
 * This class sets up the layout for the game, including the main Sudoku grid and control buttons.
 * It provides methods to add listeners and retrieve cells for interaction with the controller.
 * </p>
 *
 * <p>This class is part of the View layer in the MVC architecture, displaying the game state
 * and handling user interactions through buttons and a grid layout.</p>
 *
 * @author Bartosz Sośnica
 * @version 1.0
 */
public class SudokuGameView extends JFrame {

    /**
     * The main panel that displays the 9x9 Sudoku board in a grid layout.
     * This panel contains nine 3x3 subgrids to represent each section of the board.
     */
    private JPanel mainBoardPanel;

    /**
     * The button that starts a new game or sets up the Sudoku board.
     */
    private JButton startButton;

    /**
     * The button that allows the user to add a value to a specific cell on the board.
     */
    private JButton addValueButton;

    /**
     * Constructor that initializes the Sudoku GUI, setting up the main components
     * and displaying the window.
     */
    public SudokuGameView() {
        initializeSudokuGUI();
    }

    /**
     * Sets up the main components of the Sudoku game GUI.
     * Initializes the main board panel, control panel with buttons,
     * and sets the layout and size of the main frame.
     */
    private void initializeSudokuGUI() {
        setTitle("Sudoku Game");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        mainBoardPanel = new JPanel();
        mainBoardPanel.setLayout(new GridLayout(3, 3, 5, 5)); // Main grid 3x3 with gaps

        JPanel controlPanel = new JPanel();

        startButton = createButton("Set Board", "Set up the Sudoku board", 'S');
        addValueButton = createButton("Add Value", "Add a value to the Sudoku board", 'A');

        controlPanel.add(startButton);
        controlPanel.add(addValueButton);

        createSudokuGrid();

        add(controlPanel, BorderLayout.NORTH);
        add(mainBoardPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Creates the Sudoku grid layout consisting of 3x3 subgrids.
     * Each subgrid contains 3x3 JTextFields to represent individual cells.
     */
    private void createSudokuGrid() {
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                JPanel subGrid = new JPanel(new GridLayout(3, 3));
                subGrid.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                for (int i = 0; i < 9; i++) {
                    JTextField cell = new JTextField();
                    cell.setHorizontalAlignment(JTextField.CENTER);
                    cell.setFont(new Font("Arial", Font.PLAIN, 20));
                    cell.setToolTipText("Row " + (blockRow * 3 + i / 3 + 1) + ", Column " + (blockCol * 3 + i % 3 + 1)); // Tooltip
                    subGrid.add(cell);
                }
                mainBoardPanel.add(subGrid);
            }
        }
    }

    /**
     * Helper method to create buttons with common properties such as text, tooltip, and mnemonic.
     *
     * @param text     The button text to display.
     * @param tooltip  The tooltip text to show when hovering over the button.
     * @param mnemonic The keyboard shortcut associated with the button.
     * @return The created JButton with the specified properties.
     */
    private JButton createButton(String text, String tooltip, char mnemonic) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.getAccessibleContext().setAccessibleDescription(tooltip);
        button.setMnemonic(mnemonic);
        return button;
    }

    /**
     * Adds an ActionListener to the start button, allowing it to respond to user clicks.
     *
     * @param listener the ActionListener to add to the start button.
     */
    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    /**
     * Adds an ActionListener to the add value button, allowing it to respond to user clicks.
     *
     * @param listener the ActionListener to add to the add value button.
     */
    public void addAddValueButtonListener(ActionListener listener) {
        addValueButton.addActionListener(listener);
    }

    /**
     * Adds a WindowListener for handling window events, such as closing the window.
     *
     * @param listener the WindowListener to add to this JFrame.
     */
    public void addWindowCloseListener(WindowListener listener) {
        super.addWindowListener(listener);
    }

    /**
     * Retrieves the JTextField located at the specified row and column within the Sudoku grid.
     *
     * @param row the row index of the desired cell (0-8).
     * @param col the column index of the desired cell (0-8).
     * @return the JTextField at the specified location in the Sudoku grid.
     * @throws IndexOutOfBoundsException if the row or column index is out of bounds.
     */
    public JTextField getCellAt(int row, int col) {
        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            throw new IndexOutOfBoundsException("Invalid cell index");
        }

        int blockRow = row / 3;
        int blockCol = col / 3;
        int cellIndexInBlock = (row % 3) * 3 + (col % 3);
        JPanel subGrid = (JPanel) mainBoardPanel.getComponent(blockRow * 3 + blockCol);

        return (JTextField) subGrid.getComponent(cellIndexInBlock);
    }

    /**
     * Returns the main board panel containing the 9x9 Sudoku grid.
     *
     * @return the main board panel with the Sudoku cells.
     */
    public JPanel getBoardPanel() {
        return mainBoardPanel;
    }
}
