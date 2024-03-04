package com.thien.ingredients.bussiness.services;

import com.thien.ingredients.bussiness.model.BeverageRecipe;
import com.thien.ingredients.bussiness.model.BeverageRecipeStatus;
import com.thien.ingredients.bussiness.model.Ingredient;
import com.thien.ingredients.bussiness.model.IngredientStatus;
import java.util.HashSet;
import java.util.Set;

/**
 * Service class for generating reports.
 * This class provides methods to generate various reports related to ingredients, beverage recipes, and dispensing drinks.
 *
 * @author Thienlm30
 */
public class ReportDAO implements Reportable {
    
    private ManageIngredientDAO manageIngredientDAO;
    private ManageBeverageRecipeDAO manageBeverageRecipeDAO;
    private DispensingDrinkDAO dispensingDrinkDAO;
    
    /**
    * Constructs a ReportDAO object with the specified dependencies.
    * 
    * @param manageIngredientDAO The service for managing ingredients.
    * @param manageBeverageRecipeDAO The service for managing beverage recipes.
    * @param dispensingDrinkDAO The service for dispensing drinks.
    */
    public ReportDAO(ManageIngredientDAO manageIngredientDAO, ManageBeverageRecipeDAO manageBeverageRecipeDAO, DispensingDrinkDAO dispensingDrinkDAO) {
        this.manageIngredientDAO = manageIngredientDAO;
        this.manageBeverageRecipeDAO = manageBeverageRecipeDAO;
        this.dispensingDrinkDAO = dispensingDrinkDAO;
    }
    
    /**
    * Displays a list of available ingredients.
    * This method prints a table header and then iterates through the ingredient map,
    * printing details of each ingredient that has the status set to AVAILABLE.
    */
    @Override
    public void availableIngredient() {
        System.out.println("+---------------------------------------------------------------------------------------+");
        System.out.println("|    ID    |             Name             |   Quantity  |   Unit   |       Status       |");
        System.out.println("+---------------------------------------------------------------------------------------+");

        for (Ingredient i : manageIngredientDAO.ingredientMap.values()) {
            if (i.getIngredientStatus() == IngredientStatus.AVAILABLE)
                System.out.println(i.toString());
        }
        
        System.out.println("+---------------------------------------------------------------------------------------+");

    }
    
    /**
    * Displays a list of beverage recipes that are out of certain ingredients.
    * This method iterates through the beverage recipe map and checks if any of the required ingredients are out of stock.
    * It prints details of beverage recipes that have at least one ingredient out of stock.
    */
    @Override
    public void drinkOutOfIngredient() {
        Set<BeverageRecipe> set = new HashSet<>();
        for (BeverageRecipe b : manageBeverageRecipeDAO.beverageRecipeMap.values()) {
            if (b.getBeverageRecipeStatus() != BeverageRecipeStatus.NOT_AVAILABLE) {
                for (String key : b.getBeverageRecipeIngredients().keySet()) {
                    if (manageIngredientDAO.ingredientMap.get(key).getIngredientStatus() == IngredientStatus.OUT_OF_STOCK)
                        set.add(b);
                }
            }
        }
        
        System.out.println(" ----------------------------------------------------------------------------------------------+ ");
        System.out.println("|    ID    |             Name             |             Recipe            |       Status       |");
        System.out.println(" ----------------------------------------------------------------------------------------------+ ");
        
        for (BeverageRecipe b : set) {
            System.out.println(b.toString());
        }
        
        System.out.println(" ----------------------------------------------------------------------------------------------+ ");
    
    }
    
    /**
    * Displays details of all dispensing drinks.
    * This method delegates the task of displaying all dispensing drinks to the dispensingDrinkDAO object.
    */
    @Override
    public void showAllDispensingDrink() {
        dispensingDrinkDAO.showAll();
    }
    
}
