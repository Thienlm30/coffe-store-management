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

    Map<String, BeverageRecipe> beverageRecipeMap;
    private MenuDAL menuDAL;
    private String ingredientPathFile;
    private String menuPathFile;
    
    public ManageBeverageRecipeDAO(String menuPathFile, String ingredientPathFile) {
        menuDAL = new MenuDAL();
        
        this.beverageRecipeMap = new HashMap<>();
        
        List<BeverageRecipe> listMenu = new ArrayList<>();
        menuDAL.loadFromFile(listMenu, menuPathFile);
        for (BeverageRecipe b : listMenu) {
            this.beverageRecipeMap.put(b.getId(), b);
        }
        this.ingredientPathFile = ingredientPathFile;
        this.menuPathFile = menuPathFile; 
    }

    @Override
    public void addNew(String prefixId) {
    
        IdGenerator idGenerator = new IdGenerator(beverageRecipeMap, prefixId);
        String id = idGenerator.generateId();
        String name = DataInputter.getNonBlankString("Enter drink name :", "Name cannot be blank");
        ManageIngredientDAO manageIngredientDAO = new ManageIngredientDAO(ingredientPathFile);
        
        // Input menuItemIngredient 
        Map<String, Integer> menuItemIngredient = ingredientCollection("I", manageIngredientDAO.ingredientMap);

        BeverageRecipe menuItem = new BeverageRecipe(id, name, menuItemIngredient);

        beverageRecipeMap.put(menuItem.getId(), menuItem);

    }

    @Override
    public void update(String id) {
        String name = DataInputter.getStrCanBlank("Enter new drink name: ");
        if (name.isEmpty()) name = beverageRecipeMap.get(id).getName();

        if (DataInputter.getYN("Do you want to update drink recipe?")) {
            ManageIngredientDAO manageIngredientDAO = new ManageIngredientDAO(ingredientPathFile);

            // Input menuItemIngredient 
            Map<String, Integer> menuItemIngredient = ingredientCollection("I", manageIngredientDAO.ingredientMap);

            beverageRecipeMap.get(id).setMenuItemIngredients(menuItemIngredient);
        }
    }

    @Override
    public void delete(String id) {
        if (beverageRecipeMap.get(id) == null) 
            System.out.println("There no drink found");
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
        System.out.println(" ------------------------------------------------------------------------- ");
        System.out.println("|    ID    |             Name             |             Recipe            |");
        System.out.println(" ------------------------------------------------------------------------- ");
        List<BeverageRecipe> list = converMapToList();
        list.sort((i1, i2) -> i2.getName().compareToIgnoreCase(i1.getName()));
        for (BeverageRecipe b : list) {
            System.out.println(b.toString());
        }
        System.out.println(" ------------------------------------------------------------------------- ");
    }

    private List<BeverageRecipe> converMapToList() {
        List<BeverageRecipe> list = new ArrayList<>();
        for (BeverageRecipe b : beverageRecipeMap.values()) {
            list.add(b);
        }
        return list;
    }

    public void display(String id) {
        
        System.out.println(" ------------------------------------------------------------------ ");
        System.out.println("|    ID    |             Name             |   Quantity  |   Unit   |");
        System.out.println(" ------------------------------------------------------------------ ");
        System.out.println(beverageRecipeMap.get(id).toString());
        System.out.println(" ------------------------------------------------------------------ ");
    }

    private Map<String, Integer> ingredientCollection(String prefixId, Map<String, Ingredient> ingredientMap) {
        
        DataValidation dataValidation = new DataValidation();
        Map<String, Integer> menuItemIngredients = new HashMap<>();
            // add ingredient list
            System.out.println("You are going to add ingredients to recipe");
            do {
                String ingredientId = dataValidation.inputId(prefixId);
                int ingredientQuantity;
                
                if (ingredientMap.containsKey(ingredientId))
                    System.out.println("No ingredient found");
                else {
                      // Set ingredient status
//                    if (ingredientMap.get(ingredientId).getIngredientStatus() != null)
//                        ingredientMap.get(ingredientId).setIngredientStatus(IngredientStatus.AVAILABLE);
                    ingredientQuantity = DataInputter.getInteger("Enter ingredient quantity: ", "Quantity must be a number and cannot be less than zero", 0);;
                    menuItemIngredients.put(ingredientId, ingredientQuantity);
                }
            } while (DataInputter.getYN("Do you want to continue add ingredient to recipe?"));
    
            return menuItemIngredients;

    }
    
    
    /**
     * This function check is the beverage recipe exit or not
     * Can be use in the controller class
     * @param id
     * @return 
     */
    public boolean isExit(String id) {
        return beverageRecipeMap.containsKey(id);
    }

    public void saveToFile() {
        if (!beverageRecipeMap.isEmpty())
            menuDAL.saveToFile(converMapToList(), menuPathFile);
    }
    
}
