package com.thien.ingredients.bussiness.services;

import com.thien.ingredients.bussiness.model.BeverageRecipe;
import com.thien.ingredients.bussiness.model.BeverageRecipeStatus;
import com.thien.ingredients.bussiness.model.Ingredient;
import com.thien.ingredients.bussiness.model.IngredientStatus;
import java.util.HashSet;
import java.util.Set;

public class ReportDAO implements Reportable {
    
    private ManageIngredientDAO manageIngredientDAO;
    private ManageBeverageRecipeDAO manageBeverageRecipeDAO;
    private DispensingDrinkDAO dispensingDrinkDAO;
    
    public ReportDAO(ManageIngredientDAO manageIngredientDAO, ManageBeverageRecipeDAO manageBeverageRecipeDAO, DispensingDrinkDAO dispensingDrinkDAO) {
        this.manageIngredientDAO = manageIngredientDAO;
        this.manageBeverageRecipeDAO = manageBeverageRecipeDAO;
        this.dispensingDrinkDAO = dispensingDrinkDAO;
    }

    @Override
    public void availableIngredient() {
        System.out.println(" ------------------------------------------------------------------ ");
        System.out.println("|    ID    |             Name             |   Quantity  |   Unit   |");
        System.out.println(" ------------------------------------------------------------------ ");

        for (Ingredient i : manageIngredientDAO.ingredientMap.values()) {
            if (i.getIngredientStatus() == IngredientStatus.AVAILABLE)
                System.out.println(i.toString());
        }
        
        System.out.println(" ------------------------------------------------------------------ ");

    }

    @Override
    public void drinkOutOfIngredient() {
        Set<BeverageRecipe> set = new HashSet<>();
        for (BeverageRecipe b : manageBeverageRecipeDAO.beverageRecipeMap.values()) {
            if (b.getBeverageRecipeStatus() == BeverageRecipeStatus.AVAILABLE) {
                for (String key : b.getBeverageRecipeIngredients().keySet()) {
                    if (manageIngredientDAO.ingredientMap.get(key).getIngredientStatus() == IngredientStatus.OUT_OF_STOCK)
                        set.add(b);
                }
            }
        }
        
        System.out.println(" ------------------------------------------------------------------------- ");
        System.out.println("|    ID    |             Name             |             Recipe            |");
        System.out.println(" ------------------------------------------------------------------------- ");
        
        for (BeverageRecipe b : set) {
            System.out.println(b.toString());
        }
        
        System.out.println(" ------------------------------------------------------------------------- ");
    
    }

    @Override
    public void showAllDispensingDrink() {
        dispensingDrinkDAO.showAll();
    }

    public boolean isExit(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isExit'");
    }
    
}
