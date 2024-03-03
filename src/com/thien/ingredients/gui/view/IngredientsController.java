package com.thien.ingredients.gui.view;

import com.thien.ingredients.bussiness.components.DataValidation;
import com.thien.ingredients.bussiness.services.DispensingDrinkDAO;
import com.thien.ingredients.bussiness.services.ManageIngredientDAO;
import com.thien.ingredients.bussiness.services.ManageBeverageRecipeDAO;
import com.thien.ingredients.bussiness.services.ReportDAO;
import com.thien.ingredients.gui.utilities.DataInputter;
import com.thien.ingredients.gui.utilities.Menu;

public class IngredientsController {

    private DataValidation dataValidation;
    private ManageIngredientDAO manageIngredientDAO;
    private ManageBeverageRecipeDAO manageBeverageRecipeDAO;
    private DispensingDrinkDAO dispensingDrinkDAO;
    private ReportDAO reportDAO;

    public IngredientsController(String ingredientPathFile, String menuPathFile, String orderPathFile) {
        this.dataValidation = new DataValidation();
        this.manageIngredientDAO = new ManageIngredientDAO(ingredientPathFile);
        this.manageBeverageRecipeDAO = new ManageBeverageRecipeDAO(menuPathFile, manageIngredientDAO);
        this.dispensingDrinkDAO = new DispensingDrinkDAO(manageIngredientDAO, manageBeverageRecipeDAO, orderPathFile);
        this.reportDAO = new ReportDAO(manageIngredientDAO, manageBeverageRecipeDAO, dispensingDrinkDAO);
    }
    
    public void mainMenu(String menutitle) {
        Menu menu = new Menu(menutitle);
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
                    menuManageIngredients("Manage ingredients");
                    break;
                case 2:
                    menuManageBeverageRecipe("Manage beverage recipes");
                    break;
                case 3:
                    menuDispensableDrink("Dispensing Drink");
                    break;
                case 4:
                    menuReport("Report");
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
    
    private void menuManageIngredients(String subMenuTitle) {
        Menu menu = new Menu(subMenuTitle);
        menu.addOption("Add an ingredient");
        menu.addOption("Update ingredient information");
        menu.addOption("Delete ingredient");
        menu.addOption("Show all ingredients");
        menu.addOption("Return to main menu");
        // 5 options

        String prefixId = "I"; // Ingredient's ID like "Ixx..." (x is a number)

        int choice = 0;

        do {
            menu.printMenu();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    System.out.println("----You are going to add new ingredients----");
                    do {
                        manageIngredientDAO.addNew(prefixId);
                    } while (DataInputter.getYN("Do you want to continue add new ingredient?"));             
                    break;
                case 2:
                    System.out.println("----You are going to update ingredient's information----");
                    String id = dataValidation.inputId(prefixId);
                    if (!manageIngredientDAO.ingredientMap.containsKey(id)) System.out.println("There no ingredient found");
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
        } while (choice != 5);
    }

    private void menuManageBeverageRecipe(String subMenuTitle) {
        Menu menu = new Menu(subMenuTitle);
        menu.addOption("Add the drink to the menu");
        menu.addOption("Update the drink information");
        menu.addOption("Delete the drink from the menu");
        menu.addOption("Show all drink");
        menu.addOption("Return to main menu");
        // 5 options

        String prefixId = "D"; // Drink's ID like "Dxx..." (x is a number)

        int choice = 0;

        do {
            menu.printMenu();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    System.out.println("----You are going to add new drink----");
                    do {
                        manageBeverageRecipeDAO.addNew(prefixId);
                    } while (DataInputter.getYN("Do you want to continue add new drink?"));
                    break;
                case 2:
                    System.out.println("----You are going to update drink recipe information----");
                    String id = dataValidation.inputId(prefixId);
                    if (!manageBeverageRecipeDAO.beverageRecipeMap.containsKey(id)) 
                        System.out.println("There no drink found");
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
        } while (choice > 0 && choice < 5);
    }

    private void menuDispensableDrink(String subMenuTitle) {
        Menu menu = new Menu(subMenuTitle);
        menu.addOption("Dispensing the drink");
        menu.addOption("Update the dispensing drink");
        menu.addOption("Return to main menu");

        String prefixID = "O"; // Order ID like "Oxx.." (x is a number)

        int choice = 0;

        do {
            menu.printMenu();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    dispensingDrinkDAO.dispensingDrink(prefixID);
                    break;
                case 2:
                    System.out.println("---You are going to update order---");
                    String id = dataValidation.inputId(prefixID);
                    if (!dispensingDrinkDAO.orderMap.containsKey(id)) 
                        System.out.println("There no order found");
                    else {
                        dispensingDrinkDAO.updateDispensingDrink(id);
                    }
                    break;
                default:
                    System.out.println("---Return to main menu---");
                    break;
            }
        } while (choice > 0 && choice < 3);

    }

    private void menuReport(String subTitleMenu) {
        Menu menu = new Menu(subTitleMenu);
        menu.addOption("Show which ingredient is available");
        menu.addOption("Show the drink for which the store is out of ingredients");
        menu.addOption("Show all dispensing drink");
        menu.addOption("Return to main menu");
        
        int choice = 0;
        
        do {            
            menu.printMenu();
            choice = menu.getChoice();
            
            switch (choice) {
                case 1:
                    reportDAO.availableIngredient();
                    break;
                case 2:
                    reportDAO.drinkOutOfIngredient();
                    break;
                case 3:
                    reportDAO.showAllDispensingDrink();
                    break;
                default:
                    System.out.println("---Return to main menu---");
                    break;
            }
            
        } while (choice > 0 && choice < 4);
        
    }
    
    private void saveToFile() {
        
        dispensingDrinkDAO.saveToFile();
        manageIngredientDAO.saveToFile();
        manageBeverageRecipeDAO.saveToFile();
        
    }
    
    

}

