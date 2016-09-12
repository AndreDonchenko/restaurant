package ua.goit.andre.ee9.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.andre.ee9.model.Dish;
import ua.goit.andre.ee9.model.Menu;
import ua.goit.andre.ee9.service.DishService;
import ua.goit.andre.ee9.service.MenuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andre on 07.09.2016.
 */
@Controller
public class MenuController {

    MenuService menuService;
    DishService dishService;

    @RequestMapping(value = "admin/menu", method = RequestMethod.GET)
    public String menus(Map<String, Object> model) {
        model.put("menu", menuService.getAllMenus());
        return "admin/menu/menu";
    }

    @RequestMapping(value = "admin/menu", method = RequestMethod.POST)
    public String menus(Menu menu) {
        menuService.addMenu(menu);
        return "redirect:menu";
    }

    @RequestMapping(value = "admin/menu/update", method = RequestMethod.POST)
    public String menuUpdate(Integer menuId, String menuName) {
        Menu menu = menuService.getMenuById(menuId);
        menu.setMenuName(menuName);
        menuService.addMenu(menu);
        return "redirect:menu";
    }

    @RequestMapping(value = "admin/menu/addDish", method = RequestMethod.POST)
    public void addDishToMenu(Integer menuId, Integer dishId) {
        Menu menu = menuService.getMenuById(menuId);
        List<Dish> dishes = menu.getDishes();
        dishes.add(dishService.getDishById(dishId));
        menu.setDishes(dishes);
        menuService.saveMenu(menu);
    }

    @RequestMapping(value = "admin/menu/delDish", method = RequestMethod.POST)
    public void delDishFromMenu(Integer menuId, Integer dishId) {
        Menu menu = menuService.getMenuById(menuId);
        List<Dish> dishes = menu.getDishes();
        dishes.remove(dishService.getDishById(dishId));
        menu.setDishes(dishes);
        menuService.saveMenu(menu);
    }

    @RequestMapping(value = "admin/menu/delete/{menuId}", method = RequestMethod.GET)
    public String delMenu(@PathVariable(value = "menuId") Integer menuId) {
        menuService.delMenu(menuService.getMenuById(menuId));
        return "redirect:/admin/menu";
    }

    @RequestMapping(value = "admin/menu/edit/{menuId}", method = RequestMethod.GET)
    public ModelAndView editMenu(@PathVariable(value = "menuId") Integer menuId) {
        Menu menu = menuService.getMenuById(menuId);
        ModelAndView modelAndView = new ModelAndView("/admin/menu/editMenu");
        modelAndView.addObject("menu", menu);
        Map<Integer, String> dishList = new HashMap<>();
        for (Dish d: dishService.getAllDishes()) {
            dishList.put(d.getId(), d.getDishName());
        }
        modelAndView.addObject("dishList", dishList);
        return modelAndView;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }
}
