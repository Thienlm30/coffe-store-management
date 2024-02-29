package com.thien.ingredients.gui.view;

import com.thien.ingredients.bussiness.components.DataValidation;
import com.thien.ingredients.bussiness.services.DispensingDrinkDAO;
import com.thien.ingredients.bussiness.services.ManageIngredientDAO;
import com.thien.ingredients.bussiness.services.ManageBeverageRecipeDAO;
import com.thien.ingredients.gui.utilities.DataInputter;
import com.thien.ingredients.gui.utilities.Menu;

public class IngredientsController {

    private static Menu menu;
    private String ingredientPathFile, menuPathFile, orderPathFile;
    private DataValidation dataValidation;

    public IngredientsController(String ingredientPathFile, String menuPathFile, String orderPathFile) {
        this.ingredientPathFile = ingredientPathFile;
        this.menuPathFile = menuPathFile;
        this.orderPathFile = orderPathFile;
        this.dataValidation = new DataValidation();
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
            choice = menu.getChoice();
             switch (choice) {
                case 1:
                    menuManageIngredients("Manage ingredients", ingredientPathFile);
                    break;
                case 2:
                    menuManageMenuItem("Manage beverage recipes", menuPathFile, ingredientPathFile);
                    break;
                case 3:
                    menuDispensableDrink(menutitle, ingredientPathFile, menuPathFile, orderPathFile);
                    break;
                case 4:
                    menuReport();
                    break;
                case 5:
                    saveToFile();
                    break;
                default:
                    System.out.println("------Exit program------");
                    break;
             }
        } while (choice > 0 && choice < 6);
    }
    
    private void menuManageIngredients(String subMenuTitle, String ingredientPathFile) {
        Menu menu1 = new Menu(subMenuTitle);
        menu1.addOption("Add an ingredient");
        menu1.addOption("Update ingredient information");
        menu1.addOption("Delete ingredient");
        menu1.addOption("Show all ingredients");
        menu1.addOption("Return to main menu");
        // 5 options

        ManageIngredientDAO manageIngredientDAO = new ManageIngredientDAO(ingredientPathFile);

        String prefixId = "I"; // Ingredient's ID like "Ixx..." (x is a number)

        int choice1 = 0;

        do {
            menu1.printMenu();
            choice1 = menu1.getChoice();
            switch (choice1) {
                case 1:
                    System.out.println("----You are going to add new ingredients----");
                    do {
                        manageIngredientDAO.addNew(prefixId);
                    } while (DataInputter.getYN("Do you want to continue add new ingredient?"));             
                    break;
                case 2:
                    System.out.println("----You are going to update ingredient's information----");
                    String id = dataValidation.inputId(prefixId);
                    if (!manageIngredientDAO.isExit(id)) System.out.println("There no ingredient found");
                    else {
                        manageIngredientDAO.display(id); // Show infor before update
                        manageIngredientDAO.update(id);
                        manageIngredientDAO.display(id); // Show infor after update
                    }
                    break;
                case 3:
                    System.out.println("----You are going to delete ingredient----");
                    manageIngredientDAO.showAll(); // Show ingredient collection
                    // Cho nguoi dung nhap ID
                    manageIngredientDAO.delete(dataValidation.inputId(prefixId));
                    break;
                case 4:
                    manageIngredientDAO.showAll();
                    break;
                case 5:
                    System.out.println("----Return to main menu----");
                    break;
            }
        } while (choice1 != 5);
    }

    private void menuManageMenuItem(String subMenuTitle, String menuPathFile, String ingredientPathFile) {
        Menu menu2 = new Menu(subMenuTitle);
        menu2.addOption("Add the drink to the menu");
        menu2.addOption("Update the drink information");
        menu2.addOption("Delete the drink from the menu");
        menu2.addOption("Show all drink");
        menu2.addOption("Return to main menu");
        // 5 options

        ManageBeverageRecipeDAO manageBeverageRecipeDAO = new ManageBeverageRecipeDAO(menuPathFile, ingredientPathFile);

        String prefixId = "D"; // Drink's ID like "Dxx..." (x is a number)

        int choice2 = 0;

        do {
            menu2.printMenu();
            choice2 = menu2.getChoice();
            switch (choice2) {
                case 1:
                    System.out.println("----You are going to add new drink----");
                    do {
                        manageBeverageRecipeDAO.addNew(prefixId);
                    } while (DataInputter.getYN("Do you want to continue add new drink?"));
                    break;
                case 2:
                    System.out.println("----You are going to update drink recipe information----");
                    String id = dataValidation.inputId(prefixId);
                    if (!manageBeverageRecipeDAO.isExit(id)) System.out.println("There no drink found");
                    else {
                        manageBeverageRecipeDAO.display(id); // Show infor before update
                        manageBeverageRecipeDAO.update(id);
                        manageBeverageRecipeDAO.display(id); // Show infor after update
                    }
                    break;
                case 3:
                    System.out.println("----You are going to delete ingredient----");
                    manageBeverageRecipeDAO.showAll(); // Show drink list
                    manageBeverageRecipeDAO.delete(dataValidation.inputId(prefixId));
                    break;
                case 4:
                    manageBeverageRecipeDAO.showAll();
                    break;
                default:
                    System.out.println("----Return to main menu----");
                    break;
            }
        } while (choice2 > 0 && choice2 < 5);
    }

    private void menuDispensableDrink(String subMenuTitle, String ingredientPathFile, String menuPathFile, String orderPathFile) {
        Menu menu3 = new Menu(subMenuTitle);
        menu3.addOption("Dispensing the drink");
        menu3.addOption("Update the dispensing drink");
        menu3.addOption("Return to main menu");

        DispensingDrinkDAO dispensingDrinkDAO = new DispensingDrinkDAO(ingredientPathFile, menuPathFile, orderPathFile);

        String prefixID = "O"; // Order ID like "Oxx.." (x is a number)

        int choice3 = 0;

        do {
            menu3.printMenu();
            choice3 = menu3.getChoice();
            switch (choice3) {
                case 1:
                    dispensingDrinkDAO.dispensingDrink(prefixID);
                    break;
                case 2:
                   // dispensingDrinkDAO.updateDispensingDrink(id, quantity);
                    break;
                default:
                    System.out.println("---Return to main menu---");
                    break;
            }
        } while (choice3 > 0 && choice3 < 3);

    }

    private void menuReport() {
        
    }
    
    private void saveToFile() {
        ManageIngredientDAO manageIngredientDAO = new ManageIngredientDAO(ingredientPathFile);
        manageIngredientDAO.saveToFile();
        ManageBeverageRecipeDAO manageBeverageRecipeDAO = new ManageBeverageRecipeDAO(menuPathFile, ingredientPathFile);
        manageBeverageRecipeDAO.saveToFile();
        DispensingDrinkDAO dispensingDrinkDAO = new DispensingDrinkDAO(ingredientPathFile, menuPathFile, orderPathFile);
        dispensingDrinkDAO.saveToFile();
    }
    
    

}

