package ua.goit.andre.ee9.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.andre.ee9.model.Menu;
import ua.goit.andre.ee9.model.OrderNum;

import java.util.List;

/**
 * Created by Andre on 12.06.2016.
 */
public class HOrderDao extends Dao<OrderNum> {

    @Override
    @Transactional
    public OrderNum getById(Integer id) {
        return sessionFactory.getCurrentSession().get(OrderNum.class, id);
/*        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select e from OrderNum e where e.id = :id");
        query.setParameter("id",id);
        return (OrderNum) query.uniqueResult();
        */
    }

    @Override
    @Transactional
    public List<OrderNum> getAll() {
        return sessionFactory.getCurrentSession().createQuery("select e from OrderNum e").list();
    }

    @Transactional
    public List<OrderNum> getOpenOrders() {
        return sessionFactory.getCurrentSession().createQuery("select e from OrderNum e where e.open=true").list();
    }

    @Override
    @Transactional
    public void delAll() {
        sessionFactory.getCurrentSession().createQuery("delete from OrderNum").executeUpdate();
    }


}
