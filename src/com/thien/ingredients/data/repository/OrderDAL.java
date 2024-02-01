package com.thien.ingredients.data.repository;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thien.ingredients.bussiness.model.Order;
import com.thien.ingredients.data.FileManagement;

public class OrderDAL {

    private FileManagement fileManagement;
    private boolean flag;
    public Map<String, Order> orderMap;

    public OrderDAL() {
        this.orderMap = new HashMap<String, Order>();
        this.fileManagement = new FileManagement();
    }

    public boolean loadFromFile(Map<String, Order> orderMap, String orderFilePath) {
        List<Order> list = new ArrayList<Order>();
        flag = fileManagement.loadListFromFile(list, orderFilePath);
        if (flag) {
            for (Order order : list) {
                orderMap.put(order.getId(), order);
            }
        }
        return flag;
    }

    public boolean saveToFile(Map<String, Order> orderMap, String orderFilePath) {
        flag = fileManagement.saveListToFile(fileManagement.convertMapToList(orderMap), orderFilePath, "Save order to file successfully!");
        return flag;
    }
}
