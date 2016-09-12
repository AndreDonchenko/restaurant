package ua.goit.andre.ee9.model;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Andre on 29.06.2016.
 */
public class Coocker extends Employee {

    @OneToMany()
    @JoinColumn(name = "employee_id")
    private List<Dish> dishList;

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }
}
