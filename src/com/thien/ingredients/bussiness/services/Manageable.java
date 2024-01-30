package com.thien.ingredients.bussiness.services;

import java.util.Collection;

public interface Manageable {
    public void addNew();
    public void update(String id);
    public void delete(String id);
    public <E> void showAll(Collection<E> collection);
}
