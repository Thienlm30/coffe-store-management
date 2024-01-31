package com.thien.ingredients.bussiness.model;

import java.util.HashMap;
import java.util.Map;

public class MenuItem {

    private String id;
    private String name;
    private Map<String, Integer> menuItemIngredients;
    
    public MenuItem(String id, String name, Map<String, Integer> menuItemIngredients) {
        this.id = id;
        this.name = name;
        this.menuItemIngredients = new HashMap<String, Integer>();
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

    public Map<String, Integer> getMenuItemIngredients() {
        return menuItemIngredients;
    }

    public void setMenuItemIngredients(Map<String, Integer> menuItemIngredients) {
        this.menuItemIngredients = menuItemIngredients;
    }

}
