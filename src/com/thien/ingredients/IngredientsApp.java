package com.thien.ingredients;

import com.thien.ingredients.gui.view.IngredientsController;

/**
 * Ingredients management
 * 
 * @author Thienlm30
 */
public class IngredientsApp {
    
    public static void main(String[] args) {
       
        IngredientsController ingredientsController = new IngredientsController("Ingredients.dat", "BeverageRecipe.dat", "Order.dat");
        ingredientsController.mainMenu("Main Menu Ingredients Management");

    }
    
}
