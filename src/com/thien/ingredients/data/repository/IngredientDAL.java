package com.thien.ingredients.data.repository;

import java.util.List;

import com.thien.ingredients.bussiness.model.Ingredient;
import com.thien.ingredients.data.FileManagement;

public class IngredientDAL {
    
    private FileManagement fileManagement;

    public IngredientDAL() {
        this.fileManagement = new FileManagement();
    }

    public boolean loadFromFile(List<Ingredient> ingredientList, String ingredientPathFile) {
        return fileManagement.loadListFromFile(ingredientList, ingredientPathFile);
    }

    public boolean saveToFile(List<Ingredient> ingredientList, String ingredientPathFile) {
        return fileManagement.saveListToFile(ingredientList, ingredientPathFile, "Save ingredient to file successfully!");
    }
}
