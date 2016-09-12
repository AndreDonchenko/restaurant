package ua.goit.andre.ee9.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.andre.ee9.model.*;

import java.sql.Date;
import java.util.List;

/**
 * Created by Andre on 06.06.2016.
 */
public class HPreparedDishDao extends Dao<PreparedDish> {

    HRecipeDao recipeDao;
    HStockDao stockDao;

    @Override
    @Transactional
    public PreparedDish getById(Integer id) {
        return sessionFactory.getCurrentSession().load(PreparedDish.class, id);
    }

    @Override
    @Transactional
    public List<PreparedDish> getAll() {
        return sessionFactory.getCurrentSession().createQuery("select e from PreparedDish e").list();
    }

    @Transactional
    public List<PreparedDish> getByOrder(OrderNum orderNum) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select e from PreparedDish e where e.order = :orderNum");
        query.setParameter("orderNum", orderNum);
        return query.list();
    }

    @Override
    @Transactional
    public void delAll() {
        sessionFactory.getCurrentSession().createQuery("delete from PreparedDish").executeUpdate();
    }

    @Transactional
    public void PrepareDish (Dish dish, Employee employee, OrderNum orderNum, Date date) throws EmptyRecipeException, NotEnoughStockException {
        List<Ingredient> missingIngredients = stockDao.getIngredientsMissingOnStock(dish);
        if (missingIngredients.size() == 0) {
            List<Recipe> recipeList = recipeDao.getAllByDish(dish);
            for (Recipe r : recipeList) {
                Ingredient ingredient = r.getIngredient();
                Stock stock = stockDao.getStockByName(r.getIngredient().getIngredientName());
                Double qty = stock.getQty() - r.getQty();
                if (qty > 0) {
                    stock.setQty(qty);
                    stockDao.add(stock);
                } else throw new NotEnoughStockException("Not enough ingredient on stock", stock);
            }
            PreparedDish preparedDish = new PreparedDish();
            preparedDish.setDish(dish);
            preparedDish.setEmployee(employee);
            preparedDish.setOrder(orderNum);
            preparedDish.setPrepareDate(date);
            add(preparedDish);
        } else throw new NotEnoughStockException("Not enough ingredient on stock", null);
    }

    public void setStockDao(HStockDao stockDao) {
        this.stockDao = stockDao;
    }

    public void setRecipeDao(HRecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }
}
