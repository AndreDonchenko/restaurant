package ua.goit.andre.ee9.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.andre.ee9.model.CategoryDish;
import ua.goit.andre.ee9.model.Dish;
import ua.goit.andre.ee9.model.Position;

import java.util.List;

/**
 * Created by Andre on 06.06.2016.
 */
public class HDishDao extends Dao<Dish> {

    @Override
    @Transactional
    public Dish getById(Integer id) {
        Dish dish = sessionFactory.getCurrentSession().get(Dish.class, id);
        return dish;

    }

    @Override
    @Transactional
    public List<Dish> getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select d from Dish d where d.dishName like :name");
        query.setParameter("name", name);
        return query.list();
    }

    @Override
    @Transactional
    public List<Dish> getAll() {
        return sessionFactory.getCurrentSession().createQuery("select d from Dish d").list();
    }

    @Override
    @Transactional
    public void delAll() {
        sessionFactory.getCurrentSession().createQuery("delete from Dish").executeUpdate();
    }

    @Transactional
    public List<Dish> getAllByCategory(CategoryDish categoryDish) {
        //String categoryName = categoryDish.getCategoryName();
        Query query = sessionFactory.getCurrentSession().createQuery("select d from Dish d where d.categoryDish=:category"); //"select e from Employee e where e.position=:position"
        query.setParameter("category", categoryDish);
        return query.list();
    }
}
