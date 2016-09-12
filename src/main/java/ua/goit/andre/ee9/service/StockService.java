package ua.goit.andre.ee9.service;

import ua.goit.andre.ee9.dao.HStockDao;
import ua.goit.andre.ee9.model.Ingredient;
import ua.goit.andre.ee9.model.Stock;

import java.util.List;

/**
 * Created by Andre on 04.09.2016.
 */
public class StockService {
    private HStockDao stockDao;

    public void setStockDao(HStockDao stockDao) {
        this.stockDao = stockDao;
    }

    public List<Stock> getAll() {
        return stockDao.getAll();
    }

    public List<Stock> getByName(String ingredientNmae) {
        return stockDao.getByName(ingredientNmae);
    }

    public List<Ingredient> getEmptyIngredients() {
        return stockDao.getEmptyIngredients();
    }

    public void addIngredient(Ingredient ingredient, Double qty) {
        stockDao.add(new Stock(ingredient, qty));
    }

    public void del (Stock stock) {
        stockDao.del(stock);
    }
}
