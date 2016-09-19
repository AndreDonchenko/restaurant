package ua.goit.andre.ee9.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.andre.ee9.model.Ingredient;
import ua.goit.andre.ee9.model.Stock;
import ua.goit.andre.ee9.service.IngredientService;
import ua.goit.andre.ee9.service.StockService;

import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Andre on 07.08.2016.
 */
@Controller
public class StockController {

    private StockService stockService;
    private IngredientService ingredientService;

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    @RequestMapping(value = "admin/stock", method = RequestMethod.GET)
    public ModelAndView getStock(String filter) {
        ModelAndView modelAndView = new ModelAndView("/admin/stock/stock");
        List<Stock> stockList= stockService.getAll();
        if (null != filter) {
            for (Iterator<Stock> stock = stockList.iterator(); stock.hasNext();) {
                if (!stock.next().getIngredient().getIngredientName().contains(filter)) {
                    stock.remove();
                };
            }
        }
        modelAndView.addObject("stockList", stockList);
        List<Ingredient> list = stockService.getEmptyIngredients();
        modelAndView.addObject("emptyIngredients", list);
        System.out.println(filter);
        return modelAndView;
    }

    @RequestMapping(value = "admin/stock/addIngredient", method = RequestMethod.POST)
    public void setIngredientQty(String ingredientName, Double qty,
                              HttpServletResponse response ) {
        Ingredient ingredient = ingredientService.getByName(ingredientName);
        if (qty >= 0 && null != ingredient) {
            stockService.addIngredient(ingredient, qty);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "admin/stock/delIngredient/{ingredientName}", method = RequestMethod.GET)
    public void delIngredient(@PathVariable(value = "ingredientName") String ingredientName,
                                HttpServletResponse response) {
        List<Stock> stockList = stockService.getByName(ingredientName);
        for(Stock stock : stockList) {
            if (stock.getQty() == 0) {
                stockService.del(stock);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
