package com.thien.ingredients.bussiness.services;

public interface Reportable {
    public void availableIngredient();
    public void outOfStockIngredient();
    public void showAllDispensingDrink();

    public boolean isExit(String id);
}
