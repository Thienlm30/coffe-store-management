package com.thien.ingredients.bussiness.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Represents an order for beverages.
 * This class contains information about an order, including its ID, beverage recipes, and status.
 * @author Thienlm30
 */
public class Order implements Serializable {
    
    private String id;
    private Map<String, Integer> orderBeverageRecipe;
    private OrderStatus orderStatus;

    public Order(String id, Map<String, Integer> orderBeverageRecipe) {
        this.id = id;
        this.orderBeverageRecipe = orderBeverageRecipe;
    }

    public String getId() {
        return id;
    }

    public Map<String, Integer> getOrderBeverageRecipe() {
        return orderBeverageRecipe;
    }

    public void setOrderBeverageRecipe(Map<String, Integer> orderBeverageRecipe) {
        this.orderBeverageRecipe = orderBeverageRecipe;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    @Override
    public String toString() {
        return String.format("|%-10s|%50S|%20s|", id, menuItemIngredientsToString(), orderStatus);
    }

    private String menuItemIngredientsToString() {
        String result = "";
        if (orderBeverageRecipe != null) 
            for(Map.Entry<String, Integer> o : orderBeverageRecipe.entrySet()){
                result += o.getKey() + "(" + o.getValue() + ")" + " ";
            }
        
        return result;
    }
}
