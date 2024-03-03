package com.thien.ingredients.bussiness.model;

import java.io.Serializable;
import java.util.Map;


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
        return String.format("|-%10s|%40S|", id, menuItemIngredientsToString());
    }

    private String menuItemIngredientsToString() {
        String result = "";
        if (orderBeverageRecipe != null) 
            for(Map.Entry<String, Integer> o : orderBeverageRecipe.entrySet()){
                result += o.getKey() + " ";
            }
        
        return result;
    }
}
