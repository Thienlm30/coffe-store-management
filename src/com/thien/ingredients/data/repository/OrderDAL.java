package com.thien.ingredients.data.repository;

import java.util.List;
import com.thien.ingredients.bussiness.model.Order;
import com.thien.ingredients.data.FileManagement;

/**
 * Data Access Layer (DAL) class for handling order data.
 * This class provides methods to load order data from a file and save order data to a file.
 * @author Thienlm30
 */
public class OrderDAL {

    private FileManagement fileManagement;
    
    /**
    * Constructs an OrderDAL object.
    * This constructor initializes the FileManagement object used for file operations.
    */
    public OrderDAL() {
        this.fileManagement = new FileManagement();
    }
    
    /**
    * Loads order data from a file.
    * 
    * @param orderList The list to populate with loaded orders.
    * @param orderPathFile The path to the file containing order data.
    * @return True if the loading process is successful, false otherwise.
    */
    public boolean loadFromFile(List<Order> orderList, String orderPathFile) {
        return fileManagement.loadListFromFile(orderList, orderPathFile);
    }
    
    /**
    * Saves order data to a file.
    * 
    * @param orderList The list containing orders to be saved.
    * @param orderPathFile The path to the file where order data will be saved.
    * @return True if the saving process is successful, false otherwise.
    */
    public boolean saveToFile(List<Order> orderList, String orderPathFile) {
        return fileManagement.saveListToFile(orderList, orderPathFile, "Save order to file successfully!");
    }
}
