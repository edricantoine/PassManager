package ui;

import model.*;

import java.util.Scanner;

//Password manager application
public class PassManagerApp {
    private PassManager manager;
    private Scanner input;

    public PassManagerApp() {
        runPassManager();
    }

    private void runPassManager() {
        boolean repeat = true;
        String initial = null;
        initializeManager();

        while (repeat) {
            welcomeMenu();
            initial = input.next();
            initial = initial.toLowerCase();

            if (initial.equals("x")) {
                repeat = false;
            } else {
                doThing(initial);
            }

        }

        System.out.println("See you soon!");

    }

    private void doThing(String initial) {
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

            manager.addEntry(new Entry(userWeb, userName, userPass, true));
            System.out.println("Successfully added.");

        } else if (userImportant.equals("n")) {

            manager.addEntry(new Entry(userWeb, userName, userPass, false));
            System.out.println("Successfully added.");

        } else {

            System.out.println("Invalid input.");

        }
    }

    public void modifyRemove() {
        String choice;
        Entry chosenEntry;
        String chosenWeb;
        System.out.println("Type the label of the entry you would like to modify:");
        chosenWeb = input.next();
        chosenEntry = manager.retrieveEntry(chosenWeb);
        if (chosenEntry == null) {
            System.out.println("Entry not found in list.");
        } else {
            modifyRemoveMenu();
            choice = input.next();
            if (choice.equals("u")) {
                handleChangeUsername(chosenEntry);
            } else if (choice.equals("p")) {
                handleChangePass(chosenEntry);
            } else if (choice.equals("i")) {
                handleToggleImportant(chosenEntry);
            } else if (choice.equals("r")) {
                handleRemoval(chosenEntry);
            } else {
                System.out.println("Invalid input.");
            }
        }


    }

    private void handleChangeUsername(Entry ce) {
        System.out.println("chage");
    }

    private void handleChangePass(Entry ce) {
        System.out.println("chage apss");
    }

    private void handleToggleImportant(Entry ce) {
        System.out.println("impoernt");
    }

    private void handleRemoval(Entry ce) {
        System.out.println("hhh");
    }

    private void modifyRemoveMenu() {
        System.out.println("u: change username");
        System.out.println("p: change password");
        System.out.println("i: toggle importance");
        System.out.println("r: remove this entry");

    }

    private void searchEntries() {
        String searchSite;
        System.out.println("Label name (BE EXACT WITH SPELLING): ");
        searchSite = input.next();
        System.out.println(manager.retrieveString(searchSite));
    }

    private void displayEntries() {
        for (Entry e : manager.getEntries()) {
            System.out.println(e.entryString());
        }
    }

    private void displayImportantEntries() {
        for (Entry e : manager.getEntries()) {
            if (e.getImportant()) {
                System.out.println(e.entryString());
            }
        }
    }


    private void welcomeMenu() {
        System.out.println("Welcome to Edric's Password Manager.");
        System.out.println("You currently have " + manager.getNumEntries() + " password(s)  saved.");
        System.out.println("What would you like to do?");
        System.out.println("a: add an entry");
        if (manager.getNumEntries() > 0) {
            System.out.println("m: modify or remove an entry");
            System.out.println("s: search for an entry by site");
            System.out.println("d: display a list of all entries");
            if (manager.getNumImportantEntries() > 0) {
                System.out.println("i: display a list of all important entries");
            }
        }
        System.out.println("x: exit");

    }

    private void initializeManager() {
        manager = new PassManager();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

}
