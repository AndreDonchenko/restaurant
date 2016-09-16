package ua.goit.andre.ee9.model;

import javax.persistence.*;

/**
 * Created by Andre on 28.05.2016.
 */
@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @Column(name="ingredient_name")
    private String ingredientName;

    public Ingredient() {
    }

    public Ingredient(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public String toString() {
        return ingredientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;

        Ingredient that = (Ingredient) o;

        return getIngredientName() != null ? getIngredientName().equals(that.getIngredientName()) : that.getIngredientName() == null;

    }

    @Override
    public int hashCode() {
        return getIngredientName() != null ? getIngredientName().hashCode() : 0;
    }
}
