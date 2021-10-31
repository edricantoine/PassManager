package ui;

import exceptions.DuplicateLabelException;
import exceptions.InvalidLengthException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

//This class references code from the ListDemoProject repo.
//Link: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

//GUI for password manager project


public class PassManagerAppGui implements ListSelectionListener {
    private static final String JSON_LOC = "./data/pmanager.json";
    private PassManager manager;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private JButton addEntryButton;
    private JButton removeEntryButton;
    private JCheckBox importantBox;
    private JTextField labelField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JList<String> entryList;
    private DefaultListModel listModel;
    private JScrollPane listScrollPane;
    private JPanel mainPanel;
    private AddListener addListener;
    private JComboBox<String> categorySelection;
    private String[] catStrings;



    public PassManagerAppGui() {
        catStrings = new String[]{"WORK", "ENTERTAINMENT", "FINANCE", "DEVICES", "OTHER"};
        mainPanel = new JPanel();
        manager = new PassManager();
        listModel = new DefaultListModel();
        entryList = new JList<>(listModel);
        entryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        entryList.setSelectedIndex(0);
        entryList.addListSelectionListener(this);
        entryList.setVisibleRowCount(5);
        listScrollPane = new JScrollPane(entryList);
        setUpButtonsAndFields();
        setUpMiscItems();
        setUpPane();
        displayGUI();



    }

    public void setUpMiscItems() {
        importantBox = new JCheckBox("Make entry important");
        categorySelection = new JComboBox<>(catStrings);


    }

    public void setUpButtonsAndFields() {
        addEntryButton = new JButton("Add entry");
        addListener = new AddListener(addEntryButton);
        addEntryButton.addActionListener(addListener);
        addEntryButton.setActionCommand("Add entry");
        addEntryButton.setEnabled(false);

        removeEntryButton = new JButton("Remove entry");
        removeEntryButton.addActionListener(new RemoveListener());
        removeEntryButton.setActionCommand("Remove entry");

        labelField = new JTextField(10);
        labelField.addActionListener(addListener);
        labelField.getDocument().addDocumentListener(addListener);

        usernameField = new JTextField(10);
        usernameField.addActionListener(addListener);

        passwordField = new JTextField(10);
        passwordField.addActionListener(addListener);

        labelField.setText("Label");
        usernameField.setText("Username");
        passwordField.setText("Password");
    }

    public void setUpPane() {
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.LINE_AXIS));
        mainPane.add(labelField);
        mainPane.add(usernameField);
        mainPane.add(passwordField);
        mainPane.add(importantBox);
        mainPane.add(categorySelection);
        mainPane.add(addEntryButton);
        mainPane.add(Box.createHorizontalStrut(3));
        mainPane.add(new JSeparator(SwingConstants.VERTICAL));
        mainPane.add(Box.createHorizontalStrut(3));
        mainPane.add(removeEntryButton);
        mainPane.setOpaque(true);
        mainPanel.add(listScrollPane, BorderLayout.CENTER);
        mainPanel.add(mainPane, BorderLayout.PAGE_END);

    }



    private void pmToList() {
        for (Entry e : manager.getEntries()) {
            listModel.insertElementAt(e.toString(), 0);
        }
    }

    private void displayGUI() {
        JFrame frame = new JFrame("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.setOpaque(true);
        frame.setContentPane(mainPanel);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            if (entryList.getSelectedIndex() == -1) {
                removeEntryButton.setEnabled(false);

            } else {
                removeEntryButton.setEnabled(true);
            }
        }
    }

    public class AddListener implements ActionListener, DocumentListener {
        private boolean enabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String label = labelField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean isImportant = importantBox.isSelected();
            String chosenCat = (String)categorySelection.getSelectedItem();
            EntryType chosenType = EntryType.valueOf(chosenCat);

            try {
                Entry newE = new Entry(label, username, password, isImportant, chosenType);
                int index = entryList.getSelectedIndex();

                if (index == -1) {
                    index = 0;
                } else {
                    index++;
                }

                manager.addEntry(newE);
                listModel.insertElementAt(newE.entryString(), index);

                entryList.setSelectedIndex(index);
                entryList.ensureIndexIsVisible(index);

            } catch (InvalidLengthException | DuplicateLabelException f) {
                Toolkit.getDefaultToolkit().beep();
            }


        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (!enabled) {
                button.setEnabled(true);
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                enabled = false;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(true);
            }
        }
    }

    public class RemoveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Entry theEntry = manager.retrieveEntryFromStringValue(entryList.getSelectedValue());
            manager.removeEntry(theEntry);
            int index = entryList.getSelectedIndex();
            listModel.remove(index);


            int size = listModel.getSize();

            if (size == 0) {
                removeEntryButton.setEnabled(false);

            } else {
                if (index == listModel.getSize()) {

                    index--;
                }

                entryList.setSelectedIndex(index);
                entryList.ensureIndexIsVisible(index);
            }

        }

    }



}
