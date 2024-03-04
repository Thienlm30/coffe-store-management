package com.thien.ingredients.bussiness.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thien.ingredients.bussiness.components.IdGenerator;
import com.thien.ingredients.bussiness.model.Ingredient;
import com.thien.ingredients.bussiness.model.IngredientStatus;
import com.thien.ingredients.data.repository.IngredientDAL;
import com.thien.ingredients.gui.utilities.DataInputter;

/**
 * Service class for managing ingredients. This class provides functionalities
 * for adding, updating, deleting, and displaying ingredient information. It
 * also supports saving ingredient data to a file.
 *
 * @author Thienlm30
 */
public class ManageIngredientDAO implements Manageable {

    public Map<String, Ingredient> ingredientMap;
    private IngredientDAL ingredientDAL;
    private String ingredientPathFile;

    /**
     * Constructs a ManageIngredientDAO object with the specified ingredient
     * file path. This constructor initializes the ManageIngredientDAO object by
     * loading ingredient data from the specified file. If the loading process
     * is successful, it populates the ingredient map with the loaded data.
     *
     * @param ingredientPathFile The file path where ingredient data is stored.
     */
    public ManageIngredientDAO(String ingredientPathFile) {
        this.ingredientDAL = new IngredientDAL();
        List<Ingredient> list = new ArrayList<>();
        if (ingredientDAL.loadFromFile(list, ingredientPathFile)) {
            this.ingredientMap = new HashMap<>();
            for (Ingredient i : list) {
                this.ingredientMap.put(i.getId(), i);
            }
            System.out.println("Load thanh cong tu ingredient constructor");
        } else {
            System.err.println("Error loading ingredients from constructor");
        }
        this.ingredientPathFile = ingredientPathFile;
    }
    
    /**
    * Adds a new ingredient.
    * This method prompts the user to input the name, quantity, and unit of the ingredient.
    * It generates a unique ID for the ingredient using the provided prefixId and adds the new ingredient to the ingredient map.
    * 
    * @param prefixId The prefix used for generating the ID of the new ingredient.
    */
    @Override
    public void addNew(String prefixId) {
        IdGenerator idGenerator = new IdGenerator(ingredientMap, prefixId);
        String id = idGenerator.generateId();
        String name = DataInputter.getNonBlankString("Enter ingredient name :", "Name cannot be blank");
        int quantity = DataInputter.getInteger("Enter ingredient quantity: ", "Quantity must be a number and cannot be less than zero", 0);
        String unit = DataInputter.getNonBlankString("Enter unit (Example: ml, g, cup,...): ", "Unit cannot be blank");

        Ingredient ingredient = new Ingredient(id, name, quantity, unit);
        // check co add trung khong
        //  code code ....
        ingredientMap.put(id, ingredient);
    }
    
    /**
    * Updates an existing ingredient.
    * This method prompts the user to input new values for the name, quantity, and unit of the ingredient.
    * If the user leaves a field blank, the corresponding value remains unchanged.
    * 
    * @param id The ID of the ingredient to be updated.
    */
    @Override
    public void update(String id) {
        String name = DataInputter.getStrCanBlank("Enter new ingredient name: ");
        if (name.isEmpty()) {
            name = ingredientMap.get(id).getName();
        }

        String quantityString;
        int quantity = -1;
        boolean flag = true;
        // update quantity
        while (flag) {
            // blank: use old quantity
            quantityString = DataInputter.getStrCanBlank("Enter new ingredient quantity: ");
            if (quantityString.isEmpty()) {
                quantity = ingredientMap.get(id).getQuantity();
                flag = false;
            } else {
                try {
                    quantity = Integer.parseInt(quantityString);
                    if (quantity < 0) {
                        throw new Exception();
                    }
                    flag = false;
                } catch (Exception e) {
                    System.err.println("Quantity must be a number and cannot be less than zero");
                }
            }
        }

        String unit = DataInputter.getStrCanBlank("Enter new ingredient unit (Example: ml, g, cup,...): ");
        if (unit.isEmpty()) {
            unit = ingredientMap.get(id).getUnit();
        }

        ingredientMap.get(id).setName(name);
        ingredientMap.get(id).setQuantity(quantity);
        if (quantity <= 0) {
            ingredientMap.get(id).setIngredientStatus(IngredientStatus.OUT_OF_STOCK);
        }
        if (quantity > 0) {
            ingredientMap.get(id).setIngredientStatus(IngredientStatus.AVAILABLE);
        }
        ingredientMap.get(id).setUnit(unit);
    }
    
    /**
    * Deletes an ingredient.
    * This method checks if the ingredient with the specified ID exists.
    * If the ingredient is found, it displays its details and prompts the user for confirmation to delete.
    * If the user confirms deletion, the ingredient is either removed from the map (if its status is null) or marked as not available with quantity set to 0.
    * 
    * @param id The ID of the ingredient to be deleted.
    */
    @Override
    public void delete(String id) {
        if (ingredientMap.get(id) == null) {
            System.out.println("There no ingredient found");
        } else if (ingredientMap.get(id).getIngredientStatus() == null) {
            display(id);
            if (DataInputter.getYN("Do you want to delete this ingredient?")) {
                ingredientMap.remove(id);
                System.out.println("Delete successful");
            }
        } else {
            display(id);
            if (DataInputter.getYN("Do you want to delete this ingredient?")) {
                ingredientMap.get(id).setIngredientStatus(IngredientStatus.NOT_AVAILABLE);
                ingredientMap.get(id).setQuantity(0);
                System.out.println("Delete successful");
            }
        }
    }
    
    /**
    * Displays all ingredients.
    * This method prints a table header and then iterates through the ingredient map, printing details of each ingredient.
    * The ingredients are sorted alphabetically by name before being displayed.
    */
    @Override
    public void showAll() {
        System.out.println("+---------------------------------------------------------------------------------------+ ");
        System.out.println("|    ID    |             Name             |   Quantity  |   Unit   |       Status       |");
        System.out.println("+---------------------------------------------------------------------------------------+ ");
        List<Ingredient> list = convertMapToList();
        list.sort((i1, i2) -> i2.getName().compareToIgnoreCase(i1.getName()));
        for (Ingredient i : list) {
            System.out.println(i.toString());
        }
        System.out.println("+---------------------------------------------------------------------------------------+ ");

    }
    
    /**
    * Converts the ingredient map to a list.
    * This method iterates through the values of the ingredient map and adds them to a list.
    * 
    * @return A list containing all ingredients in the map.
    */
    private List<Ingredient> convertMapToList() {
        List<Ingredient> list = new ArrayList<>();
        for (Ingredient i : ingredientMap.values()) {
            list.add(i);
        }
        return list;
    }
    
    /**
    * Displays details of a specific ingredient.
    * This method prints a table header and then prints details of the ingredient corresponding to the provided ID.
    * 
    * @param id The ID of the ingredient to be displayed.
    */
    public void display(String id) {
        System.out.println("+---------------------------------------------------------------------------------------+ ");
        System.out.println("|    ID    |             Name             |   Quantity  |   Unit   |       Status       |");
        System.out.println("+---------------------------------------------------------------------------------------+ ");
        System.out.println(ingredientMap.get(id).toString());
        System.out.println("+---------------------------------------------------------------------------------------+ ");
    }
    
    /**
    * Saves ingredient data to a file.
    * This method attempts to save the ingredient data to a file specified by the ingredientPathFile.
    * If successful, it converts the ingredient map to a list and writes it to the file using the ingredientDAL object.
    * If an exception occurs during the process, an error message is printed indicating the failure.
    */
    public void saveToFile() {
        try {
            ingredientDAL.saveToFile(convertMapToList(), ingredientPathFile);
        } catch (Exception e) {
            System.out.println("Save ingredient fail");
        }
    }

}
