package ua.goit.andre.ee9.dao;

import ua.goit.andre.ee9.model.Dish;

/**
 * Created by Andre on 06.09.2016.
 */
public class EmptyRecipeException extends Exception {
    private Dish dish;

    public EmptyRecipeException(String message, Dish dish) {
        super(message);
        this.dish = dish;
    }

    public Dish getDish() {
        return dish;
    }
}
