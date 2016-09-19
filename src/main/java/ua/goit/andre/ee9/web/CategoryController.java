package ua.goit.andre.ee9.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.andre.ee9.dao.EmptyRecipeException;
import ua.goit.andre.ee9.dao.NotEnoughStockException;
import ua.goit.andre.ee9.model.CategoryDish;
import ua.goit.andre.ee9.model.Dish;
import ua.goit.andre.ee9.model.Ingredient;
import ua.goit.andre.ee9.model.Recipe;
import ua.goit.andre.ee9.service.DishService;
import ua.goit.andre.ee9.service.IngredientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Andre on 07.08.2016.
 */
@Controller
public class CategoryController {

    DishService dishService;

    @RequestMapping(value = "admin/dish/categories", method = RequestMethod.GET)
    public String getCategories(Map<String, Object> model) {
        model.put("dishCategories", dishService.getAllCategories());
        return "/admin/dish/categories";
    }

    @RequestMapping(value = "admin/dish/categories", method = RequestMethod.POST)
    public String addCategory(CategoryDish categoryDish) {
        dishService.addCategoryDish(categoryDish);
        return "redirect:/admin/dish/categories";
    }

    @RequestMapping(value = "admin/dish/categories/delete/{categoryName}", method = RequestMethod.GET)
    public void delCategory(@PathVariable(value = "categoryName") String categoryName,
                                HttpServletResponse response) {
        try {
            CategoryDish categoryDish = new CategoryDish(categoryName);
            dishService.delCategoryDish(categoryDish);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }
}
