package com.thien.cfsmanagement.bussiness.model;

import java.util.ArrayList;
import java.util.List;

public class MenuElement {

    private String id;
    private String name;
    private List<Ingredients> listOfIngredientes;
    
    public MenuElement(String id, String name, List<Ingredients> list) {
        this.id = id;
        this.name = name;
        this.listOfIngredientes = new ArrayList<Ingredients>(list);
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

    public List<Ingredients> getListOfIngredientes() {
        return listOfIngredientes;
    }

    public void setListOfIngredientes(List<Ingredients> listOfIngredientes) {
        this.listOfIngredientes = listOfIngredientes;
    }

    

    
    
}
