package com.thien.ingredients.bussiness.components;

import java.util.Map;

/**
 * This class provides functionality for generating unique IDs with a specified prefix.
 * It scans a given map of IDs to determine the current maximum numeric value following the prefix,
 * and then generates the next available ID by incrementing this maximum value.
 * 
 *@author Thienlm30
 */
public class IdGenerator {
    
    private int currentMaxId;
    private String prefixId;
    
    /**
    * Constructs a new IdGenerator instance with the specified prefix.
    * The constructor initializes the current maximum ID based on existing IDs in the provided map.
    *
    * @param map The map containing existing IDs.
    * @param prefixId The prefix for the generated IDs.
    */
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
    
    /**
     * Generates the next available ID by incrementing the current maximum ID.
     *
     * @return The generated ID with the specified prefix.
     */
    public String generateId() {
        return this.prefixId + (++currentMaxId);
    }

}
