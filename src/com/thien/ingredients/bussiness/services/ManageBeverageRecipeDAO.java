package com.thien.ingredients.bussiness.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thien.ingredients.bussiness.components.DataValidation;
import com.thien.ingredients.bussiness.components.IdGenerator;
import com.thien.ingredients.bussiness.model.Ingredient;
import com.thien.ingredients.bussiness.model.BeverageRecipe;
import com.thien.ingredients.bussiness.model.IngredientStatus;
import com.thien.ingredients.data.repository.MenuDAL;
import com.thien.ingredients.gui.utilities.DataInputter;

public class ManageBeverageRecipeDAO implements Manageable {

    public Map<String, BeverageRecipe> beverageRecipeMap;
    private MenuDAL menuDAL;
    private Map<String, Ingredient> ingredientMap;
    private String menuPathFile;
    private ManageIngredientDAO manageIngredientDAO;
    
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

    private List<BeverageRecipe> converMapToList() {
        List<BeverageRecipe> list = new ArrayList<>();
        for (BeverageRecipe b : beverageRecipeMap.values()) {
            list.add(b);
        }
        return list;
    }

    public void display(String id) {
        
        System.out.println(" ----------------------------------------------------------------------------------------------+ ");
        System.out.println("|    ID    |             Name             |             Recipe            |       Status       |");
        System.out.println(" ----------------------------------------------------------------------------------------------+ ");
        System.out.println(beverageRecipeMap.get(id).toString());
        System.out.println(" ----------------------------------------------------------------------------------------------+ ");
    }

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

    public void saveToFile() {
        try {
            menuDAL.saveToFile(converMapToList(), menuPathFile);
        } catch (Exception e) {
            System.out.println("Save beverage recipe fail");
        }
    }
    
}
