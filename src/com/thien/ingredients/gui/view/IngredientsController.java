package com.thien.ingredients.gui.view;

import com.thien.ingredients.bussiness.services.ManageIngredientDAO;
import com.thien.ingredients.gui.utilities.DataInputter;
import com.thien.ingredients.gui.utilities.Menu;

public class IngredientsController {

    private static Menu menu;
    private String ingredientPathFile, menuPathFile, orderPathFile;

    public IngredientsController(String ingredientPathFile, String menuPathFile, String orderPathFile) {
        this.ingredientPathFile = ingredientPathFile;
        this.menuPathFile = menuPathFile;
        this.orderPathFile = orderPathFile;
    }
    
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
                    menuManageIngredients("Manage ingredients", ingredientPathFile);
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

    private void menuManageIngredients(String subMenuTitle, String ingredientPathFile) {
        menu = new Menu(subMenuTitle);
        menu.addOption("Add an ingredient");
        menu.addOption("Update ingredient information");
        menu.addOption("Delete ingredient");
        menu.addOption("Show all ingredients");
        menu.addOption("Return to main menu");
        // 5 options

        ManageIngredientDAO manageIngredientDAO = new ManageIngredientDAO(ingredientPathFile);

        int choice = 0;

        do {
            menu.printMenu();
            menu.getChoice();
            switch (choice) {
                case 1:
                    manageIngredientDAO.addNew();
                    break;
                case 2:
                    manageIngredientDAO.update(DataInputter.getNonBlankString("Enter ingredient ID to update", "ID cannot be blank"));
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
