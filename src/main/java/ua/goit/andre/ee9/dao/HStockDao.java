package ua.goit.andre.ee9.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.andre.ee9.model.Dish;
import ua.goit.andre.ee9.model.Ingredient;
import ua.goit.andre.ee9.model.Recipe;
import ua.goit.andre.ee9.model.Stock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre on 06.06.2016.
 */
public class HStockDao extends Dao<Stock> {

    HIngredientDao ingredientDao;
    HRecipeDao recipeDao;

    public void setRecipeDao(HRecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public void setIngredientDao(HIngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    @Override
    @Transactional
    public Stock getById(Integer id) {
        return sessionFactory.getCurrentSession().get(Stock.class, id);
    }

    @Override
    @Transactional
    public List<Stock> getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        List<Stock> result = new ArrayList();
        List<Ingredient> ingredient = ingredientDao.getByName(name);
        if (ingredient.size() > 0) {
            Query query = session.createQuery("select e from Stock e where e.ingredient = :name order by e.ingredient");
            query.setParameter("name", ingredient.get(0));
            result = query.list();
        }
        return result;
    }

    @Transactional
    public List<Ingredient> getIngredientsMissingOnStock(Dish dish) throws EmptyRecipeException {
        List<Ingredient> result = new ArrayList();
        List<Recipe> recipeList = recipeDao.getAllByDish(dish);
        if (null == recipeList || recipeList.size()==0) throw new EmptyRecipeException("Receipt for dish " + dish.getDishName() + " is empty!!!", dish);
        for (Recipe r: recipeList) {
            Stock stock = getStockByName(r.getIngredient().getIngredientName());
            Double stockQty = (null == stock) ? 0 : stock.getQty();
            if (r.getQty() > stockQty) {
                result.add(r.getIngredient());
            }
        }
        return result;
    }

    @Transactional
    public Stock getStockByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        List<Stock> result = new ArrayList();
        List<Ingredient> ingredient = ingredientDao.getByName(name);
        if (ingredient.size() > 0) {
            Query query = session.createQuery("select e from Stock e where e.ingredient = :name order by e.ingredient");
            query.setParameter("name", ingredient.get(0));
            result = query.list();
        }
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }

    }

    @Transactional
    public List<Ingredient> getEmptyIngredients() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery("SELECT ingredient.ingredient_name FROM " +
                "public.ingredient LEFT JOIN public.stock ON ingredient.ingredient_name = stock.ingredient WHERE stock.ingredient is NULL;");
        return query.list();
    }

    @Override
    @Transactional
    public List<Stock> getAll() {
        return sessionFactory.getCurrentSession().createQuery("select e from Stock e order by e.ingredient").list();
    }

    @Override
    @Transactional
    public void delAll() {
        sessionFactory.getCurrentSession().createQuery("delete from Stock").executeUpdate();
    }
}
