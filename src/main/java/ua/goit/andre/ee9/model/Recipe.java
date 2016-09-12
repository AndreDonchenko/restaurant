package ua.goit.andre.ee9.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by Andre on 28.05.2016.
 */
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @SequenceGenerator(name="recipe_id_seq",
            sequenceName="recipe_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="recipe_id_seq")
    @Column(name = "id", updatable=false)
    private int id;

    @Column(name="dish_id")
    private int dishId;

    @ManyToOne
    @JoinColumn (name = "ingredient")
    @Fetch(FetchMode.JOIN)
    private Ingredient ingredient;

    @Column(name="qty")
    private double qty;

    public Recipe() {
    }

    public Recipe(int dishId, Ingredient ingredient, double qty) {
        this.dishId = dishId;
        this.ingredient = ingredient;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
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
