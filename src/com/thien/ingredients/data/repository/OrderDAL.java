package com.thien.ingredients.data.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thien.ingredients.bussiness.model.Order;
import com.thien.ingredients.data.FileManagement;

public class OrderDAL {

    private FileManagement fileManagement = new FileManagement();
    private boolean flag;
    public Map<String, Order> orderMap;

    public OrderDAL() {
        this.orderMap = new HashMap<String, Order>();
        
    }

    public boolean loadFromFile(List<Order> list, String orderFilePath) {
        flag = fileManagement.loadFromFile(list, orderFilePath);
        if (flag) {
            for (Order order : list) {
                orderMap.put(order.getId(), order);
            }
        }
        return flag;
    }

    public boolean writeToFile(HashMap<String, Order> orderMap, String orderFilePath) {
        flag = fileManagement.saveToFileToFile(orderMap, orderFilePath);
    }
}
