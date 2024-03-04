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
    
    /**
     * Constructor for DispensingDrinkDAO.
     * Initializes the DAO with necessary dependencies and loads orders from a file.
     *
     * @param manageIngredientDAO     The data access object for managing ingredients.
     * @param manageBeverageRecipeDAO The data access object for managing beverage recipes.
     * @param orderPathFile           The file path for storing order data.
     */
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
        IdGenerator idGenerator = new IdGenerator(orderMap, prefixId);
        String id = idGenerator.generateId();
        Map<String, Integer> orderBeverageRecipe = drinkCollection("D", id);

        if (orderBeverageRecipe.isEmpty()) {
            System.out.println("emty order");
        } else {
            
            
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
    private Map<String, Integer> drinkCollection(String prefixId, String orderId) {
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
                int oldQuantity = 0;
                try {
                    oldQuantity = orderMap.get(orderId).getOrderBeverageRecipe().get(beverageId);
                } catch (Exception e) {
                    System.out.println("");
                }
                quantity = DataInputter.getInteger("Enter quantity: ", "Quantity must a number and cannot be less than one", 1);
                // check ingredient quantity
                
                if ( oldQuantity != 0 ) quantity += oldQuantity;
                
                if (isEnoughIngredient(beverageId, quantity) == false) {
                    System.out.println("Ingredient not enough");
                } else {
                    orderBeverageRecipe.put(beverageId, quantity);
                    System.out.println("drink add thanh cong");
                }
            }

        } while (DataInputter.getYN("Do you want to countinue order?"));
        return orderBeverageRecipe;
    }
    
    /**
     * Checks if there are enough ingredients available to prepare a beverage with the specified ID and quantity.
     * This method retrieves the recipe for the beverage from the manageBeverageRecipeDAO and iterates through
     * its ingredients. For each ingredient, it checks if the quantity available is sufficient to prepare
     * the required quantity of the beverage. If any ingredient is not available or its quantity is insufficient,
     * this method returns false; otherwise, it returns true.
     *
     * @param beverageId The ID of the beverage to check for ingredient availability.
     * @param quantity   The quantity of the beverage to prepare.
     * @return True if there are enough ingredients available, false otherwise.
     */
    private boolean isEnoughIngredient(String beverageId, int quantity) {
        
        Map<String, Integer> map = manageBeverageRecipeDAO.beverageRecipeMap.get(beverageId).getBeverageRecipeIngredients();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String ingredientId = entry.getKey();
            int requiredQuantity = entry.getValue() * quantity;
            System.out.println(manageIngredientDAO.ingredientMap.get(ingredientId).toString());
            System.out.println(requiredQuantity);
            if ((manageIngredientDAO.ingredientMap.get(ingredientId).getIngredientStatus() == IngredientStatus.NOT_AVAILABLE) || (manageIngredientDAO.ingredientMap.get(ingredientId).getIngredientStatus() == IngredientStatus.OUT_OF_STOCK))
                return false;
            else if(manageIngredientDAO.ingredientMap.get(ingredientId).getQuantity() < requiredQuantity  ) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Updates the dispensing of a drink order identified by its order ID.
     * This method allows the user to update the details of a drink order, such as adding or removing
     * beverages from the order. If the order is currently being prepared, the user can choose to cancel it
     * or modify the order by adding or removing beverages. If the order is already done, it cannot be
     * modified, and a message indicating this is displayed.
     *
     * @param orderId The ID of the order to update.
     */
    @Override
    public void updateDispensingDrink(String orderId) {
        if (orderMap.get(orderId).getOrderStatus() == OrderStatus.PREPARING) {
            if (!DataInputter.getYN("Do you want to cancel this order?")) {
                manageBeverageRecipeDAO.showAll();
                System.out.println("==Your old order==");
                display(orderId);
                Map<String, Integer> drinkMap = drinkCollection("D", orderId);
                orderMap.get(orderId).setOrderBeverageRecipe(drinkMap);
                System.out.println("");
                display(orderId);
            } else {
                orderMap.remove(orderId);
                System.out.println("Cancel order thanh cong");
            }
        } else {
            System.out.println("Order have done and cannot edit");
        }
    }

    /**
     * This method show one order
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
    
    /**
    * Updates the status of ingredients based on the orders being prepared.
    * This method iterates through all preparing orders, deducts the required quantities
    * of ingredients from the available stock, and updates the ingredient status accordingly.
    */
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
    
    /**
    * Updates the status of beverages based on the orders being prepared.
    * This method iterates through all preparing orders and sets the status of 
    * each beverage in the order's recipe to AVAILABLE.
    */
    private void setBeverageStatus() {
        for (Order o : orderMap.values()) {
            if (o.getOrderStatus() == OrderStatus.PREPARING) {
                for (String key : o.getOrderBeverageRecipe().keySet()) {
                    manageBeverageRecipeDAO.beverageRecipeMap.get(key).setBeverageRecipeStatus(BeverageRecipeStatus.AVAILABLE);
                }
            }
        }
    }
    /**
    * Sets the status of all orders in the order map to DONE.
    * This method iterates through all orders in the order map and 
    * sets their status to DONE, indicating that they have been processed.
    */
    private void setOrderStatus() {
        for (Order o : orderMap.values()) {
            o.setOrderStatus(OrderStatus.DONE);
        }
    }
    
    /**
     * Converts the order map to a list of orders.
     * This method iterates through the order map and converts its values (orders)
     * into a list of orders. This list is then returned.
     *
     * @return A list containing all orders from the order map.
     */
    private List<Order> converMapToList() {
        List<Order> list = new ArrayList<>();
        for (Order o : orderMap.values()) {
            list.add(o);
        }
        return list;
    }
}
