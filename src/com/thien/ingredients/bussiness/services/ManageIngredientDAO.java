package com.thien.ingredients.bussiness.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thien.ingredients.bussiness.components.IdGenerator;
import com.thien.ingredients.bussiness.model.Ingredient;
import com.thien.ingredients.data.repository.IngredientDAL;
import com.thien.ingredients.gui.utilities.DataInputter;

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
        IdGenerator idGenerator = new IdGenerator(ingredientMap, "I");
        String id = idGenerator.generateId();
        String name = DataInputter.getNonBlankString("Enter ingredient name", "Name cannot be blank");
        int quantity = DataInputter.getInteger("Enter ingredient quantity", "Quantity must be a number and cannot be less than zero", 0);
        String unit = DataInputter.getNonBlankString("Enter unit (Example: ml, g, cup,...): ", "Unit cannot be blank");
        Ingredient ingredient = new Ingredient(id, name, quantity, unit);
        ingredientMap.put(id, ingredient);
    }

    @Override
    public void update(String id) {
        String name = DataInputter.getStrCanBlank("Enter new ingredient name");
        String quantityString = DataInputter.getStrCanBlank("Enter new ingredient unit");
        String unit = DataInputter.getStrCanBlank("Enter new ingredient unit");
        int quantity;
        if (name.isEmpty()) name = ingredientMap.get(id).getName();

        if (quantityString.isEmpty()) quantity = ingredientMap.get(id).getQuantity();
        else quantity = Integer

        if (unit.isEmpty()) unit = ingredientMap.get(id).getUnit();

        ingredientMap.get(id).setName(name);
        ingredientMap.get(id).setQuantity(quantity);
        ingredientMap.get(id).setUnit(unit);
    }

    @Override
    public void delete(String id) {
        
    }

    @Override
    public <E> void showAll(Collection<E> collection) {
        
    }
    
}
