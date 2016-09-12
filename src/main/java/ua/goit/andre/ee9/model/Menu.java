package ua.goit.andre.ee9.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Andre on 28.05.2016.
 */

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @SequenceGenerator(name="menu_id_seq",
            sequenceName="menu_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="menu_id_seq")
    @Column(name = "id", updatable=false)
    private int id;

    @Column (name = "menu_name")
    private String menuName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="menu_detail",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", menuName='" + menuName + '\'' +
                ", dishes=" + dishes +
                '}';
    }
}
