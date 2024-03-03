package com.thien.ingredients.bussiness.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thien.ingredients.bussiness.components.DataValidation;
import com.thien.ingredients.bussiness.components.IdGenerator;
import com.thien.ingredients.bussiness.model.Ingredient;
import com.thien.ingredients.bussiness.model.BeverageRecipe;
import com.thien.ingredients.bussiness.model.Order;
import com.thien.ingredients.bussiness.model.OrderStatus;
import com.thien.ingredients.data.repository.OrderDAL;
import com.thien.ingredients.gui.utilities.DataInputter;

public class DispensingDrinkDAO implements Dispensable {

    private Map<String, Order> orderMap;
    private Map<String, Ingredient> ingredientMap;
    private Map<String, BeverageRecipe> beverageRecipeMap;
    private OrderDAL orderDAL;
    private String orderPathFile;
    private ManageIngredientDAO manageIngredientDAO;
    private ManageBeverageRecipeDAO manageBeverageRecipeDAO;

    public DispensingDrinkDAO(ManageIngredientDAO manageIngredientDAO, ManageBeverageRecipeDAO manageBeverageRecipeDAO, String orderPathFile) {
        this.manageIngredientDAO = manageIngredientDAO;
        this.manageBeverageRecipeDAO = manageBeverageRecipeDAO;
        this.ingredientMap = manageIngredientDAO.ingredientMap;
        this.beverageRecipeMap = manageBeverageRecipeDAO.beverageRecipeMap;

        OrderDAL orderDAL = new OrderDAL();
        List<Order> list = new ArrayList<>();
        if (orderDAL.loadFromFile(list, orderPathFile)) {
            this.orderMap = new HashMap<>();
            for (Order o : list) {
                orderMap.put(o.getId(), o);
            }
            System.out.println("load thanh cong tu order constructor");
        } else {
            System.err.println("Error loading order from constructor");
        }
        
        this.orderDAL = new OrderDAL();
        this.orderPathFile = orderPathFile;
    }
    
    /** 
     * This method dispensing drinks that have been ordered
     * The order's status is preparing
     * @param prefixId is prefix of the order's id
     */
    @Override
    public void dispensingDrink(String prefixId) {
        IdGenerator idGenerator = new IdGenerator(orderMap, prefixId);
        String id = idGenerator.generateId();
        
        Map<String, Integer> drinkMap = dirnkCollection("D");
        
        orderMap.put(id, new Order(id, drinkMap));
        orderMap.get(id).setOrderStatus(OrderStatus.PREPARING);
    }
    
    /**
     * This method return the list of order drink and it's quantity 
     * @param prefixId is the prefix of the drink's id
     * @return a list contain drinks order
     */
    private Map<String, Integer> dirnkCollection(String prefixId) {
        Map <String, Integer> drinkMap = new HashMap<>();
        DataValidation dataValidation = new DataValidation();
        do {
            String drinkId = dataValidation.inputId(prefixId);
            int quantity;

            if (beverageRecipeMap.get(drinkId) == null ) 
                System.out.println("There no drink found");
            else {
                quantity = DataInputter.getInteger("Enter quantity", "Quantity must a number and cannot be less than one", 1);
                drinkMap.put(drinkId, quantity);
            }

        } while (!DataInputter.getYN("Are you sure to order?"));
        return drinkMap;
    }

    @Override
    public void updateDispensingDrink(String menuItemId, int quantity) {
        
    }
    
    public void saveToFile() {
        try {
            orderDAL.saveToFile(converMapToList(), orderPathFile);
        } catch (Exception e) {
            System.out.println("Save order fail");
        }
    }
    
    /**
     * ?????
     * @param id
     * @return 
     */
    public boolean checkIngredient(String id) {
        return ingredientMap.containsKey(id);
    }
    
    private List<Order> converMapToList() {
        List<Order> list = new ArrayList<>();
        for (Order o : orderMap.values()) {
            list.add(o);
        }
        return list;
    }
}
