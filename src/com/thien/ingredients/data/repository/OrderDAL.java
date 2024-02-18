package com.thien.ingredients.data.repository;

import java.util.List;
import com.thien.ingredients.bussiness.model.Order;
import com.thien.ingredients.data.FileManagement;

public class OrderDAL {

    private FileManagement fileManagement;
    
    public OrderDAL() {
        this.fileManagement = new FileManagement();
    }

    public boolean loadFromFile(List<Order> orderList, String orderPathFile) {
        return fileManagement.loadListFromFile(orderList, orderPathFile);
    }

    public boolean saveToFile(List<Order> orderList, String orderPathFile) {
        return fileManagement.saveListToFile(orderList, orderPathFile, "Save order to file successfully!");
    }
}
