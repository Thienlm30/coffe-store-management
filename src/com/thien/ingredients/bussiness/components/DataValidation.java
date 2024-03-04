package com.thien.ingredients.bussiness.components;

import com.thien.ingredients.gui.utilities.DataInputter;

/**
 * This class provides methods for validating user input data.
 * It includes methods for validating IDs with specified prefixes and quantities.
 *
 * @author Thienlm30
 */
public class DataValidation {

    public DataValidation() {

    }
    /**
    * Prompts the user to input an ID with a specified prefix and validates the format.
    * This method displays a prompt to the user requesting an ID input with a specified prefix.
    * It then validates the input against a regular expression pattern to ensure it matches
    * the required format. If the input does not match the pattern, it prompts the user to
    * try again until a valid ID is provided.
    *
    * @param prefixId The prefix for the ID.
    * @return The validated ID entered by the user.
    */
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
    
    /**
    * Prompts the user to input a quantity and validates the input.
    * This method displays a prompt to the user requesting a quantity input.
    * It then validates the input to ensure it is a non-negative integer.
    * If the input is invalid, it prompts the user to try again until a valid quantity is provided.
    *
    * @return The validated quantity entered by the user.
    */
    public int inputQuantity() {
        return DataInputter.getInteger("Enter quantity", "Quantity must be a number and cannot be less than zero", 0);   
    }

    
}
