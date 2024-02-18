package com.thien.ingredients.bussiness.components;

import java.util.Iterator;
import java.util.Map;

public class IdGenerator {
    
    private int currentMaxId;
    private String prefixId;

    public IdGenerator(Map<String, ?> map, String prefixId) {
        this.prefixId = prefixId;
        this.currentMaxId = 0;
        if (map != null && !map.isEmpty()) {
            Iterator<String> iterator = map.keySet().iterator();
            int tempNumber;

            while (iterator.hasNext()) {
                String currentMaxIdString = "" + iterator.next().substring(prefixId.length()-1, iterator.next().length()-1);
                tempNumber = Integer.parseInt(currentMaxIdString);
                if (tempNumber > this.currentMaxId) {
                    this.currentMaxId = tempNumber;
                }
            }
        }
    }

    public String generateId() {
        return this.prefixId + currentMaxId++;
    }

}
