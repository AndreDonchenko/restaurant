package ua.goit.andre.ee9.service;

import ua.goit.andre.ee9.dao.HEmployeeDao;
import ua.goit.andre.ee9.dao.HOrderDao;
import ua.goit.andre.ee9.dao.HOrderDetailDao;
import ua.goit.andre.ee9.model.OrderDetail;
import ua.goit.andre.ee9.model.OrderNum;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by Andre on 16.08.2016.
 */
public class OrderService {
    private HOrderDao orderDao;
    private HOrderDetailDao orderDetailDao;
    private HEmployeeDao employeeDao;

    public List<OrderNum> getAllOrders() {
        return orderDao.getAll();
    }

    public List<OrderNum> getOpenOrders() {
        return orderDao.getOpenOrders();
    }

    public void saveOrder(OrderNum orderNum) {
        orderDao.add(orderNum);
    }

    public void delOrder (OrderNum orderNum) {orderDao.del(orderNum);}

    public OrderNum getOrderById(Integer id) {
        return orderDao.getById(id);
    }

    public void setOrderDao(HOrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setOrderDetailDao(HOrderDetailDao orderDetailDao) {
        this.orderDetailDao = orderDetailDao;
    }

    public void setEmployeeDao(HEmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public List<OrderDetail> getOrderDetailById(Integer id) {
        return orderDetailDao.getByOrderId(id);
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetailDao.add(orderDetail);
    }

    public void delOrderDetail(Integer detailId) {
        orderDetailDao.del(orderDetailDao.getById(detailId));
    }
}
