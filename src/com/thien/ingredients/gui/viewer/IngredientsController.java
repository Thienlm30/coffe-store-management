package com.thien.ingredients.gui.viewer;

import com.thien.ingredients.gui.utilities.Menu;

public class IngredientsController {

    private static Menu menu;
    
    public static void mainMenu(String menutitle) {
        menu = new Menu(menutitle);
        menu.addOption("Add an ingredient");
        menu.addOption("Update ingredient information");
        menu.addOption("Delete ingredient");
        menu.addOption("Show all ingredients");
        menu.addOption("Other: Return to main menu");

        
        int choice = 0;

        do {
            menu.printMenu();
            menu.getChoice();
             switch (choice) {
                case 1:
                    
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:
                    
                    break;             
                default:
                System.out.println("----------------Main Menu---------------");
                    break;
             }
        } while (choice > 0 );
    }




}
