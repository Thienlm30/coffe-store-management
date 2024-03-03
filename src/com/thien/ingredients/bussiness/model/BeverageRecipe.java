package com.thien.ingredients.bussiness.model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class BeverageRecipe implements Serializable, Comparable<BeverageRecipe>{

    private String id;
    private String name;
    private Map<String, Integer> beverageRecipeIngredients;
    private BeverageRecipeStatus beverageRecipeStatus;
    
    public BeverageRecipe(String id, String name, Map<String, Integer> beverageRecipeIngredients) {
        this.id = id;
        this.name = name;
        this.beverageRecipeIngredients = new TreeMap<>(beverageRecipeIngredients);
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

    public Map<String, Integer> getBeverageRecipeIngredients() {
        return beverageRecipeIngredients;
    }

    public void setBeverageRecipeIngredients(Map<String, Integer> beverageRecipeIngredients) {
        this.beverageRecipeIngredients = beverageRecipeIngredients;
    }

    public BeverageRecipeStatus getBeverageRecipeStatus() {
        return beverageRecipeStatus;
    }

    public void setBeverageRecipeStatus(BeverageRecipeStatus beverageRecipeStatus) {
        this.beverageRecipeStatus = beverageRecipeStatus;
    }
    
    @Override
    public String toString() {
        return String.format("|%-10s|%30S|%35s|%20s|", id, name, beverageRecipeIngredientsToString(), beverageRecipeStatus);
    }

    private String beverageRecipeIngredientsToString() {
        String result = "";
        if (beverageRecipeIngredients != null) 
            for(Map.Entry<String, Integer> b : beverageRecipeIngredients.entrySet()){
                result += b.getKey() + "(" + b.getValue() + ")" + " ";
            }
        return result;
    }

    @Override
    public int compareTo(BeverageRecipe o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
