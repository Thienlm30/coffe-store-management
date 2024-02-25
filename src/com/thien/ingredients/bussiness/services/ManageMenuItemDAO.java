package com.thien.ingredients.bussiness.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thien.ingredients.bussiness.components.DataValidation;
import com.thien.ingredients.bussiness.components.IdGenerator;
import com.thien.ingredients.bussiness.model.Ingredient;
import com.thien.ingredients.bussiness.model.MenuItem;
import com.thien.ingredients.data.repository.MenuDAL;
import com.thien.ingredients.gui.utilities.DataInputter;

public class ManageMenuItemDAO implements Manageable {

    Map<String, MenuItem> menuItemMap;
    private MenuDAL menuDAL;
    private String ingredientPathFile;
    private String menuPathFile;
    
    public ManageMenuItemDAO(String menuPathFile, String ingredientPathFile) {
        menuDAL = new MenuDAL();
        List<MenuItem> list = new ArrayList<MenuItem>();
        menuDAL.loadFromFile(list, menuPathFile);
        for (MenuItem m : list) {
            menuItemMap.put(m.getId(), m);
        }
        this.ingredientPathFile = ingredientPathFile;
        this.menuPathFile = menuPathFile;
    }

    @Override
    public void addNew(String prefixId) {
    
        IdGenerator idGenerator = new IdGenerator(menuItemMap, prefixId);
        String id = idGenerator.generateId();
        String name = DataInputter.getNonBlankString("Enter drink name :", "Name cannot be blank");
        ManageIngredientDAO manageIngredientDAO = new ManageIngredientDAO(ingredientPathFile);
        
        // Input menuItemIngredient 
        Map<String, Integer> menuItemIngredient = ingredientCollection("I", manageIngredientDAO.ingredientMap);

        MenuItem menuItem = new MenuItem(id, name, menuItemIngredient);

        menuItemMap.put(menuItem.getId(), menuItem);

    }

    @Override
    public void update(String id) {
        String name = DataInputter.getStrCanBlank("Enter new drink name: ");
        if (name.isEmpty()) name = menuItemMap.get(id).getName();

        if (DataInputter.getYN("Do you want to update drink recipe?")) {
            ManageIngredientDAO manageIngredientDAO = new ManageIngredientDAO(ingredientPathFile);

            // Input menuItemIngredient 
            Map<String, Integer> menuItemIngredient = ingredientCollection("I", manageIngredientDAO.ingredientMap);

            menuItemMap.get(id).setMenuItemIngredients(menuItemIngredient);
        }
    }

    @Override
    public void delete(String id) {
        if (menuItemMap.get(id) == null) 
            System.out.println("There no drink found");
        else {
            display(id);
            if (DataInputter.getYN("Do you want to delete this drink recipe?")) {
                menuItemMap.remove(id);
                System.out.println("Delete successful");
            }
        }
    }

    @Override
    public void showAll() {
        System.out.println(" ------------------------------------------------------------------------- ");
        System.out.println("|    ID    |             Name             |             Recipe            |");
        System.out.println(" ------------------------------------------------------------------------- ");
        List<MenuItem> list = converMapToList();
        list.sort((i1, i2) -> i2.getName().compareToIgnoreCase(i1.getName()));
        for (MenuItem m : list) {
            System.out.println(m.toString());
        }
        System.out.println(" ------------------------------------------------------------------------- ");
    }

    private List<MenuItem> converMapToList() {
        List<MenuItem> list = new ArrayList<MenuItem>();
        for (MenuItem m : menuItemMap.values()) {
            list.add(m);
        }
        return list;
    }

    public void display(String id) {
        
        System.out.println(" ------------------------------------------------------------------ ");
        System.out.println("|    ID    |             Name             |   Quantity  |   Unit   |");
        System.out.println(" ------------------------------------------------------------------ ");
        System.out.println(menuItemMap.get(id).toString());
        System.out.println(" ------------------------------------------------------------------ ");
    }

    private Map<String, Integer> ingredientCollection(String prefixId, Map<String, Ingredient> ingredientMap) {
        
        DataValidation dataValidation = new DataValidation();
        Map<String, Integer> menuItemIngredients = new HashMap<String, Integer>();
            // add ingredient list
            do {
                String ingredientId = dataValidation.inputId(prefixId);
                int ingredientQuantity;
                
                if (!isExit(ingredientId)) 
                    System.out.println("No ingredient found");
                else {
                    ingredientQuantity = DataInputter.getInteger("Enter ingredient quantity: ", "Quantity must be a number and cannot be less than zero", 0);;
                    menuItemIngredients.put(ingredientId, ingredientQuantity);
                }
            } while (DataInputter.getYN("Do you want to continue add ingredient to recipe?"));
    
            return menuItemIngredients;

        }

    @Override
    public boolean isExit(String id) {
        if (menuItemMap.get(id) != null) return true;
        return false;
    }

    public void saveToFile() {
        menuDAL.saveToFile(converMapToList(), menuPathFile);
    }
    
}
