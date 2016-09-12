package ua.goit.andre.ee9.dao;

import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.andre.ee9.model.Dish;

import ua.goit.andre.ee9.model.Recipe;

import java.util.List;

/**
 * Created by Andre on 03.09.2016.
 */
public class HRecipeDao extends Dao<Recipe> {
    @Override
    @Transactional
    public Recipe getById(Integer id) {
        return sessionFactory.getCurrentSession().get(Recipe.class, id);
    }

    @Override
    @Transactional
    public List<Recipe> getAll() {
        return sessionFactory.getCurrentSession().createQuery("select r from Recipe r").list();
    }

    @Override
    @Transactional
    public void delAll() {
        sessionFactory.getCurrentSession().createQuery("delete from Recipe").executeUpdate();
    }

    @Transactional
    public List<Recipe> getAllByDish(Dish dish) {
        Query query = sessionFactory.getCurrentSession().createQuery("select r from Recipe r where r.dishId=:dishId");
        query.setParameter("dishId", dish.getId());
        return query.list();
    }

}
