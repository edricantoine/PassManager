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

//GUI for password manager project. Represents the "main" panel.


public class PassManagerAppGui implements ListSelectionListener {
    private static final String JSON_LOC = "./data/pmanager.json";
    private PassManager manager;
    private PassManagerAppGui pp;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame frame;

    private JButton addEntryButton;
    private JButton removeEntryButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton goToSearchButton;
    private JButton gotoModButton;

    private JCheckBox importantBox;


    private JList<String> entryList;
    private DefaultListModel listModel;

    private JScrollPane listScrollPane;
    private JPanel mainPanel;
    private AddListener addListener;
    private SortListener sortListener;

    private JComboBox<String> categorySelection;
    private JComboBox<String> sortImportant;
    private JComboBox<String> sortCategory;

    private String[] catStrings;

    private JLabel welcomeLabel;

    private SplashScreen splash;



    public PassManagerAppGui() {
        pp = this;

        jsonWriter = new JsonWriter(JSON_LOC);
        jsonReader = new JsonReader(JSON_LOC);

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

    //MODIFIES: this
    //EFFECTS: sets up miscellaneous items, checkboxes, buttons, etc. in Swing

    public void setUpMiscItems() {
        importantBox = new JCheckBox("Make entry important");

        categorySelection = new JComboBox<>(catStrings);

        saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveListener());
        saveButton.setActionCommand("Save");

        loadButton = new JButton("Load");
        loadButton.addActionListener(new LoadListener());
        loadButton.setActionCommand("Load");

        goToSearchButton = new JButton("Search for an entry");
        goToSearchButton.setActionCommand("Search for an entry");
        goToSearchButton.addActionListener(new GoSearchListener());

        gotoModButton = new JButton("Modify entry");
        gotoModButton.setActionCommand("Modify entry");
        gotoModButton.addActionListener(new GoModListener());
        gotoModButton.setEnabled(false);

        welcomeLabel = new JLabel("Welcome! You currently have " + manager.getNumEntries()
                + " entry/entries of which " + manager.getNumImportantEntries() + " are important.");

        sortImportant = new JComboBox<>(new String[]{"All entries", "Important only"});
        sortImportant.addActionListener(new SortListener());

        sortCategory = new JComboBox<>(new String[]{"All entries", "WORK", "ENTERTAINMENT", "FINANCE",
                "DEVICES", "OTHER"});
        sortCategory.addActionListener(new SortListener());


    }

    //MODIFIES: this
    //EFFECTS: sets up buttons and fields in Swing

    public void setUpButtonsAndFields() {
        addEntryButton = new JButton("Add entry");
        addListener = new AddListener(addEntryButton);
        addEntryButton.addActionListener(addListener);
        addEntryButton.setActionCommand("Add entry");
        addEntryButton.setEnabled(true);

        removeEntryButton = new JButton("Remove entry");
        removeEntryButton.addActionListener(new RemoveListener());
        removeEntryButton.setActionCommand("Remove entry");
        removeEntryButton.setEnabled(false);

    }

    //MODIFIES: this
    //EFFECTS: sets up the main panel in Swing

    public void setUpPane() {
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));

        mainPane.add(welcomeLabel);
        mainPane.add(sortImportant);
        mainPane.add(sortCategory);
        mainPane.add(importantBox);
        mainPane.add(addEntryButton);

        mainPane.add(Box.createHorizontalStrut(3));
        mainPane.add(new JSeparator(SwingConstants.VERTICAL));
        mainPane.add(Box.createHorizontalStrut(3));

        mainPane.add(removeEntryButton);
        mainPane.add(goToSearchButton);
        mainPane.add(gotoModButton);
        mainPane.add(saveButton);
        mainPane.add(loadButton);

        mainPane.setOpaque(true);

        mainPanel.add(listScrollPane, BorderLayout.CENTER);
        mainPanel.add(mainPane, BorderLayout.PAGE_END);

    }

    //This method references code from the JsonSerializationDemo repo.
    //Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    //MODIFIES: this
    //EFFECTS: Reads data from file
    private void loadPassManager() {
        try {
            manager = jsonReader.read();
            System.out.println("Successfully loaded data from" + JSON_LOC);
        } catch (IOException e) {
            System.out.println("Error reading from " + JSON_LOC
                    + ". Ignore message if this is first usage of application.");
        }
    }


    //MODIFIES: this
    //EFFECTS: converts a pass manager into elements in a ListModel
    private void pmToList() {
        for (Entry e : manager.getEntries()) {
            listModel.insertElementAt(e.entryString(), 0);
        }
    }

    //MODIFIES: this
    //EFFECTS: converts a pass manager into elements in a ListModel with only important entries
    private void pmToImportantList() {
        for (Entry e : manager.getEntries()) {
            if (e.getImportant()) {
                listModel.insertElementAt(e.entryString(), 0);
            }
        }
    }
    //MODIFIES: this
    //EFFECTS: converts a pass manager into elements in a ListModel with only entries of a certain category

    private void pmToListOfType(EntryType e) {
        for (Entry h : manager.getEntries()) {
            if (h.getType().equals(e)) {
                listModel.insertElementAt(h.entryString(), 0);
            }
        }
    }
    //MODIFIES: this
    //EFFECTS: converts a pass manager into elements in a ListModel with only important entries of a certain
    //         category

    private void pmToImportantListOfType(EntryType e) {
        for (Entry h : manager.getEntries()) {
            if (h.getImportant() && h.getType().equals(e)) {
                listModel.insertElementAt(h.entryString(), 0);
            }
        }
    }

    //MODIFIES: this
    //initializes JFrame and displays GUI

    private void displayGUI() {
        frame = new JFrame("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.setOpaque(true);
        frame.setContentPane(mainPanel);

        frame.pack();
        frame.setVisible(true);
    }

    //MODIFIES: this
    //sets welcomeLabel to display correct num of entries and important entries

    private void displayWelcomeLabel() {
        welcomeLabel.setText("Welcome! You currently have " + manager.getNumEntries()
                + " entry/entries of which " + manager.getNumImportantEntries() + " are important.");
    }

    //MODIFIES: this
    //EFFECTS: sets modify and remove buttons to false if there are no valid entries to operate on

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            if (entryList.getSelectedIndex() == -1) {
                removeEntryButton.setEnabled(false);
                gotoModButton.setEnabled(false);

            } else {
                removeEntryButton.setEnabled(true);
                gotoModButton.setEnabled(true);
            }
        }
    }

    //AddListener opens a new AddTool, where the user can then add a new entry

    public class AddListener implements ActionListener {
        private boolean enabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //MODIFIES: this
        //EFFECTS: opens a new AddTool, sets selected index to a valid entry afterward

        @Override
        public void actionPerformed(ActionEvent e) {

            int index = entryList.getSelectedIndex();
            new AddTool(manager, pp, index);

            entryList.setSelectedIndex(index);
            entryList.ensureIndexIsVisible(index);


        }

    }

    public class RemoveListener implements ActionListener {

        //MODIFIES: this
        //EFFECTS: removes entry from password manager

        @Override
        public void actionPerformed(ActionEvent e) {

            Entry theEntry = manager.retrieveEntryFromStringValue(entryList.getSelectedValue());
            manager.removeEntry(theEntry);
            int index = entryList.getSelectedIndex();
            listModel.remove(index);
            displayWelcomeLabel();
            frame.pack();


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

    public class SaveListener implements ActionListener {

        //MODIFIES: this
        //EFFECTS: saves data to JSON file

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                jsonWriter.openFile();
                jsonWriter.write(manager);
                jsonWriter.close();
            } catch (FileNotFoundException f) {
                Toolkit.getDefaultToolkit().beep();
            }

        }
    }

    public class LoadListener implements ActionListener {

        //MODIFIES: this
        //EFFECTS: loads data from JSON file

        @Override
        public void actionPerformed(ActionEvent e) {
            loadPassManager();
            refresh();
            int size = listModel.getSize();
            if (size != 0) {
                loadButton.setEnabled(false);
            }
        }
    }

    public class GoSearchListener implements ActionListener {


        //EFFECTS: opens a new SearchTool

        @Override
        public void actionPerformed(ActionEvent e) {
            SearchTool searcher = new SearchTool(manager);
        }
    }

    public class GoModListener implements ActionListener {

        //MODIFIES: this
        //EFFECTS: opens a new ModifyTool, and if the entry list is empty sets button to be disabled
        @Override
        public void actionPerformed(ActionEvent e) {

            Entry theEntry = manager.retrieveEntryFromStringValue(entryList.getSelectedValue());
            ModifyTool modder = new ModifyTool(manager, theEntry, pp);

            int size = listModel.getSize();

            if (size == 0) {
                gotoModButton.setEnabled(false);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: "refreshes" the password manager: clears lists then creates a new one, also updating the label

    public void refresh() {
        listModel.clear();
        pmToList();
        displayWelcomeLabel();
        frame.pack();
    }

    public class SortListener implements ActionListener {

        //MODIFIES: this
        //EFFECTS: sorts entries in list box by importance, category, or both

        @Override
        public void actionPerformed(ActionEvent e) {
            String sortingByImportance = (String)sortImportant.getSelectedItem();
            String categoryToSortBy = (String)sortCategory.getSelectedItem();

            EntryType chosenType = chooseType(categoryToSortBy);

            handleSort(sortingByImportance, categoryToSortBy, chosenType);
        }

        //EFFECTS: takes selected category in combobox and returns its corresponding EntryType

        public EntryType chooseType(String categoryToSortBy) {
            if (!categoryToSortBy.equals("All entries")) {
                return EntryType.valueOf(categoryToSortBy);
            } else {
                return EntryType.OTHER;
            }
        }

        //MODFIES: this
        //EFFECTS: causes the list to only display entries of types/importance that the user has selected

        public void handleSort(String sortingByImportance, String categoryToSortBy, EntryType chosenType) {
            if (sortingByImportance.equals("Important only")) {
                if (!categoryToSortBy.equals("All entries")) {
                    listModel.clear();
                    pmToImportantListOfType(chosenType);
                    addEntryButton.setEnabled(false);
                } else {
                    listModel.clear();
                    pmToImportantList();
                    addEntryButton.setEnabled(false);
                }
            } else {
                if (!categoryToSortBy.equals("All entries")) {
                    listModel.clear();
                    pmToListOfType(chosenType);
                    addEntryButton.setEnabled(false);
                } else {
                    listModel.clear();
                    pmToList();
                    addEntryButton.setEnabled(true);
                }
            }
        }
    }



}
