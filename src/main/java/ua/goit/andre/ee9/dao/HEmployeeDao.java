package ua.goit.andre.ee9.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.andre.ee9.model.Employee;
import ua.goit.andre.ee9.model.Position;

import java.util.List;

/**
 * Created by Andre on 06.06.2016.
 */
public class HEmployeeDao extends Dao<Employee> {

    @Override
    @Transactional
    public Employee getById(Integer id) {
        //return sessionFactory.getCurrentSession().load(Employee.class, id);
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select e from Employee e where e.id = :id");
        query.setParameter("id",id);
        return (Employee) query.uniqueResult();
    }

    @Override
    @Transactional
    public List<Employee> getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select e from Employee e where e.name like :name");
        query.setParameter("name", name);
        return query.list();
    }

    @Override
    @Transactional
    public List<Employee> getAll() {
        return sessionFactory.getCurrentSession().createQuery("select e from Employee e").list();
    }

    @Override
    @Transactional
    public void delAll() {
        sessionFactory.getCurrentSession().createQuery("delete from Employee").executeUpdate();
    }

    @Transactional
    public List<Employee> getAllByPosition(Position position) {
        String positionName = position.getPositionName();
        Query query = sessionFactory.getCurrentSession().createQuery("select e from Employee e where e.position=:position");
        query.setParameter("position", position);
        return query.list();
    }
}
