package com.thien.ingredients.bussiness.services;

public interface Dispensable {
    public void dispensingDrink(String menuItemId);
    public void updateDispensingDrink(String menuItemId, int quantity);
}
