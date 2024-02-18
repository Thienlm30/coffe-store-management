package com.thien.ingredients.bussiness.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thien.ingredients.bussiness.model.Ingredient;
import com.thien.ingredients.data.repository.IngredientDAL;

public class ManageIngredientDAO implements Manageable {

    private Map<String, Ingredient> ingredientMap;

    public ManageIngredientDAO(String ingredientPathFile) {
        IngredientDAL ingredientDAL = new IngredientDAL();
        List<Ingredient> list = new ArrayList<Ingredient>();
        ingredientDAL.loadFromFile(list, ingredientPathFile);
        ingredientMap = new HashMap<String, Ingredient>();
        for (Ingredient i : list) {
            ingredientMap.put(i.getId(), i);
        }
    }

    @Override
    public void addNew() {
        
    }

    @Override
    public void update(String id) {
        
    }

    @Override
    public void delete(String id) {
        
    }

    @Override
    public <E> void showAll(Collection<E> collection) {
        
    }
    
}
