package ua.goit.andre.ee9.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.andre.ee9.model.Ingredient;
import ua.goit.andre.ee9.model.Position;

import java.util.List;

/**
 * Created by Andre on 06.06.2016.
 */
public class HIngredientDao extends Dao<Ingredient> {

    @Override
    @Transactional
    public Ingredient getById(Integer id) {
        return sessionFactory.getCurrentSession().get(Ingredient.class, id);
    }

    @Override
    @Transactional
    public List<Ingredient> getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select e from Ingredient e where e.ingredientName like :name");
        query.setParameter("name", name);
        return query.list();
    }

    @Override
    @Transactional
    public List<Ingredient> getAll() {
        return sessionFactory.getCurrentSession().createQuery("select e from Ingredient e").list();
    }

    @Override
    @Transactional
    public void delAll() {
        sessionFactory.getCurrentSession().createQuery("delete from Ingredient").executeUpdate();
    }


}
