package com.thien.ingredients.bussiness.model;

import java.util.Map;


public class Order {
    
    private String id;
    private Map<String, Integer> orderMenuItem;

    public Order(String id, Map<String, Integer> orderMenuItem) {
        this.id = id;
        this.orderMenuItem = orderMenuItem;
    }

    public String getId() {
        return id;
    }

    public Map<String, Integer> getorderMenuItem() {
        return orderMenuItem;
    }

    public void setorderMenuItem(Map<String, Integer> orderMenuItem) {
        this.orderMenuItem = orderMenuItem;
    }
    
    @Override
    public String toString() {
        return String.format("|-%10s|%40S|", id, menuItemIngredientsToString());
    }

    private String menuItemIngredientsToString() {
        String result = "";
        if (orderMenuItem != null) 
            for(String o : orderMenuItem.keySet()){
                result += o + " ";
            }
        
        return result;
    }
}
