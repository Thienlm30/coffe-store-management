package com.thien.ingredients.data.repository;

import java.util.List;

import com.thien.ingredients.bussiness.model.BeverageRecipe;
import com.thien.ingredients.data.FileManagement;

public class MenuDAL {

    private FileManagement fileManagement;

    public MenuDAL() {
        this.fileManagement = new FileManagement();
    }

    public boolean loadFromFile(List<BeverageRecipe> beverageRecipeList, String menuPathFile) {
        return fileManagement.loadListFromFile(beverageRecipeList, menuPathFile);
    }
    
    public boolean saveToFile(List<BeverageRecipe> menuItemList, String menuPathFile) {
        return fileManagement.saveListToFile(menuItemList, menuPathFile, "Save beverage recipe to file successfully!");
    }
}
