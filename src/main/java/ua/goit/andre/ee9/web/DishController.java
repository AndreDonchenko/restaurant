package ua.goit.andre.ee9.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.andre.ee9.dao.EmptyRecipeException;
import ua.goit.andre.ee9.dao.NotEnoughStockException;
import ua.goit.andre.ee9.model.*;
import ua.goit.andre.ee9.service.DishService;
import ua.goit.andre.ee9.service.IngredientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import static ua.goit.andre.ee9.Util.getFilenames;

/**
 * Created by Andre on 07.08.2016.
 */
@Controller
public class DishController {

    DishService dishService;
    IngredientService ingredientService;

    @RequestMapping(value = "admin/dish/edit/addIngredient", method = RequestMethod.POST)
    public void addIngredientToDish(Integer dishId, String ingredientName, double qty,
                                      HttpServletResponse response) {
        boolean badRequest=false;
        List<Recipe> recipeList = dishService.getRecipeByDish(dishService.getDishById(dishId));
        for (Recipe r: recipeList) {
            if (ingredientName.equals(r.getIngredient().getIngredientName())) {
                badRequest = true;
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        if (!badRequest) {
            dishService.saveRecipe(new Recipe(dishId, ingredientService.getByName(ingredientName), qty));
        }
    }

    @RequestMapping(value = "admin/dish/edit/deleteIngedient/{recipeId}", method = RequestMethod.POST)
    public void delRecipe(@PathVariable(value = "recipeId") Integer recipeId) {
        dishService.delRecipe(dishService.getRecipeById(recipeId));
    }

    @RequestMapping(value = "admin/dish", method = RequestMethod.GET)
    public ModelAndView dishes(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("/admin/dish/dishes");
        modelAndView.addObject("dishes", dishService.getAllDishes());
        modelAndView.addObject("categoryList", dishService.getAllCategories());
        modelAndView.addObject("dish", new Dish());
        String photoPath = request.getSession().getServletContext().getRealPath("/resources/dish/");
        modelAndView.addObject("photoFiles", getFilenames(photoPath));
        return modelAndView;
    }

    @RequestMapping(value = "admin/dish", method = RequestMethod.POST)
    public String dishUpdate(Dish dish) {
        dishService.saveDish(dish);
        return "redirect:/admin/dish";
    }

    @RequestMapping(value = "admin/dish/{dishId}", method = RequestMethod.GET)
    public ModelAndView editDish(@PathVariable(value = "dishId") Integer dishId,
                                 HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("admin/dish/editDish");
        modelAndView.addObject("dish",dishService.getDishById(dishId));
        modelAndView.addObject("categoryList", dishService.getAllCategories());
        modelAndView.addObject("recipeList", dishService.getRecipeByDish(dishService.getDishById(dishId)));
        modelAndView.addObject("ingredientList", ingredientService.getAll());
        String photoPath = request.getSession().getServletContext().getRealPath("/resources/dish/");
        modelAndView.addObject("photoFiles", getFilenames(photoPath));
        return modelAndView;
    }

    @RequestMapping(value = "admin/dish/add", method = RequestMethod.GET)
    public ModelAndView addDish() {
        ModelAndView modelAndView = new ModelAndView("admin/dish/editDish", "dish", new Dish());
        modelAndView.addObject("categoryList", dishService.getAllCategories());
        return modelAndView;
    }

    @RequestMapping(value = "admin/dish/delete/{dishId}", method = RequestMethod.GET)
    public void delDish(@PathVariable(value = "dishId") Integer dishId,
                        HttpServletResponse response) {
        try {
            dishService.delDish(dishService.getDishById(dishId));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "admin/dish/prepareDish", method = RequestMethod.POST)
    public void prepareDish(Integer dishId, Integer employeeId, Integer orderId, Date dateOrder,
                              HttpServletResponse response) {
        try {
            //Date date = Date.valueOf(dateOrder);
            dishService.PrepareDish(dishId, employeeId, orderId, dateOrder);
        } catch (NotEnoughStockException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (EmptyRecipeException e) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


}
