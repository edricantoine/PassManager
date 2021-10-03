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
        System.out.println("Adding to manager...");
    }

    private void modifyRemove() {
        System.out.println("Modifying/removing...");
    }

    private void searchEntries() {
        System.out.println("Searching entries...");
    }

    private void displayEntries() {
        System.out.println("Displaying entries...");
    }

    private void displayImportantEntries() {
        System.out.println("Importance");
    }


    private void welcomeMenu() {
        System.out.println("Welcome to Edric's Password Manager.");
        System.out.println("You currently have " + manager.getNumEntries() + " passwords saved.");
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
