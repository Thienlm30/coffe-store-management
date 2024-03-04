package com.thien.ingredients.bussiness.model;

import java.io.Serializable;

/**
 * Represents an ingredient used in beverage recipes.
 * This class contains information about an ingredient, including its ID, name, quantity, unit, and status.
 * @author Thielm30
 */
public class Ingredient implements Serializable, Comparable<Ingredient> {
    
    private String id;
    private String name;
    private int quantity;
    private String unit;
    private IngredientStatus ingredientStatus;

    public Ingredient(String id, String name, int quantity, String unit) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public IngredientStatus getIngredientStatus() {
        return ingredientStatus;
    }

    public void setIngredientStatus(IngredientStatus ingredientStatus) {
        this.ingredientStatus = ingredientStatus;
    }

    @Override
    public String toString() {
        return String.format("|%-10s|%-30s|%10d|%10s|%20s|", id, name, quantity, unit, ingredientStatus);
    }

    @Override
    public int compareTo(Ingredient o) {
        if (this.getId().compareTo(o.getId()) > 0){
            return 1;
        } else if (this.getId().compareTo(o.getId()) < 0) {
            return -1;
        } else {
            return 0;
        }
    }
    
    
}
