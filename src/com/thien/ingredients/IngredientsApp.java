package com.thien.ingredients;

import com.thien.ingredients.gui.view.IngredientsController;

public class IngredientsApp {
    
    public static void main(String[] args) {
       
        IngredientsController ingredientsController = new IngredientsController("Ingredients.dat", "Menu.dat", "Order.dat");
        ingredientsController.mainMenu("Ingredients Management");

    }
    
}
