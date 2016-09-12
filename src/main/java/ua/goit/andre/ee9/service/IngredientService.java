package ua.goit.andre.ee9.service;

import ua.goit.andre.ee9.dao.HIngredientDao;
import ua.goit.andre.ee9.model.Ingredient;

import java.util.List;

/**
 * Created by Andre on 03.09.2016.
 */
public class IngredientService {
    HIngredientDao ingredientDao;

    public void setIngredientDao(HIngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public List<Ingredient> getAll() {
        return ingredientDao.getAll();
    }

    public void add(Ingredient ingredient) {
        ingredientDao.add(ingredient);
    }

    public void del(Ingredient ingredient) {
        ingredientDao.del(ingredient);
    }

    public Ingredient getByName(String ingredientName) {
        List<Ingredient> result = ingredientDao.getByName(ingredientName);
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }
}
