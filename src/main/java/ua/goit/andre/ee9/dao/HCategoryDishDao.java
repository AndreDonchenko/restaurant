package ua.goit.andre.ee9.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.andre.ee9.model.CategoryDish;

import java.util.List;

/**
 * Created by Andre on 06.06.2016.
 */
public class HCategoryDishDao extends Dao<CategoryDish> {

    @Override
    @Transactional
    public CategoryDish getById(Integer id) {
        return sessionFactory.getCurrentSession().load(CategoryDish.class, id);
    }

    @Override
    @Transactional
    public List<CategoryDish> getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select e from CategoryDish e where e.categoryName like :name");
        query.setParameter("name", name);
        return query.list();
    }

    @Override
    @Transactional
    public List<CategoryDish> getAll() {
        return sessionFactory.getCurrentSession().createQuery("select e from CategoryDish e").list();
    }

    @Override
    @Transactional
    public void delAll() {
        sessionFactory.getCurrentSession().createQuery("delete from CategoryDish").executeUpdate();
    }
}
