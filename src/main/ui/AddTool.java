package ui;

import exceptions.DuplicateLabelException;
import exceptions.InvalidLengthException;
import model.Entry;
import model.EntryType;
import model.PassManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//A class representing a panel that allows entries to be added

public class AddTool {

    private PassManager manager;
    private PassManagerAppGui pmag;
    private int idx;
    private JFrame addFrame;
    private JTextField newLab;
    private JTextField newUser;
    private JTextField newPass;
    private JCheckBox newImp;
    private JComboBox<String> newCat;
    private JButton addButton;
    private JButton backButton;
    private JPanel addPanel;
    private JLabel errorLabel;
    private AddListener addListener;

    public AddTool(PassManager pm, PassManagerAppGui pp, int index) {
        this.manager = pm;
        this.pmag = pp;
        this.idx = index;
        setUpAddSwing();
        addToModPane();
        displayModGui();
    }

    //MODIFIES: this
    //EFFECTS: sets up Swing components of entry adding screen

    public void setUpAddSwing() {
        addListener = new AddListener(addButton);
        this.addFrame = new JFrame();
        this.addPanel = new JPanel();
        newLab = new JTextField(10);
        newLab.setText("Label");
        newUser = new JTextField(10);
        newUser.setText("Username");
        newPass = new JTextField(10);
        newPass.setText("Password");
        errorLabel = new JLabel("");
        newImp = new JCheckBox("Make entry important");
        newCat = new JComboBox<>(new String[]{"WORK", "ENTERTAINMENT", "FINANCE", "DEVICES", "OTHER"});
        addButton = new JButton("Add entry");
        addButton.setActionCommand("Add entry");
        addButton.addActionListener(addListener);
        backButton = new JButton("Back");
        backButton.setActionCommand("Back");
        backButton.addActionListener(new QuitListener());

    }

    //MODIFIES: this
    //EFFECTS: sets up main panel of entry adding screen

    public void addToModPane() {
        JPanel addPane = new JPanel();
        addPane.setLayout(new BoxLayout(addPane, BoxLayout.Y_AXIS));

        addPane.add(newLab);
        addPane.add(newUser);
        addPane.add(newPass);

        addPane.add(newCat);
        addPane.add(newImp);

        addPane.add(addButton);
        addPane.add(errorLabel);
        addPane.add(backButton);

        addPane.setOpaque(true);
        addPanel.add(addPane, BorderLayout.PAGE_END);
    }

    //MODIFIES: this
    //EFFECTS: sets up GUI of entry adding screen

    public void displayModGui() {
        addFrame = new JFrame("Add entry");
        addFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addPanel.setOpaque(true);
        addFrame.setContentPane(addPanel);

        addFrame.pack();
        addFrame.setVisible(true);
    }

    public class QuitListener implements ActionListener {

        //EFFECTS: disposes of frame

        @Override
        public void actionPerformed(ActionEvent e) {
            addFrame.dispose();
        }
    }

    public class AddListener implements ActionListener, DocumentListener {
        private boolean enabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //MODIFIES: this, manager, pmag
        //EFFECTS: creates a new entry based on the user's selections and adds it to manager

        @Override
        public void actionPerformed(ActionEvent e) {

            String label = newLab.getText();
            String username = newUser.getText();
            String password = newPass.getText();
            boolean isImportant = newImp.isSelected();
            String chosenCat = (String)newCat.getSelectedItem();
            EntryType chosenType = EntryType.valueOf(chosenCat);

            try {
                Entry newE = new Entry(label, username, password, isImportant, chosenType);
                if (idx == -1) {
                    idx = 0;
                } else {
                    idx++;
                }
                manager.addEntry(newE);
                pmag.refresh();
                addFrame.dispose();

            } catch (DuplicateLabelException f) {
                errorLabel.setText("An entry with that name already exists.");
                addFrame.pack();
            } catch (InvalidLengthException x) {
                errorLabel.setText("One or more fields were empty.");
                addFrame.pack();
            }



        }

        //MODIFIES: this
        //EFFECTS: if the button is disabled, enable it

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (!enabled) {
                button.setEnabled(true);
            }
        }

        //MODIFIES: this
        //EFFECTS: if document event's document is zero entries long, sets button to be disabled
        //         (for remove events)

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                enabled = false;
            }
        }

        //MODIFIES: this
        //EFFECTS: if document event's document is zero entries long, sets button to be enabled
        //         (for change/update events)
        @Override
        public void changedUpdate(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(true);
            }
        }
    }

}
