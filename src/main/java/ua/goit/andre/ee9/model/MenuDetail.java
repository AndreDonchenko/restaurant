package ua.goit.andre.ee9.model;

import javax.persistence.*;

/**
 * Created by Andre on 28.05.2016.
 */

@Entity
@Table(name = "menu_detail")
public class MenuDetail {

    @Id
    @SequenceGenerator(name="menu_detail_id_seq",
            sequenceName="menu_detail_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="menu_detail_id_seq")
    @Column(name = "id", updatable=false)
    private int id;

    @Column(name="menu_id")
    private int menuId;

    @Column(name = "dish_id")
    private int dishId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    @Override
    public String toString() {
        return "MenuDetail{" +
                "id=" + id +
                ", menuId=" + menuId +
                ", dishId=" + dishId +
                '}';
    }
}
