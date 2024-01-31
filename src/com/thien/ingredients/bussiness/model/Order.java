package com.thien.ingredients.bussiness.model;

import java.util.Map;

enum IngredientStatus {
    OUT_OF_STOCK("Out of Stock"), 
    NOT_ENOUGH("Not Enough in Stock"), 
    AVAILABLE("Available");

    private String status;

    IngredientStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return status;
    }
}

public class Order {
    
    private String id;
    private Map<String, Integer> orderIngredient;
    private IngredientStatus ingredientStatus;

    public Order(String id, Map<String, Integer> orderIngredient, IngredientStatus ingredientStatus) {
        this.id = id;
        this.orderIngredient = orderIngredient;
        this.ingredientStatus = ingredientStatus;
    }

    public String getId() {
        return id;
    }

    public Map<String, Integer> getOrderIngredient() {
        return orderIngredient;
    }

    public void setOrderIngredient(Map<String, Integer> orderIngredient) {
        this.orderIngredient = orderIngredient;
    }

    public IngredientStatus getIngredientStatus() {
        return ingredientStatus;
    }

    public void setIngredientStatus(IngredientStatus ingredientStatus) {
        this.ingredientStatus = ingredientStatus;
    }

    
}
