package pl.polsl.lab.bartosz.sosnica.sudoku.view;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 * <p>
 * The UserInputGuiView class represents the login interface for the Sudoku game.
 * It provides a GUI for the user to enter their username, select a difficulty level,
 * and view a history of previous game records.
 * </p>
 * <p>This class is part of the View layer in the MVC architecture, responsible for
 * gathering user input before starting the game.</p>
 *
 * @author Bartosz Sośnica
 * @version 1.0
 *
 * @see pl.polsl.lab.bartosz.sosnica.sudoku.controller.MainController
 */
public class UserInputGuiView extends JFrame {

    /**
     * The text field for entering the player's username.
     */
    private JTextField usernameField;

    /**
     * The combo box for selecting the difficulty level of the game.
     */
    private JComboBox<String> difficultyComboBox;

    /**
     * The button to submit the user's input and start the game.
     * -- GETTER --
     *  Retrieves the submit button component, allowing other classes to customize or manipulate it if necessary.
     *
     * @return the submit button used to start the game.
     */
    @Getter
    private JButton submitButton;

    /**
     * The table that displays the game history records, including username, date, difficulty, and game status.
     */
    private JTable historyTable;

    /**
     * The table model for managing the data in the history table.
     */
    private DefaultTableModel tableModel;

    /**
     * Constructor that initializes the user input GUI.
     * Sets up the layout for username input, difficulty selection, a submit button, and a game history table.
     */
    public UserInputGuiView() {
        initializeLoginGUI();
    }

    /**
     * Sets up the main components of the login GUI.
     * This includes the username input field, difficulty level combo box,
     * submit button, and the game history table with column headers.
     */
    private void initializeLoginGUI() {
        setTitle("Login to Sudoku");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(mainPanel);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField(15);
        userPanel.add(usernameLabel);
        userPanel.add(usernameField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(userPanel, gbc);

        JPanel difficultyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel difficultyLabel = new JLabel("Difficulty Level:");
        String[] difficulties = {"Easy", "Medium", "Hard"};
        difficultyComboBox = new JComboBox<>(difficulties);
        difficultyPanel.add(difficultyLabel);
        difficultyPanel.add(difficultyComboBox);

        gbc.gridy = 1;
        mainPanel.add(difficultyPanel, gbc);

        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitButton = new JButton("Start game");
        submitPanel.add(submitButton);

        gbc.gridy = 2;
        mainPanel.add(submitPanel, gbc);

        String[] columnNames = {"Username", "Date", "Difficulty", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        historyTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setPreferredSize(new Dimension(350, 75));

        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbc);

        setVisible(true);
    }

    /**
     * Adds an ActionListener to the submit button to handle the start game action.
     *
     * @param listener the ActionListener to be added to the submit button.
     */
    public void addSubmitButtonListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    /**
     * Adds a record to the history table displaying details of a game.
     *
     * @param username   the username of the player.
     * @param date       the date when the game was played.
     * @param difficulty the difficulty level selected for the game.
     * @param status     the outcome or status of the game (e.g., "Completed", "In Progress").
     */
    public void addGameRecord(String username, String date, String difficulty, String status) {
        tableModel.addRow(new Object[]{username, date, difficulty, status});
    }

    /**
     * Retrieves the username entered by the user.
     *
     * @return the username as a String.
     */
    public String getUsername() {
        return usernameField.getText();
    }

    /**
     * Retrieves the difficulty level selected by the user from the combo box.
     *
     * @return the selected difficulty level as a String (e.g., "Easy", "Medium", "Hard").
     */
    public String getDifficulty() {
        return (String) difficultyComboBox.getSelectedItem();
    }
}
