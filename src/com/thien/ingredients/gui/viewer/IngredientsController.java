package com.thien.ingredients.gui.viewer;

import com.thien.ingredients.gui.utilities.Menu;

public class IngredientsController {

    private static Menu menu;
    
    public void mainMenu(String menutitle) {
        menu = new Menu(menutitle);
        menu.addOption("Manage ingredients");
        menu.addOption("Manage beverage recipes");
        menu.addOption("Dispensing beverages");
        menu.addOption("Report");
        menu.addOption("Store data to files");
        menu.addOption("Exit program");
        // 6 options

        int choice = 0;

        do {
            menu.printMenu();
            menu.getChoice();
             switch (choice) {
                case 1:
                    menuManageIngredients("Manage ingredients");
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:
                    
                    break;
                case 5:
                    
                    break;
                default:
                    System.out.println("------Exit program------");
                    break;
             }
        } while (choice > 0 );
    }

    private void menuManageIngredients(String subMenuTitle) {
        menu = new Menu(subMenuTitle);
        menu.addOption("Add an ingredient");
        menu.addOption("Update ingredient information");
        menu.addOption("Delete ingredient");
        menu.addOption("Show all ingredients");
        menu.addOption("Return to main menu");
        // 5 options

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
                    System.out.println("----Return to main menu----");
                    break;
            }
        } while (choice > 0);
    }


}
