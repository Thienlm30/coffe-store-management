package com.thien.ingredients.bussiness.services;

public interface Manageable {
    void addNew(String prefixId);
    void update(String id);
    void delete(String id);
    void showAll();
    
}
