package ua.goit.andre.ee9.service;

import org.springframework.transaction.annotation.Transactional;
import ua.goit.andre.ee9.dao.*;
import ua.goit.andre.ee9.model.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Andre on 07.08.2016.
 */
public class DishService {

    private HCategoryDishDao categoryDishDao;
    private HDishDao dishDao;
    private HRecipeDao recipeDao;
    private HPreparedDishDao preparedDishDao;
    private HEmployeeDao employeeDao;
    private HOrderDao orderDao;

    public List<Dish> getAllDishes() {
        return dishDao.getAll();
    }

    public List<CategoryDish> getAllCategories() {
        return categoryDishDao.getAll();
    }

    public void addCategoryDish(CategoryDish categoryDish) {categoryDishDao.add(categoryDish);}

    public void delCategoryDish(CategoryDish categoryDish) {
        categoryDishDao.del(categoryDish);
    }

    public void saveDish(Dish dish) {
        dishDao.add(dish);
    }

    public void delDish(Dish dish) {
        dishDao.del(dish);
    }


    public List <Dish> getDishesByCategoryName(CategoryDish categoryDish) {
        return dishDao.getAllByCategory(categoryDish);
    }

    public Recipe getRecipeById(Integer id) {
        return recipeDao.getById(id);
    }

    public void PrepareDish (Integer dishId, Integer employeeId, Integer orderId, Date dateOrder) throws NotEnoughStockException, EmptyRecipeException {
        Dish dish = dishDao.getById(dishId);
        Employee employee = employeeDao.getById(employeeId);
        OrderNum orderNum = orderDao.getById(orderId);
        preparedDishDao.PrepareDish(dish, employee, orderNum, dateOrder);
    }

    public List<PreparedDish> getPreparedDishByOrder(Integer orderId) {
        return preparedDishDao.getByOrder(orderDao.getById(orderId));
    }

    public List<Recipe> getRecipeByDish(Dish dish) {
        return recipeDao.getAllByDish(dish);
    }

    public void delRecipe(Recipe recipe) {
        recipeDao.del(recipe);
    }

    public void saveRecipe (Recipe recipe) {
        recipeDao.add(recipe);
    }

    public Dish getDishById(Integer dishId) {
        return dishDao.getById(dishId);
    }

    public void setCategoryDishDao(HCategoryDishDao categoryDishDao) {
        this.categoryDishDao = categoryDishDao;
    }

    public void setDishDao(HDishDao dishDao) {
        this.dishDao = dishDao;
    }

    public void setRecipeDao(HRecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public void setPreparedDishDao(HPreparedDishDao preparedDishDao) {
        this.preparedDishDao = preparedDishDao;
    }

    public void setEmployeeDao(HEmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void setOrderDao(HOrderDao orderDao) {
        this.orderDao = orderDao;
    }
}
