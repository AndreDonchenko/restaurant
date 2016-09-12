package ua.goit.andre.ee9.dao;

import ua.goit.andre.ee9.model.Dish;
import ua.goit.andre.ee9.model.Stock;

/**
 * Created by Andre on 06.09.2016.
 */
public class NotEnoughStockException extends Exception {
    private Stock stock;

    public NotEnoughStockException(String message, Stock stock) {
        super(message);
        this.stock = stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
