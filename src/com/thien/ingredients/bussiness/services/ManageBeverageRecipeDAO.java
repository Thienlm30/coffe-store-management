package com.thien.ingredients.bussiness.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thien.ingredients.bussiness.components.DataValidation;
import com.thien.ingredients.bussiness.components.IdGenerator;
import com.thien.ingredients.bussiness.model.Ingredient;
import com.thien.ingredients.bussiness.model.BeverageRecipe;
import com.thien.ingredients.data.repository.MenuDAL;
import com.thien.ingredients.gui.utilities.DataInputter;

/**
 * Manages operations related to beverage recipes.
 * This class provides functionalities to add, update, delete, and display beverage recipes.
 * It manages the beverage recipe data, interacts with the ingredient data, and handles input validation.
 *
 * @author Thienlm30
 */
public class ManageBeverageRecipeDAO implements Manageable {
    private final String TIEU_DE = " ----------------------------------------------------------------------------------------------+\n" +
                                    "|    ID    |             Name             |             Recipe            |       Status       |\n" +
                                    " ----------------------------------------------------------------------------------------------+ \n";
    public Map<String, BeverageRecipe> beverageRecipeMap;
    private MenuDAL menuDAL;
    private Map<String, Ingredient> ingredientMap;
    private String menuPathFile;
    private ManageIngredientDAO manageIngredientDAO;
    
    /**
    * Initializes a new instance of the ManageBeverageRecipeDAO class with the specified menu file path and ingredient manager.
    * This constructor loads beverage recipes from the specified menu file, initializes the beverage recipe map,
    * and sets up the ingredient manager.
    *
    * @param menuPathFile       The file path of the menu containing beverage recipes.
    * @param manageIngredientDAO The ingredient manager used to interact with ingredient data.
    */
    public ManageBeverageRecipeDAO(String menuPathFile, ManageIngredientDAO manageIngredientDAO) {
        this.menuDAL = new MenuDAL();
        this.manageIngredientDAO = manageIngredientDAO;
        List<BeverageRecipe> listBevarage = new ArrayList<>();
        if (menuDAL.loadFromFile(listBevarage, menuPathFile)) {
            this.beverageRecipeMap = new HashMap<>();
            for (BeverageRecipe b : listBevarage) {
                this.beverageRecipeMap.put(b.getId(), b);
            }
            System.out.println("load thanh cong tu beverage recipe constructor");
        } else {
            System.err.println("Error loading beverage from constructor");
        }
        
        this.ingredientMap = manageIngredientDAO.ingredientMap;
        this.menuPathFile = menuPathFile; 
    }
    
    /**
    * Adds a new beverage recipe to the system.
    * This method prompts the user to input details of the new beverage recipe, including its name and ingredients.
    * It generates a unique ID for the beverage recipe using the provided prefix ID.
    * The method then creates a new BeverageRecipe object and adds it to the beverage recipe map.
    *
    * @param prefixId The prefix to be used for generating the ID of the new beverage recipe.
    */
    @Override
    public void addNew(String prefixId) {
        
        manageIngredientDAO.showAll();
        
        IdGenerator idGenerator = new IdGenerator(beverageRecipeMap, prefixId);
        String id = idGenerator.generateId();
        String name = DataInputter.getNonBlankString("Enter drink name :", "Name cannot be blank");
        
        // Input menuItemIngredient 
        Map<String, Integer> menuItemIngredient = ingredientCollection("I", ingredientMap);

        BeverageRecipe beverageRecipe = new BeverageRecipe(id, name, menuItemIngredient);

        beverageRecipeMap.put(beverageRecipe.getId(), beverageRecipe);

    }
    
    /**
    * Updates an existing beverage recipe in the system.
    * This method prompts the user to enter the new name for the beverage recipe.
    * If the user does not provide a new name, the method retains the current name of the beverage recipe.
    * The method then prompts the user to confirm if they want to update the recipe.
    * If confirmed, the user inputs the updated ingredients for the beverage recipe.
    *
    * @param id The ID of the beverage recipe to be updated.
    */
    @Override
    public void update(String id) {
        String name = DataInputter.getStrCanBlank("Enter new drink name: ");
        if (name.isEmpty()) name = beverageRecipeMap.get(id).getName();

        if (DataInputter.getYN("Do you want to update drink recipe?")) {

            // Input menuItemIngredient 
            Map<String, Integer> menuItemIngredient = ingredientCollection("I", ingredientMap);

            beverageRecipeMap.get(id).setBeverageRecipeIngredients(menuItemIngredient);
        }
    }
    
    /**
    * Deletes a beverage recipe from the system.
    * This method checks if the beverage recipe with the specified ID exists.
    * If the beverage recipe does not exist, it notifies the user.
    * If the beverage recipe exists and its status is null, indicating it's an ingredient, it displays the recipe details and prompts the user to confirm deletion.
    * If confirmed, it removes the beverage recipe from the system and notifies the user.
    * If the beverage recipe exists and its status is not null, indicating it's a drink recipe, it displays the recipe details and prompts the user to confirm deletion.
    * If confirmed, it removes the beverage recipe from the system and notifies the user.
    *
    * @param id The ID of the beverage recipe to be deleted.
    */
    @Override
    public void delete(String id) {
        if (beverageRecipeMap.get(id) == null) 
            System.out.println("There no drink found");
        else if (beverageRecipeMap.get(id).getBeverageRecipeStatus()== null) {
            display(id);
            if (DataInputter.getYN("Do you want to delete this ingredient?")) {
                beverageRecipeMap.remove(id);
                System.out.println("Delete successful");
            }
        }
        else {
            display(id);
            if (DataInputter.getYN("Do you want to delete this drink recipe?")) {
                beverageRecipeMap.remove(id);
                System.out.println("Delete successful");
            }
        }
    }
    
    /**
    * Displays all beverage recipes in the system.
    * This method prints a table header with column names: ID, Name, Recipe, and Status.
    * It retrieves all beverage recipes from the beverage recipe map, converts them to a list, and sorts them by name in descending order.
    * Then, it iterates through the list of beverage recipes, printing their details in the table format.
    * Finally, it prints a table footer to mark the end of the table.
    */
    @Override
    public void showAll() {
        System.out.println(" ----------------------------------------------------------------------------------------------+ ");
        System.out.println("|    ID    |             Name             |             Recipe            |       Status       |");
        System.out.println(" ----------------------------------------------------------------------------------------------+ ");
        List<BeverageRecipe> list = converMapToList();
        // sort by name
        list.sort((i1, i2) -> i2.getName().compareToIgnoreCase(i1.getName()));
        for (BeverageRecipe b : list) {
            System.out.println(b.toString());
        }
        System.out.println(" ----------------------------------------------------------------------------------------------+ ");
    
    }
    
    /**
    * Converts the beverage recipe map to a list of beverage recipes.
    * This method iterates through the values of the beverage recipe map and adds each beverage recipe to a list.
    * It then returns the list containing all beverage recipes.
    *
    * @return The list of beverage recipes converted from the beverage recipe map.
    */
    private List<BeverageRecipe> converMapToList() {
        List<BeverageRecipe> list = new ArrayList<>();
        for (BeverageRecipe b : beverageRecipeMap.values()) {
            list.add(b);
        }
        return list;
    }
    
    /**
    * Displays the details of a specific beverage recipe.
    * This method prints the details of the beverage recipe with the given ID, including its ID, name, recipe, and status.
    * 
    * @param id The ID of the beverage recipe to display.
    */
    public void display(String id) {
        
        System.out.println(TIEU_DE);
        System.out.println(beverageRecipeMap.get(id).toString());
        System.out.println(" ----------------------------------------------------------------------------------------------+ ");
    }
    
    /**
    * Collects ingredients for a beverage recipe.
    * This method prompts the user to add ingredients to a beverage recipe.
    * It iterates through the process of adding ingredients, allowing the user
    * to input an ingredient ID and its quantity. The method validates the ingredient ID,
    * checks if the ingredient exists in the ingredient map, and then adds it to the recipe
    * along with the specified quantity. The process continues until the user chooses not to
    * add more ingredients.
    *
    * @param prefixId The prefix for the ingredient's ID.
    * @param ingredientMap The map containing all available ingredients.
    * @return A map containing the ingredients and their quantities for the beverage recipe.
    */
    private Map<String, Integer> ingredientCollection(String prefixId, Map<String, Ingredient> ingredientMap) {
        
        DataValidation dataValidation = new DataValidation();
        Map<String, Integer> beverageRecipeIngredients = new HashMap<>();
            // add ingredient list
            System.out.println("You are going to add ingredients to recipe");
            do {
                String ingredientId = dataValidation.inputId(prefixId);
                int ingredientQuantity;
                
                if (!ingredientMap.containsKey(ingredientId))
                    System.out.println("No ingredient found");
                else {
                      // Set ingredient status
//                    if (ingredientMap.get(ingredientId).getIngredientStatus() != null)
//                        ingredientMap.get(ingredientId).setIngredientStatus(IngredientStatus.AVAILABLE);
                    ingredientQuantity = DataInputter.getInteger("Enter ingredient quantity: ", "Quantity must be a number and cannot be less than zero", 0);;
                    beverageRecipeIngredients.put(ingredientId, ingredientQuantity);
                }
            } while (DataInputter.getYN("Do you want to continue add ingredient to recipe?"));
    
            return beverageRecipeIngredients;

    }
    
    /**
    * Saves the beverage recipes to a file.
    * This method attempts to save the beverage recipes to a file specified by the menu path file.
    * If successful, the beverage recipes are converted to a list and written to the file using the menuDAL object.
    * If an exception occurs during the process, an error message is printed indicating the failure.
    */
    public void saveToFile() {
        try {
            menuDAL.saveToFile(converMapToList(), menuPathFile);
        } catch (Exception e) {
            System.out.println("Save beverage recipe fail");
        }
    }
    
}
