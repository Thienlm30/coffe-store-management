package com.thien.ingredients.bussiness.model;

/**
 * This enumeration represent the status of Ingredient
 * OUT_OF_STOCK: The ingredient's quantity is zero
 * NOT_AVAILABLE: The ingredient is not yet in the store
 * AVAILABLE: The ingredient has been use and in stock
 * @author Thienlm30
 */
public enum IngredientStatus {
    OUT_OF_STOCK,
    NOT_AVAILABLE,
    AVAILABLE;
}
