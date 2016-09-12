package ua.goit.andre.ee9.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.andre.ee9.dao.HMenuDao;
import ua.goit.andre.ee9.model.Dish;
import ua.goit.andre.ee9.model.Employee;
import ua.goit.andre.ee9.model.Menu;
import ua.goit.andre.ee9.model.Stock;
import ua.goit.andre.ee9.service.DishService;
import ua.goit.andre.ee9.service.EmployeeService;
import ua.goit.andre.ee9.service.IngredientService;
import ua.goit.andre.ee9.service.MenuService;

import java.util.*;

/**
 * Created by Andre on 30.07.2016.
 */
@Controller
public class MainController {
    MenuService menuService;
    DishService dishService;
    EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    IngredientService ingredientService;

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public ModelAndView index(String filter) {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Menu> menuList = menuService.getAllMenus();
        if (null != filter) {
            for (Iterator<Menu> menuIterator = menuList.iterator(); menuIterator.hasNext();) {
                Menu menu = menuIterator.next();
                for (Iterator<Dish> dishIterator = menu.getDishes().iterator(); dishIterator.hasNext();) {
                    if (!dishIterator.next().getDishName().contains(filter)) {
                        dishIterator.remove();
                    }
                }
                if (menu.getDishes().size()==0) {
                    menuIterator.remove();
                }
            }
        }
        modelAndView.addObject("menuList",menuList);
        modelAndView.addObject("dishList", dishService.getAllDishes());
        return modelAndView;
    }

    @RequestMapping(value ="admin", method = RequestMethod.GET)
    public String adminIndex(Map<String, Object> model) {
        model.put("currentTime", new Date().toString());
        return "admin/index";
    }

    @RequestMapping(value ="dish/{dishId}", method = RequestMethod.GET)
    public ModelAndView viewDish(@PathVariable(value = "dishId") Integer dishId) {
        ModelAndView modelAndView = new ModelAndView("viewDish");
        modelAndView.addObject(dishService.getDishById(dishId));
        modelAndView.addObject("categoryList", dishService.getAllCategories());
        modelAndView.addObject("recipeList", dishService.getRecipeByDish(dishService.getDishById(dishId)));
        modelAndView.addObject("ingredientList", ingredientService.getAll());
        return modelAndView;
    }

    @RequestMapping(value ="personel", method = RequestMethod.GET)
    public ModelAndView personel() {
        ModelAndView modelAndView = new ModelAndView("personel");
        List<Employee> employeeList = new ArrayList<>();
        for (Employee e: employeeService.getAllEmployees()) {
            if (e.getPosition().getPositionName().equals("Waiter")) {
                employeeList.add(e);
            }
        }
        if (employeeList.size() == 0) {
            employeeList = employeeService.getAllEmployees();
        }
        modelAndView.addObject("employees",employeeList);
        return modelAndView;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    @RequestMapping(value ="map", method = RequestMethod.GET)
    public String showMap() {
        return "map";
    }

    @RequestMapping(value ="contacts", method = RequestMethod.GET)
    public String showcontacts() {
        return "contacts";
    }

}
