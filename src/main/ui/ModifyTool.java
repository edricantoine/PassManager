package ui;

import javax.swing.*;

import exceptions.InvalidLengthException;
import model.Entry;
import model.EntryType;
import model.PassManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//A panel that lets the user modify entries

public class ModifyTool {

    private PassManager manager;
    private PassManagerAppGui pmag;
    private Entry entry;
    private JFrame modFrame;
    private JTextField newUser;
    private JTextField newPass;
    private JCheckBox newImp;
    private JComboBox<String> newCat;
    private JButton modButton;
    private JButton backButton;
    private JPanel modPanel;
    private ModListener modListener;

    public ModifyTool(PassManager pm, Entry en, PassManagerAppGui pp) {
        this.manager = pm;
        this.pmag = pp;
        this.entry = en;
        modListener = new ModListener();
        setUpModSwing();
        addToModPane();
        displayModGui();
    }

    //MODIFIES: this
    //EFFECTS: sets up Swing components for modify screen

    public void setUpModSwing() {
        this.modFrame = new JFrame();
        this.modPanel = new JPanel();
        newUser = new JTextField(10);
        newUser.setText("New User");
        newPass = new JTextField(10);
        newPass.setText("New Pass");
        newImp = new JCheckBox("Make entry important");
        newCat = new JComboBox<>(new String[]{"WORK", "ENTERTAINMENT", "FINANCE", "DEVICES", "OTHER"});
        modButton = new JButton("Modify entry");
        modButton.setActionCommand("Modify entry");
        modButton.addActionListener(modListener);
        backButton = new JButton("Back");
        backButton.setActionCommand("Back");
        backButton.addActionListener(new QuitListener());
    }

    //MODIFIES: this
    //EFFECTS: sets up main panel for modify screen

    public void addToModPane() {
        JPanel modPane = new JPanel();
        modPane.setLayout(new BoxLayout(modPane, BoxLayout.Y_AXIS));
        modPane.add(newUser);
        modPane.add(newPass);
        modPane.add(newCat);
        modPane.add(newImp);
        modPane.add(modButton);
        modPane.add(backButton);
        modPane.setOpaque(true);
        modPanel.add(modPane, BorderLayout.PAGE_END);
    }

    //MODIFIES: this
    //EFFECTS: sets up GUI for modify screen

    public void displayModGui() {
        modFrame = new JFrame("Modify entry");
        modFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        modPanel.setOpaque(true);
        modFrame.setContentPane(modPanel);

        modFrame.pack();
        modFrame.setVisible(true);
    }

    public class QuitListener implements ActionListener {

        //EFFECTS: disposes of frame

        @Override
        public void actionPerformed(ActionEvent e) {
            modFrame.dispose();
        }
    }

    public class ModListener implements ActionListener {

        //MODIFIES: this, manager, pmag
        //EFFECTS: changes an entry's username, password, importance, category based on user selection

        @Override
        public void actionPerformed(ActionEvent e) {
            String newUs = newUser.getText();
            String newPa = newPass.getText();
            boolean newIm = newImp.isSelected();
            String newCt = (String) newCat.getSelectedItem();
            EntryType newTp = EntryType.valueOf(newCt);

            try {
                entry.setUsername(newUs);
                entry.setPassword(newPa);
                if (newIm) {
                    entry.makeImportant();
                } else {
                    entry.makeUnimportant();
                }

                entry.setType(newTp);

            } catch (InvalidLengthException q) {
                Toolkit.getDefaultToolkit().beep();
            }

            pmag.refresh();

            modFrame.dispose();

        }
    }

}
