package com.thien.ingredients.bussiness.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.thien.ingredients.bussiness.model.MenuItem;
import com.thien.ingredients.data.repository.MenuDAL;

public class ManageMenuItemDAO implements Manageable {

    private Map<String, MenuItem> menuItemMap;
    
    public ManageMenuItemDAO(String menuPathFile) {
        MenuDAL menuDAL = new MenuDAL();
        List<MenuItem> list = new ArrayList<MenuItem>();
        menuDAL.loadFromFile(list, menuPathFile);
        for (MenuItem m : list) {
            menuItemMap.put(m.getId(), m);
        }
    }

    @Override
    public void addNew() {
        
    }

    @Override
    public void update(String id) {
        
    }

    @Override
    public void delete(String id) {
        
    }

    @Override
    public <E> void showAll(Collection<E> collection) {
        
    }
    
}
