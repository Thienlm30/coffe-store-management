package com.thien.ingredients.data.repository;

import java.util.List;

import com.thien.ingredients.bussiness.model.Ingredient;
import com.thien.ingredients.data.FileManagement;

/**
 * Data Access Layer (DAL) class for handling ingredient data.
 * This class provides methods to load ingredient data from a file and save ingredient data to a file.
 * 
 * @author  Thienlm30
 */
public class IngredientDAL {
    
    private FileManagement fileManagement;
    
    /**
    * Constructs an IngredientDAL object.
    * This constructor initializes the FileManagement object used for file operations.
    */
    public IngredientDAL() {
        this.fileManagement = new FileManagement();
    }
    
    /**
    * Loads ingredient data from a file.
    * 
    * @param ingredientList The list to populate with loaded ingredients.
    * @param ingredientPathFile The path to the file containing ingredient data.
    * @return True if the loading process is successful, false otherwise.
    */
    public boolean loadFromFile(List<Ingredient> ingredientList, String ingredientPathFile) {
        return fileManagement.loadListFromFile(ingredientList, ingredientPathFile);
    }
    
    /**
    * Saves ingredient data to a file.
    * 
    * @param ingredientList The list containing ingredients to be saved.
    * @param ingredientPathFile The path to the file where ingredient data will be saved.
    * @return True if the saving process is successful, false otherwise.
    */
    public boolean saveToFile(List<Ingredient> ingredientList, String ingredientPathFile) {
        return fileManagement.saveListToFile(ingredientList, ingredientPathFile, "Save ingredient to file successfully!");
    }
}
