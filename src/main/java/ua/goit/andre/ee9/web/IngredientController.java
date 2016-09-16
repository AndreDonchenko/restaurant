package ua.goit.andre.ee9.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.andre.ee9.model.Ingredient;
import ua.goit.andre.ee9.service.IngredientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Andre on 07.08.2016.
 */
@Controller
public class IngredientController {

    private IngredientService ingredientService;

    @RequestMapping(value = "admin/dish/ingredient", method = RequestMethod.GET)
    public ModelAndView positions() {
        return new ModelAndView("admin/dish/ingredient", "ingredients", ingredientService.getAll());
    }

    @RequestMapping(value = "admin/dish/ingredient", method = RequestMethod.POST)
    public void addIngredient(String ingredientName) {
        ingredientService.add(new Ingredient(ingredientName));
    }

    @RequestMapping(value = "admin/dish/ingredient/delete/{ingredientName}", method = RequestMethod.GET)
    public void positionsDelete(@PathVariable(value = "ingredientName") String ingredientName,
                                HttpServletResponse response ) {
        try {
            Ingredient ingredient = ingredientService.getByName(ingredientName);
            ingredientService.del(ingredient);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
}
