package ua.goit.andre.ee9.model;

import javax.persistence.*;

/**
 * Created by Andre on 28.05.2016.
 */

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @SequenceGenerator(name="order_detail_id_seq",
            sequenceName="order_detail_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="order_detail_id_seq")
    @Column(name = "id", updatable=false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderNum orderNum;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name="qty")
    private double qty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderNum getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(OrderNum orderNum) {
        this.orderNum = orderNum;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "dish=" + dish +
                ", qty=" + qty +
                '}';
    }
}
