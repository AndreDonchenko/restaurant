package ua.goit.andre.ee9.model;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Andre on 29.06.2016.
 */
public class Waiter extends Employee {

    @OneToMany()
    @JoinColumn(name = "employee_id")
    private List<OrderNum> orders;

    public List<OrderNum> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderNum> orders) {
        this.orders = orders;
    }
}
