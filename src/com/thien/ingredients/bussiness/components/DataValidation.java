package com.thien.ingredients.bussiness.components;



import com.thien.ingredients.gui.utilities.DataInputter;

public class DataValidation {

    public DataValidation() {

    }

    public String inputId(String prefixId) {
        String id = "";
        //String pattern = prefixId + "\\d++";
        try {
            id = DataInputter.getPatternString("Enter ID: ", "ID must match the format", prefixId + "\\d++");
        } catch (Exception e) {
            System.err.println("Invalid ID format. Please try again.");
        }
        return id;
    }

    public int inputQuantity() {
        return DataInputter.getInteger("Enter quantity", "Quantity must be a number and cannot be less than zero", 0);   
    }

    
}
