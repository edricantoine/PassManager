package ui;

import model.*;

import java.util.Scanner;

//Password manager application, handles console UI and user input

public class PassManagerApp {
    private PassManager manager;
    private Scanner input;

    public PassManagerApp() {
        runPassManager();
    }

    //MODIFIES: this
    //EFFECTS: displays welcome menu, prompts user for input, handles input
    private void runPassManager() {
        boolean repeat = true;
        String initial;
        initializeManager();

        while (repeat) {
            welcomeMenu();
            initial = input.next();
            initial = initial.toLowerCase();

            if (initial.equals("x")) {
                repeat = false;
            } else {
                useCommand(initial);
            }

        }

        System.out.println("See you soon!");

    }

    //MODIFIES: this
    //EFFECTS: chooses what to do based on user input
    private void useCommand(String initial) {
        if (initial.equals("a")) {
            addToManager();
        } else if (manager.getNumEntries() > 0) {
            if (initial.equals("m")) {
                modifyRemove();
            } else if (initial.equals("s")) {
                searchEntries();
            } else if (initial.equals("d")) {
                displayEntries();
            } else if (initial.equals("i") && manager.getNumImportantEntries() > 0) {
                displayImportantEntries();
            }
        } else {
            System.out.println("Not a valid command");
        }
    }

    //REQUIRES: user types label, username, password all with length > 0, AND label does not already
    //          exist in password manager.
    //MODIFIES: this
    //EFFECTS: prompts user for label, username, password, and importance, and adds entry with
    //         matching values to password manager
    private void addToManager() {
        String userWeb;
        String userName;
        String userPass;
        String userImportant;
        System.out.println("Choose a label for this entry: ");
        userWeb = input.next();
        System.out.println("Please enter your username: ");
        userName = input.next();
        System.out.println("Please enter the password: ");
        userPass = input.next();
        System.out.println("Would you like to mark this entry as important?");
        System.out.println("y : yes");
        System.out.println("n : no");
        userImportant = input.next();

        if (userImportant.equals("y")) {

            manager.addEntry(new Entry(userWeb, userName, userPass, true, EntryType.OTHER));
            System.out.println("Successfully added.");

        } else if (userImportant.equals("n")) {

            manager.addEntry(new Entry(userWeb, userName, userPass, false, EntryType.OTHER));
            System.out.println("Successfully added.");

        } else {

            System.out.println("Invalid input.");

        }
    }

    //MODIFIES: this
    //EFFECTS: prompts user for a label, searches for an entry with matching label, then handles user input
    public void modifyRemove() {

        Entry chosenEntry;
        String chosenWeb;
        System.out.println("Type the label of the entry you would like to modify:");
        chosenWeb = input.next();
        chosenEntry = manager.retrieveEntry(chosenWeb);
        if (chosenEntry == null) {
            System.out.println("Entry not found in list.");
        } else {
            chooseAction(chosenEntry);
        }


    }

    //MODIFIES: this, ce
    //EFFECTS: handles user input in terms of modifying and removing entries
    public void chooseAction(Entry chosenEntry) {
        String choice;
        modifyRemoveMenu();
        choice = input.next();
        switch (choice) {
            case "u":
                handleChangeUsername(chosenEntry);
                break;
            case "p":
                handleChangePass(chosenEntry);
                break;
            case "i":
                handleToggleImportant(chosenEntry);
                break;
            case "r":
                handleRemoval(chosenEntry);
                break;
            case "t":
                handleCategory(chosenEntry);
                break;
            default:
                System.out.println("Invalid input.");
                break;
        }
    }

    //REQUIRES: new username has length > 0
    //MODIFIES: this, ce
    //EFFECTS: prompts the user for a new username, changes the chosen entry's username to the new one
    private void handleChangeUsername(Entry ce) {
        String newName;
        System.out.println("Type the new username here: ");
        newName = input.next();
        ce.setUsername(newName);
        System.out.println("Great! Username changed to " + ce.getUsername());
    }

    //REQUIRES: new password has length > 0
    //MODIFIES: this, ce
    //EFFECTS: prompts user for a new password, changes chosen entry's password to the new one
    private void handleChangePass(Entry ce) {
        String newPass;
        System.out.println("Type the new password here: ");
        newPass = input.next();
        ce.setPassword(newPass);
        System.out.println("Great! Password changed to " + ce.getPassword());
    }

    //MODIFIES: this, ce
    //EFFECTS: if ce is important, makes unimportant, and vice versa
    private void handleToggleImportant(Entry ce) {
        if (ce.getImportant()) {
            ce.makeUnimportant();
            System.out.println("This entry is no longer important");
        } else {
            ce.makeImportant();
            System.out.println("This entry is now important");
        }
    }

    //MODIFIES: this
    //EFFECTS: removes chosen entry from password manager
    private void handleRemoval(Entry ce) {
        manager.removeEntry(ce);
        System.out.println("Entry successfully removed");
    }

    //MODIFIES: this, ce
    //EFFECTS: prompts user for an input, changes ce's category based on input
    private void handleCategory(Entry ce) {
        String chosenCategory;
        System.out.println("Select a category for this entry: ");
        displayCategoryMenu();
        chosenCategory = input.next();
        if (chosenCategory.equals("w")) {
            ce.setType(EntryType.WORK);
            System.out.println("Category set to: Work");
        } else if (chosenCategory.equals("f")) {
            ce.setType(EntryType.FINANCE);
            System.out.println("Category set to: Finance");
        } else if (chosenCategory.equals("e")) {
            ce.setType(EntryType.ENTERTAINMENT);
            System.out.println("Category set to: Entertainment");
        } else if (chosenCategory.equals("d")) {
            ce.setType(EntryType.DEVICES);
            System.out.println("Category set to: Devices");
        } else if (chosenCategory.equals("o")) {
            ce.setType(EntryType.OTHER);
            System.out.println("Category set to: Other");
        } else {
            System.out.println("Invalid category.");
        }
    }

    //EFFECTS: displays a menu for categories and their corresponding inputs
    private void displayCategoryMenu() {
        System.out.println("w: work");
        System.out.println("f: finance");
        System.out.println("e: entertainment");
        System.out.println("d: devices");
        System.out.println("o: other");
    }

    //EFFECTS: displays a menu for how to modify or remove entries
    private void modifyRemoveMenu() {
        System.out.println("t: change category");
        System.out.println("u: change username");
        System.out.println("p: change password");
        System.out.println("i: toggle importance");
        System.out.println("r: remove this entry");

    }

    //EFFECTS: searches for an entry, if found, returns its string format
    private void searchEntries() {
        String searchSite;
        System.out.println("Label name (BE EXACT WITH SPELLING): ");
        searchSite = input.next();
        System.out.println(manager.retrieveString(searchSite));
    }

    //EFFECTS: displays string format for all entries in password manager, has functionality for sorting
    //         by category
    private void displayEntries() {
        EntryType sortType;
        boolean isSorting = askSort();
        if (isSorting) {
            sortType = chooseCategoryToSortBy();
            System.out.println("You have " + manager.getNumEntriesOfType(sortType)
                    + " in the category " + sortType + ".");
            for (Entry e : manager.getEntries()) {
                if (e.getType().equals(sortType)) {
                    System.out.println(e.entryString());
                }

            }
        } else {
            for (Entry e : manager.getEntries()) {
                System.out.println(e.entryString());
            }
        }

    }

    //EFFECTS: displays string format for all important entries in password manager,
    //         has functionality for sorting by category
    private void displayImportantEntries() {
        EntryType sortType;
        boolean isSorting = askSort();
        if (isSorting) {
            sortType = chooseCategoryToSortBy();
            System.out.println("You have " + manager.getNumImportantEntriesOfType(sortType)
                    + " important entries in the category " + sortType + ".");
            for (Entry e : manager.getEntries()) {
                if (e.getType().equals(sortType) && e.getImportant()) {
                    System.out.println(e.entryString());
                }

            }
        } else {
            for (Entry e : manager.getEntries()) {
                if (e.getImportant()) {
                    System.out.println(e.entryString());
                }

            }
        }

    }

    //EFFECTS: prompts user for a category to sort by, returns that category
    private EntryType chooseCategoryToSortBy() {
        String categoryChoice;
        System.out.println("Select category to sort by: ");
        displayCategoryMenu();
        categoryChoice = input.next();
        return handleCategoryForSort(categoryChoice);
    }

    //EFFECTS: chooses category to return based on user input. If invalid input received, defaults to sorting
    //         by "Other".
    private EntryType handleCategoryForSort(String choice) {

        if (choice.equals("w")) {
            return EntryType.WORK;
        } else if (choice.equals("f")) {
            return EntryType.FINANCE;
        } else if (choice.equals("e")) {
            return EntryType.ENTERTAINMENT;
        } else if (choice.equals("d")) {
            return EntryType.DEVICES;
        } else if (choice.equals("o")) {
            return EntryType.OTHER;
        } else {
            System.out.println("Invalid category. Sorting by: OTHER.");
            return EntryType.OTHER;
        }
    }

    //EFFECTS: returns true if user prompts to sort, false otherwise
    private boolean askSort() {
        String sortChoice;
        System.out.println("Would you like to sort by category?");
        System.out.println("y: yes");
        System.out.println("Anything else: no");
        sortChoice = input.next();
        return sortChoice.equals("y");

    }


    //EFFECTS: displays welcome menu with options and their corresponding user inputs
    private void welcomeMenu() {
        System.out.println("Welcome to Edric's Password Manager.");
        System.out.println("You currently have " + manager.getNumEntries() + " password(s) saved.");
        System.out.println("You have " + manager.getNumImportantEntries() + " important password(s) saved.");
        System.out.println("What would you like to do?");
        System.out.println("a: add an entry");
        if (manager.getNumEntries() > 0) {
            System.out.println("m: modify or remove an entry");
            System.out.println("s: search for an entry by label");
            System.out.println("d: display a list of all entries");
            if (manager.getNumImportantEntries() > 0) {
                System.out.println("i: display a list of all important entries");
            }
        }
        System.out.println("x: exit");

    }

    //EFFECTS: initializes new password manager and scanner
    private void initializeManager() {
        manager = new PassManager();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

}
