package com.thien.ingredients.data.repository;

import java.util.List;

import com.thien.ingredients.bussiness.model.BeverageRecipe;
import com.thien.ingredients.data.FileManagement;

/**
 * Data Access Layer (DAL) class for handling beverage recipe data.
 * This class provides methods to load beverage recipe data from a file and save beverage recipe data to a file.
 *
 * @author Thienlm30
 */
public class MenuDAL {

    private FileManagement fileManagement;
    
    /**
    * Constructs a MenuDAL object.
    * This constructor initializes the FileManagement object used for file operations.
    */
    public MenuDAL() {
        this.fileManagement = new FileManagement();
    }
    
    /**
    * Loads beverage recipe data from a file.
    * 
    * @param beverageRecipeList The list to populate with loaded beverage recipes.
    * @param menuPathFile The path to the file containing beverage recipe data.
    * @return True if the loading process is successful, false otherwise.
    */
    public boolean loadFromFile(List<BeverageRecipe> beverageRecipeList, String menuPathFile) {
        return fileManagement.loadListFromFile(beverageRecipeList, menuPathFile);
    }
    
    /**
    * Saves beverage recipe data to a file.
    * 
    * @param menuItemList The list containing beverage recipes to be saved.
    * @param menuPathFile The path to the file where beverage recipe data will be saved.
    * @return True if the saving process is successful, false otherwise.
    */
    public boolean saveToFile(List<BeverageRecipe> menuItemList, String menuPathFile) {
        return fileManagement.saveListToFile(menuItemList, menuPathFile, "Save beverage recipe to file successfully!");
    }
}
