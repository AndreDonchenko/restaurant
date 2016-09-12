package ua.goit.andre.ee9.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.andre.ee9.model.Menu;

import java.util.List;

/**
 * Created by Andre on 06.06.2016.
 */
public class HMenuDao extends Dao<Menu> {

    @Override
    @Transactional
    public Menu getById(Integer id) {
        return sessionFactory.getCurrentSession().get(Menu.class, id);
    }

    @Override
    @Transactional
    public List<Menu> getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select m from Menu m where m.menuName like :name");
        query.setParameter("name", name);
        return query.list();
    }


    @Override
    @Transactional
    public List<Menu> getAll() {
        return sessionFactory.getCurrentSession().createQuery("select m from Menu m").list();
    }

    @Override
    @Transactional
    public void delAll() {
        sessionFactory.getCurrentSession().createQuery("delete from Menu").executeUpdate();
    }

}
