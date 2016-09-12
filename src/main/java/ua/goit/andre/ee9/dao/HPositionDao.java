package ua.goit.andre.ee9.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.andre.ee9.model.Position;

import java.util.List;

/**
 * Created by Andre on 06.06.2016.
 */
public class HPositionDao extends Dao<Position> {

    @Override
    @Transactional
    public Position getById(Integer id) {
        return sessionFactory.getCurrentSession().get(Position.class, id);
    }

    @Override
    @Transactional
    public List<Position> getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select e from Position e where e.positionName like :name");
        query.setParameter("name", name);
        return query.list();
    }

    @Override
    @Transactional
    public List<Position> getAll() {
        return sessionFactory.getCurrentSession().createQuery("select e from Position e").list();
    }

    @Override
    @Transactional
    public void delAll() {
        sessionFactory.getCurrentSession().createQuery("delete from Position").executeUpdate();
    }
}
