package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the shopping list of a user.
 */
public class ShoppingList {
    private List<FoodItem> shoppingList;

    public ShoppingList() {
        this.shoppingList = new ArrayList<>();
    }

    public void addToShoppingList(FoodItem item) {
        shoppingList.add(item);
    }

    public Boolean removeFromShoppingList(FoodItem item) {
        return this.shoppingList.remove(item);
    }
}
