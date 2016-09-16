package ua.goit.andre.ee9.model;

import javax.persistence.*;

/**
 * Created by Andre on 28.05.2016.
 */
@Entity
@Table(name = "category_dish")
public class CategoryDish {

    @Id
    @Column(name = "category_name")
    private String categoryName;

    public CategoryDish() {
    }

    public CategoryDish(String categoryName) {
        this.categoryName=categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDish)) return false;

        CategoryDish that = (CategoryDish) o;

        return getCategoryName() != null ? getCategoryName().equals(that.getCategoryName()) : that.getCategoryName() == null;

    }

    @Override
    public int hashCode() {
        return getCategoryName() != null ? getCategoryName().hashCode() : 0;
    }
}
