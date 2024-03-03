package com.thien.ingredients.bussiness.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thien.ingredients.bussiness.components.IdGenerator;
import com.thien.ingredients.bussiness.model.Ingredient;
import com.thien.ingredients.bussiness.model.IngredientStatus;
import com.thien.ingredients.data.repository.IngredientDAL;
import com.thien.ingredients.gui.utilities.DataInputter;

public class ManageIngredientDAO implements Manageable {

        public Map<String, Ingredient> ingredientMap;
        private IngredientDAL ingredientDAL;
        private String ingredientPathFile;

        public ManageIngredientDAO(String ingredientPathFile) {
            this.ingredientDAL = new IngredientDAL();
            List<Ingredient> list = new ArrayList<>();
            if (ingredientDAL.loadFromFile(list, ingredientPathFile)) {
                this.ingredientMap = new HashMap<>();
                for (Ingredient i : list) {
                    this.ingredientMap.put(i.getId(), i);
                }
                System.out.println("Load thanh cong tu ingredient constructor");
            } else {
                System.err.println("Error loading ingredients from constructor");
            }
            this.ingredientPathFile = ingredientPathFile;
        }

    @Override
    public void addNew(String prefixId) {
        IdGenerator idGenerator = new IdGenerator(ingredientMap, prefixId);
        String id = idGenerator.generateId();
        String name = DataInputter.getNonBlankString("Enter ingredient name :", "Name cannot be blank");
        int quantity = DataInputter.getInteger("Enter ingredient quantity: ", "Quantity must be a number and cannot be less than zero", 0);
        String unit = DataInputter.getNonBlankString("Enter unit (Example: ml, g, cup,...): ", "Unit cannot be blank");
        
        Ingredient ingredient = new Ingredient(id, name, quantity, unit);
        ingredientMap.put(id, ingredient);
    }

    @Override
    public void update(String id) {
        String name = DataInputter.getStrCanBlank("Enter new ingredient name: ");
        if (name.isEmpty()) name = ingredientMap.get(id).getName();

        String quantityString;
        int quantity = -1;
        boolean flag = true;
        while (flag) {
            quantityString = DataInputter.getStrCanBlank("Enter new ingredient quantity: ");
            if (quantityString.isEmpty()) {
                quantity = ingredientMap.get(id).getQuantity();
                flag = false;
            }
            else {
                try {
                    quantity = Integer.parseInt(quantityString);
                    if (quantity < 0) throw new Exception();
                    flag = false;
                } catch (Exception e) {
                    System.err.println("Quantity must be a number and cannot be less than zero");                
                }
            }
        }

        String unit = DataInputter.getStrCanBlank("Enter new ingredient unit (Example: ml, g, cup,...): ");
        if (unit.isEmpty()) unit = ingredientMap.get(id).getUnit();

        ingredientMap.get(id).setName(name);
        ingredientMap.get(id).setQuantity(quantity);
        ingredientMap.get(id).setUnit(unit);
    }

    @Override
    public void delete(String id) {
        if  (ingredientMap.get(id) == null) 
            System.out.println("There no ingredient found");
        else {
            display(id);            
            if (DataInputter.getYN("Do you want to delete this ingredient?")) {
                ingredientMap.remove(id);
                System.out.println("Delete successful");
            }
        }
    }

    @Override
    public void showAll() {
        System.out.println(" ------------------------------------------------------------------ ");
        System.out.println("|    ID    |             Name             |   Quantity  |   Unit   |");
        System.out.println(" ------------------------------------------------------------------ ");
        List<Ingredient> list = convertMapToList();
        list.sort((i1, i2) -> i2.getName().compareToIgnoreCase(i1.getName()));
        for (Ingredient i : list) {
            System.out.println(i.toString());
        }
        System.out.println(" ------------------------------------------------------------------ ");

    }

    private List<Ingredient> convertMapToList() {
        List<Ingredient> list = new ArrayList<>();
        for (Ingredient i : ingredientMap.values()) {
            list.add(i);
        }
        return list;
    }

    public void display(String id) {
        System.out.println(" ------------------------------------------------------------------ ");
        System.out.println("|    ID    |             Name             |   Quantity  |   Unit   |");
        System.out.println(" ------------------------------------------------------------------ ");
        System.out.println(ingredientMap.get(id).toString());
        System.out.println(" ------------------------------------------------------------------ ");
    }

    public boolean isExit(String id) {
        if (ingredientMap.get(id) != null) return true;
        return false;
    }
 
    public void saveToFile() {
        try {
            ingredientDAL.saveToFile(convertMapToList(), ingredientPathFile);
        } catch (Exception e) {
            System.out.println("Save ingredient fail");
        }
    }

}
