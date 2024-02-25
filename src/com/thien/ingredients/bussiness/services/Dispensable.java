package com.thien.ingredients.bussiness.services;

public interface Dispensable {
    public void dispensingDrink(String prefixId);
    public void updateDispensingDrink(String menuItemId, int quantity);
}
