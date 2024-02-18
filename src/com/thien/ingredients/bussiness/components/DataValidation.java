package com.thien.ingredients.bussiness.components;

import com.thien.ingredients.gui.utilities.DataInputter;

public class DataValidation {
    
    private String ingredientIdPattern = "";
    private String menuItemIdPattern = "";
    private String orderIdPattern = "";

    // private boolean checkId(String id, String pattern) {

    //     return true;
    // }

    public DataValidation() {
        
    }

    public String inputId() {
        String id = "";
        id = DataInputter.getStrCanBlank("Enter id");
        
        return id;
    }

}
