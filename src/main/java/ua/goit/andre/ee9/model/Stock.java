package ua.goit.andre.ee9.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Andre on 28.05.2016.
 */

@Entity
@Table(name = "stock")
public class Stock implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "ingredient")
    private Ingredient ingredient;

    @Column(name = "qty")
    private double qty;

    public Stock() {
    }

    public Stock(Ingredient ingredient, double qty) {
        this.ingredient = ingredient;
        this.qty = qty;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }
}
