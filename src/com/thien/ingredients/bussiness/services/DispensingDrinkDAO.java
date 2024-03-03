package com.thien.ingredients.bussiness.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.thien.ingredients.bussiness.components.DataValidation;
import com.thien.ingredients.bussiness.components.IdGenerator;
import com.thien.ingredients.bussiness.model.BeverageRecipeStatus;
import com.thien.ingredients.bussiness.model.Ingredient;
import com.thien.ingredients.bussiness.model.IngredientStatus;
import com.thien.ingredients.bussiness.model.Order;
import com.thien.ingredients.bussiness.model.OrderStatus;
import com.thien.ingredients.data.repository.OrderDAL;
import com.thien.ingredients.gui.utilities.DataInputter;

public class DispensingDrinkDAO implements Dispensable {

    public Map<String, Order> orderMap;
    private OrderDAL orderDAL;
    private String orderPathFile;
    private ManageIngredientDAO manageIngredientDAO;
    private ManageBeverageRecipeDAO manageBeverageRecipeDAO;

    public DispensingDrinkDAO(ManageIngredientDAO manageIngredientDAO, ManageBeverageRecipeDAO manageBeverageRecipeDAO, String orderPathFile) {
        this.manageIngredientDAO = manageIngredientDAO;
        this.manageBeverageRecipeDAO = manageBeverageRecipeDAO;

        orderDAL = new OrderDAL();
        List<Order> list = new ArrayList<>();
        if (orderDAL.loadFromFile(list, orderPathFile)) {
            this.orderMap = new TreeMap<>();
            for (Order o : list) {
                orderMap.put(o.getId(), o);
            }
            System.out.println("load thanh cong tu order constructor");
        } else {
            System.err.println("Error loading order from constructor");
        }

        this.orderDAL = new OrderDAL();
        this.orderPathFile = orderPathFile;
    }

    /**
     * This method dispensing drinks that have been ordered The order's status
     * is preparing
     *
     * @param prefixId is prefix of the order's id
     */
    @Override
    public void dispensingDrink(String prefixId) {

        Map<String, Integer> orderBeverageRecipe = drinkCollection("D");

        if (orderBeverageRecipe.isEmpty()) {
            System.out.println("emty order");
        } else {
            IdGenerator idGenerator = new IdGenerator(orderMap, prefixId);
            String id = idGenerator.generateId();
            orderMap.put(id, new Order(id, orderBeverageRecipe));
            orderMap.get(id).setOrderStatus(OrderStatus.PREPARING);
            System.out.println("Them order thanh cong");
        }
    }

    /**
     * This method return the list of order drink and it's quantity
     *
     * @param prefixId is the prefix of the drink's id
     * @return a list contain drinks order
     */
    private Map<String, Integer> drinkCollection(String prefixId) {
        Map<String, Integer> orderBeverageRecipe = new HashMap<>();
        DataValidation dataValidation = new DataValidation();
        do {
            String beverageId = dataValidation.inputId(prefixId);
            int quantity;

            // check drink id
            if (manageBeverageRecipeDAO.beverageRecipeMap.get(beverageId) == null) {
                System.out.println("There no drink found");
            } // check drink status
            else if (manageBeverageRecipeDAO.beverageRecipeMap.get(beverageId).getBeverageRecipeStatus() == BeverageRecipeStatus.NOT_AVAILABLE) {
                System.out.println("Drink are not availabe");
            } // check ingredient in recipe
            else {

                quantity = DataInputter.getInteger("Enter quantity: ", "Quantity must a number and cannot be less than one", 1);
                // check ingredient quantity
                if (isEnoughIngredient(beverageId, quantity)) {
                    System.out.println("Ingredient not enough");
                } else {
                    orderBeverageRecipe.put(beverageId, quantity);
                    System.out.println("drink add thanh cong");
                }
            }

        } while (DataInputter.getYN("Do you want to countinue order?"));
        return orderBeverageRecipe;
    }

    private boolean isEnoughIngredient(String beverageId, int quantity) {
        
        Map<String, Integer> map = manageBeverageRecipeDAO.beverageRecipeMap.get(beverageId).getBeverageRecipeIngredients();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String ingredientId = entry.getKey();
            int requiredQuantity = entry.getValue() * quantity;
            System.out.println(manageIngredientDAO.ingredientMap.get(ingredientId).toString());
            System.out.println(requiredQuantity);
            if (manageIngredientDAO.ingredientMap.get(ingredientId).getIngredientStatus() == IngredientStatus.NOT_AVAILABLE || manageIngredientDAO.ingredientMap.get(ingredientId).getIngredientStatus() == IngredientStatus.OUT_OF_STOCK)
                return false;
            if (manageIngredientDAO.ingredientMap.get(ingredientId).getQuantity() < requiredQuantity || manageIngredientDAO.ingredientMap.get(ingredientId).getIngredientStatus() != IngredientStatus.AVAILABLE) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateDispensingDrink(String orderId) {
        if (orderMap.get(orderId).getOrderStatus() == OrderStatus.PREPARING) {
            if (!DataInputter.getYN("Do you want to cancel this order?")) {
                manageBeverageRecipeDAO.showAll();
                System.out.println("==Your old order==");
                display(orderId);
                Map<String, Integer> drinkMap = drinkCollection("D");
                orderMap.get(orderId).setOrderBeverageRecipe(drinkMap);
                System.out.println("");
                display(orderId);
            } else {
                orderMap.remove(orderId);
            }
        } else {
            System.out.println("Order have done and cannot edit");
        }
    }

    /**
     * This method show one order
     *
     * @param id of order
     */
    private void display(String id) {
        System.out.println(" -----------------------------------------------------------------------------------+");
        System.out.println("|    ID    |                      Details                      |       Status       |");
        System.out.println(" -----------------------------------------------------------------------------------+");
        System.out.println(orderMap.get(id).toString());
        System.out.println(" -----------------------------------------------------------------------------------+");
    }

    /**
     * This method show all order
     */
    public void showAll() {
        System.out.println(" ------------------------------------------------------------------------------------+");
        System.out.println("|    ID    |                     Details                        |       Status       |");
        System.out.println(" ------------------------------------------------------------------------------------+");

        for (Order o : orderMap.values()) {
            System.out.println(o.toString());
        }

        System.out.println(" ------------------------------------------------------------------------------------+");
    }

    /**
     * This method sends orders to the database And set the status of the
     * ingredient and beverage recipe Ingredients and beverages that have been
     * used will not be deleted
     */
    public void saveToFile() {
        try {
            // set status của beverage với ingredient
            setIngredientStatus();
            setBeverageStatus();
            setOrderStatus();
            orderDAL.saveToFile(converMapToList(), orderPathFile);
        } catch (Exception e) {
            System.out.println("Save order fail");
        }
    }

    private void setIngredientStatus() {

        System.out.println("ham update status ingre");
        for (Order o : orderMap.values()) {
            // visit preparing order
            if (o.getOrderStatus() == OrderStatus.PREPARING) {

                for (Map.Entry<String, Integer> entry : o.getOrderBeverageRecipe().entrySet()) {
                    String beverageId = entry.getKey();
                    int quantityToSubtract = entry.getValue();
                    Map<String, Integer> beverageIngredients = manageBeverageRecipeDAO.beverageRecipeMap.get(beverageId).getBeverageRecipeIngredients();

                    // Update ingredient status for each ingredient used in the beverage recipe
                    for (Map.Entry<String, Integer> ingredientEntry : beverageIngredients.entrySet()) {
                        String ingredientId = ingredientEntry.getKey();
                        int requiredQuantity = ingredientEntry.getValue() * quantityToSubtract;

                        Ingredient ingredient = manageIngredientDAO.ingredientMap.get(ingredientId);
                        if (ingredient != null) {
                            int currentQuantity = ingredient.getQuantity();
                            int updatedQuantity = currentQuantity - requiredQuantity;
                            ingredient.setQuantity(updatedQuantity);

                            // Set ingredient status based on quantity
                            if (updatedQuantity <= 0) {
                                ingredient.setIngredientStatus(IngredientStatus.OUT_OF_STOCK);
                            } else {
                                ingredient.setIngredientStatus(IngredientStatus.AVAILABLE);
                            }

                            System.out.println("Updated ingredient status for ingredient ID: " + ingredientId);
                        } else {
                            System.err.println("Ingredient ID " + ingredientId + " not found in ingredient map.");
                        }
                    }
                }
            }
        }
    }    
    

    private void setBeverageStatus() {
        for (Order o : orderMap.values()) {
            if (o.getOrderStatus() == OrderStatus.PREPARING) {
                for (String key : o.getOrderBeverageRecipe().keySet()) {
                    manageBeverageRecipeDAO.beverageRecipeMap.get(key).setBeverageRecipeStatus(BeverageRecipeStatus.AVAILABLE);
                }
            }
        }
    }

    private void setOrderStatus() {
        for (Order o : orderMap.values()) {
            o.setOrderStatus(OrderStatus.DONE);
        }
    }

    private List<Order> converMapToList() {
        List<Order> list = new ArrayList<>();
        for (Order o : orderMap.values()) {
            list.add(o);
        }
        return list;
    }
}
