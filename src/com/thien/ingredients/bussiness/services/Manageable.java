package com.thien.ingredients.bussiness.services;

public interface Manageable {
    public void addNew(String prefixId);
    public void update(String id);
    public void delete(String id);
    public void showAll();

    public boolean isExit(String id);
}
