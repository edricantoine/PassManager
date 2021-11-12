package ui;

import javax.swing.*;
import model.PassManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//represents a panel that allows the user to search for an entry by label

public class SearchTool {
    private PassManager manager;
    private JFrame searchFrame;
    private JTextField searchField;
    private JButton searchButton;
    private JButton backButton;
    private JLabel searchLabel;
    private JLabel resultLabel;
    private JPanel searchPanel;
    private SearchListener searchListener;

    public SearchTool(PassManager pm) {
        this.manager = pm;
        searchListener = new SearchListener();
        setUpSearchSwing();
        addToSearchPane();
        displaySearchGUI();
    }

    //MODIFIES: this
    //EFFECTS: sets up Swing components for search screen

    public void setUpSearchSwing() {

        this.searchFrame = new JFrame();
        this.searchPanel = new JPanel();
        searchField = new JTextField(10);
        searchLabel = new JLabel("Enter the label you want to search for.");
        resultLabel = new JLabel();
        this.searchButton = new JButton("Search for entry");
        searchButton.setActionCommand("Search for entry");
        searchButton.addActionListener(searchListener);
        this.backButton = new JButton("Back");
        backButton.setActionCommand("Back");
        backButton.addActionListener(new QuitListener());
    }

    //MODIFIES: this
    //EFFECTS: sets up main panel for search screen

    public void addToSearchPane() {
        JPanel searchPane = new JPanel();
        searchPane.setLayout(new BoxLayout(searchPane, BoxLayout.Y_AXIS));
        searchPane.add(searchLabel);
        searchPane.add(searchField);
        searchPane.add(searchButton);
        searchPane.add(resultLabel);
        searchPane.add(backButton);
        searchPane.setOpaque(true);
        searchPanel.add(searchPane, BorderLayout.PAGE_END);
    }

    //MODIFIES: this
    //EFFECTS: sets up GUI for search screen

    public void displaySearchGUI() {
        searchFrame = new JFrame("Search");
        searchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        searchPanel.setOpaque(true);
        searchFrame.setContentPane(searchPanel);

        searchFrame.pack();
        searchFrame.setVisible(true);
    }

    public class SearchListener implements ActionListener {

        //MODIFIES: this
        //EFFECTS: Searches for entry based on label the user types

        @Override
        public void actionPerformed(ActionEvent e) {

            String searchValue = searchField.getText();
            if (searchValue.length() == 0) {
                Toolkit.getDefaultToolkit().beep();
            } else {
                String valueToDisplay = manager.retrieveString(searchValue);
                resultLabel.setText(valueToDisplay);
                searchFrame.pack();
            }

        }
    }

    public class QuitListener implements ActionListener {

        //EFFECTS: disposes of frame

        @Override
        public void actionPerformed(ActionEvent e) {
            searchFrame.dispose();
        }
    }
}
