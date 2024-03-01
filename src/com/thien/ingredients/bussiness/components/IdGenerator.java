package com.thien.ingredients.bussiness.components;

import java.util.Map;

public class IdGenerator {
    
    private int currentMaxId;
    private String prefixId;

    public IdGenerator(Map<String, ?> map, String prefixId) {
        this.prefixId = prefixId;
        this.currentMaxId = 0;
        
        if (map != null && !map.isEmpty()) {
            for (String key : map.keySet()) {
                if (key.startsWith(prefixId)) {
                    String numericPart = key.substring(prefixId.length()); // Get the number after prefix
                    try {
                        int numericValue = Integer.parseInt(numericPart);
                        if (numericValue > this.currentMaxId) {
                            this.currentMaxId = numericValue;
                        }
                    } catch (NumberFormatException e) {
                        // Xử lý trường hợp không thể chuyển đổi thành số
                        // Có thể thông báo lỗi hoặc bỏ qua
                        System.err.println("ID Genertator error");
                    }
                }
            }
        }
    }

    public String generateId() {
        return this.prefixId + (++currentMaxId);
    }

}
