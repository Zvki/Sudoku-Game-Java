package pl.polsl.lab.bartosz.sosnica.sudoku.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class UserInputGuiView extends JFrame {

    private JTextField usernameField;
    private JComboBox<String> difficultyComboBox;
    private JButton submitButton;
    private JTable historyTable;
    private DefaultTableModel tableModel;

    public UserInputGuiView(){
        LoginGUI();
    }

    public void LoginGUI() {
        setTitle("Login to Sudoku");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Główny panel z GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(mainPanel);

        // Panel użytkownika (username) - centrowanie
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField(15);
        userPanel.add(usernameLabel);
        userPanel.add(usernameField);

        // Ustawienia dla userPanel w GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(userPanel, gbc);

        // Panel trudności (difficulty) - centrowanie
        JPanel difficultyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel difficultyLabel = new JLabel("Difficulty Level:");
        String[] difficulties = {"Easy", "Medium", "Hard"};
        difficultyComboBox = new JComboBox<>(difficulties);
        difficultyPanel.add(difficultyLabel);
        difficultyPanel.add(difficultyComboBox);

        // Ustawienia dla difficultyPanel w GridBagLayout
        gbc.gridy = 1;
        mainPanel.add(difficultyPanel, gbc);

        // Panel przycisku "Start game" - centrowanie
        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitButton = new JButton("Start game");
        submitPanel.add(submitButton);

        // Ustawienia dla submitPanel w GridBagLayout
        gbc.gridy = 2;
        mainPanel.add(submitPanel, gbc);

        // Tabela historii gry
        String[] columnNames = {"Username", "Date", "Difficulty"};
        tableModel = new DefaultTableModel(columnNames, 0);
        historyTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setPreferredSize(new Dimension(350, 75));

        // Ustawienia dla scrollPane w GridBagLayout
        gbc.gridy = 3;
        gbc.weighty = 1.0; // Dodajemy wagę dla tabeli, aby była "rozciągnięta" w pionie
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbc);

        setVisible(true);
    }

    public void addSubmitButtonListener(ActionListener listener){
        submitButton.addActionListener(listener);
    }

    public void addGameRecord(String username, String date, String difficulty) {
        tableModel.addRow(new Object[]{username, date, difficulty});
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
