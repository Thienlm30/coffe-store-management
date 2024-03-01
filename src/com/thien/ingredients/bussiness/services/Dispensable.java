package com.thien.ingredients.bussiness.services;

public interface Dispensable {
    void dispensingDrink(String prefixId);
    void updateDispensingDrink(String menuItemId, int quantity);
}
