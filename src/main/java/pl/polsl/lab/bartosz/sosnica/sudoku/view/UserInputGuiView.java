package pl.polsl.lab.bartosz.sosnica.sudoku.view;

import pl.polsl.lab.bartosz.sosnica.sudoku.controller.BoardController;
import pl.polsl.lab.bartosz.sosnica.sudoku.controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class UserInputGuiView extends JFrame {

    private JTextField usernameField;
    private JComboBox<String> difficultyComboBox;
    private JButton submitButton;
    private String username;
    private String difficulty;

    public void LoginGUI(BoardController boardController, UserController userController) {
        setTitle("Login to Sudoku");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel userPanel = new JPanel(new FlowLayout());
        JLabel usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField(15);
        userPanel.add(usernameLabel);
        userPanel.add(usernameField);

        JPanel difficultyPanel = new JPanel(new FlowLayout());
        JLabel difficultyLabel = new JLabel("Difficulty Level:");
        String[] difficulties = {"Easy", "Medium", "Hard"};
        difficultyComboBox = new JComboBox<>(difficulties);
        difficultyPanel.add(difficultyLabel);
        difficultyPanel.add(difficultyComboBox);

        submitButton = new JButton("Start game");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText();
                difficulty = (String) difficultyComboBox.getSelectedItem();
                userController.getUserModel().setUsername(username);
                boardController.getBoardModel().settingDifficultyLevel(difficulty);

                JOptionPane.showMessageDialog(null, "Welcome, " + username + "! Difficulty Level: " + difficulty);

                userController.getSudokuGameView().SudokuGUI(boardController);

                dispose();
            }
        });

        setLayout(new BorderLayout());
        add(userPanel, BorderLayout.NORTH);
        add(difficultyPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);

        setVisible(true);
    }

}
