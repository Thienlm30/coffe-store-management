package com.thien.ingredients.bussiness.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thien.ingredients.bussiness.components.DataValidation;
import com.thien.ingredients.bussiness.components.IdGenerator;
import com.thien.ingredients.bussiness.model.Ingredient;
import com.thien.ingredients.bussiness.model.IngredientStatus;
import com.thien.ingredients.bussiness.model.BeverageRecipe;
import com.thien.ingredients.bussiness.model.Order;
import com.thien.ingredients.data.repository.OrderDAL;
import com.thien.ingredients.gui.utilities.DataInputter;

public class DispensingDrinkDAO implements Dispensable {

    private Map<String, Order> orderMap;
    private Map<String, Ingredient> ingredientMap;
    private Map<String, BeverageRecipe> menuItemMap;
    private OrderDAL orderDAL;
    private String orderPathFile;

    public DispensingDrinkDAO(String ingredientPathFile, String menuPathFile, String orderPathFile) {
        ManageIngredientDAO manageIngredientDAO = new ManageIngredientDAO(ingredientPathFile);
        this.ingredientMap = manageIngredientDAO.ingredientMap;

        ManageBeverageRecipeDAO manageMenuItemDAO = new ManageBeverageRecipeDAO(menuPathFile, ingredientPathFile);
        this.menuItemMap = manageMenuItemDAO.menuItemMap;

        OrderDAL orderDAL = new OrderDAL();
        List<Order> list = new ArrayList<Order>();
        orderDAL.loadFromFile(list, orderPathFile);
        for (Order o : list) {
            orderMap.put(o.getId(), o);
        }
        this.orderDAL = new OrderDAL();
        this.orderPathFile = orderPathFile;
    }
    
    @Override
    public void dispensingDrink(String prefixId) {
        IdGenerator idGenerator = new IdGenerator(orderMap, prefixId);
        String id = idGenerator.generateId();

        Map<String, Integer> drinkMap = dirnkCollection("D");
        
        Order order = new Order(id, drinkMap);

    }

    private Map<String, Integer> dirnkCollection(String prefixId) {
        Map <String, Integer> drinkMap = new HashMap<>();
        DataValidation dataValidation = new DataValidation();
        do {
            String drinkId = dataValidation.inputId(prefixId);
            int quantity;

            if (menuItemMap.get(drinkId) == null ) 
                System.out.println("There no drink found");
            else {
                quantity = DataInputter.getInteger("Enter quantity", "Quantity must a number and cannot be less than one", 1);
                drinkMap.put(drinkId, quantity);
            }

        } while (DataInputter.getYN("Are you sure to order?"));
        return drinkMap;
    }

    @Override
    public void updateDispensingDrink(String menuItemId, int quantity) {
        
    }
    
    public void saveToFile() {
        if (!orderMap.isEmpty())
            orderDAL.saveToFile(converMapToList(), orderPathFile);
    }
    
    public boolean checkIngredient(String id) {
        return ingredientMap.containsKey(id);
    }
    
    private List<Order> converMapToList() {
        List<Order> list = new ArrayList<Order>();
        for (Order o : orderMap.values()) {
            list.add(o);
        }
        return list;
    }

