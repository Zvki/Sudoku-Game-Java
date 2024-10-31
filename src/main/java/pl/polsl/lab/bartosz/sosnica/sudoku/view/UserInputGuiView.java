package pl.polsl.lab.bartosz.sosnica.sudoku.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class UserInputGuiView extends JFrame {

    private JTextField usernameField;
    private JComboBox<String> difficultyComboBox;
    private JButton submitButton;
    private String username;
    private String difficulty;

    public UserInputGuiView(){
        LoginGUI();
    }

    public void LoginGUI() {
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

        setLayout(new BorderLayout());
        add(userPanel, BorderLayout.NORTH);
        add(difficultyPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void addSubmitButtonListener(ActionListener listener){
        submitButton.addActionListener(listener);
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getDifficulty() {
        return (String) difficultyComboBox.getSelectedItem();
    }

}
