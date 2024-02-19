package com.thien.ingredients.bussiness.components;



import com.thien.ingredients.gui.utilities.DataInputter;

public class DataValidation {

    public DataValidation() {

    }

    public int inputQuantity() {
        return DataInputter.getInteger("Enter quantity", "Quantity must be a number and cannot be less than zero", 0);   
    }

    
}
