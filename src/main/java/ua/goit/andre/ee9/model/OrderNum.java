package ua.goit.andre.ee9.model;

import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Andre on 28.05.2016.
 */
@Entity
@Table(name = "order_num")
public class OrderNum {

    @Id
    @SequenceGenerator(name="order_detail_id_seq",
            sequenceName="order_detail_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="order_detail_id_seq")
    @Column(name = "id", updatable=false)
    private int id;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "orderNum")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @Column(name = "table_num")
    private int tableNum;

    @Column(name = "date_order")
    private Date dateOrder;

    @Column(name="open", columnDefinition = "boolean default true")
    private boolean open = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public String toString() {
        return "OrderNum{" +
                "id=" + id +
                ", orderDetails=" + orderDetails +
                ", tableNum=" + tableNum +
                ", dateOrder=" + dateOrder +
                ", open=" + open +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderNum)) return false;

        OrderNum orderNum = (OrderNum) o;

        return getId() == orderNum.getId();

    }

    @Override
    public int hashCode() {
        return getId();
    }
}
