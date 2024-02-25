package com.thien.ingredients.bussiness.model;

import java.util.Map;
import java.util.TreeMap;

public class MenuItem {

    private String id;
    private String name;
    private Map<String, Integer> menuItemIngredients;
    
    public MenuItem(String id, String name, Map<String, Integer> menuItemIngredients) {
        this.id = id;
        this.name = name;
        this.menuItemIngredients = new TreeMap<String, Integer>();
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

    @Override
    public String toString() {
        return String.format("|%10s|%30S|%30s|", id, name, menuItemIngredientsToString());
    }

    private String menuItemIngredientsToString() {
        String result = "";
        if (menuItemIngredients != null) 
            for(String i : menuItemIngredients.keySet()){
                result += i + " ";
            }
        
        return result;
    }
}
