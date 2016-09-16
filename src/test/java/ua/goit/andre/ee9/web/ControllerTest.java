package ua.goit.andre.ee9.web;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.andre.ee9.dao.HPreparedDishDao;
import ua.goit.andre.ee9.model.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.*;

/**
 * Created by Andre on 12.09.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/web-context.xml",
                                    "file:src/main/webapp/WEB-INF/application-context.xml",
                                    "file:src/main/webapp/WEB-INF/hibernate-context.xml"})

public class ControllerTest {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected ApplicationContext ac;

    CategoryController categoryController;
    PositionController positionController;
    IngredientController ingredientController;
    EmployeeController employeeController;
    MenuController menuController;
    DishController dishController;
    StockController stockController;
    OrderController orderController;
    HPreparedDishDao preparedDishDao;

    static ComboPooledDataSource dataSource;
    public static final String TEST_STRING_PATTERN="TeSt_";
    public static final String TEST_DATE = "2016-01-01";

    @Before
    public void before() {
        categoryController = (CategoryController) ac.getBean("categoryController");
        positionController = (PositionController) ac.getBean("positionController");
        ingredientController = (IngredientController) ac.getBean("ingredientController");
        employeeController = (EmployeeController) ac.getBean("employeeController");
        menuController = (MenuController) ac.getBean("menuController");
        dishController = (DishController) ac.getBean("dishController");
        stockController = (StockController) ac.getBean("stockController");
        orderController = (OrderController) ac.getBean("orderController");
        preparedDishDao = (HPreparedDishDao) ac.getBean("preparedDishDao");


        dataSource = (ComboPooledDataSource) ac.getBean("dataSource");

        String jdbcUrl = dataSource.getJdbcUrl();
        if (!jdbcUrl.contains("_test")) {
            dataSource.setJdbcUrl(jdbcUrl + "_test");
        }
    }

    public String getTestString() {
        Integer random = new Random().nextInt();
        return TEST_STRING_PATTERN + random.toString();
    }

    @Test
    public void testCategory() throws Exception {
        String testCategoryDish = getTestString();
        HttpServletResponse response = new MockHttpServletResponse();

        Map<String, Object> model = new HashMap();
        String viewName = categoryController.positions(model);
        Assert.assertTrue("/admin/dish/categories".equals(viewName));
        Assert.assertTrue(model.get("dishCategories").getClass().equals(ArrayList.class));
        List<CategoryDish> list = (ArrayList) model.get("dishCategories");
        CategoryDish categoryDish = new CategoryDish(testCategoryDish);
        Assert.assertTrue(!list.contains(categoryDish));
        categoryController.positions(categoryDish); //Add new test category
        viewName = categoryController.positions(model);
        list = (ArrayList) model.get("dishCategories");
        Assert.assertTrue(list.contains(categoryDish)); //check for category added
        categoryController.positionsDelete(testCategoryDish, response); //remove test category
        viewName = categoryController.positions(model);
        list = (ArrayList) model.get("dishCategories");
        Assert.assertTrue(!list.contains(categoryDish));
    }

    @Test
    public void testIngredient() throws Exception {
        String testIngredientName = getTestString();
        ModelAndView modelAndView = ingredientController.positions();
        String viewName = modelAndView.getViewName();
        Assert.assertTrue("admin/dish/ingredient".equals(viewName));
        Assert.assertTrue(modelAndView.getModel().get("ingredients").getClass().equals(ArrayList.class));
        List<Ingredient> list = (ArrayList) modelAndView.getModel().get("ingredients");
        Ingredient ingredient = new Ingredient(testIngredientName);
        Assert.assertTrue(!list.contains(testIngredientName));

        ingredientController.addIngredient(testIngredientName);
        modelAndView = ingredientController.positions();
        list = (ArrayList) modelAndView.getModel().get("ingredients");
        Assert.assertTrue(list.contains(ingredient));
        HttpServletResponse response = new MockHttpServletResponse();
        ingredientController.positionsDelete(testIngredientName, response);
        modelAndView = ingredientController.positions();
        list = (ArrayList) modelAndView.getModel().get("ingredients");
        Assert.assertTrue(!list.contains(ingredient));
    }

    @Test
    public void testPosition() throws Exception {
        String testPositionName = getTestString();
        Map<String, Object> model = new HashMap();
        String viewName = positionController.positions(model);
        Assert.assertTrue("admin/Employee/positions".equals(viewName));
        Assert.assertTrue(model.get("positions").getClass().equals(ArrayList.class));
        List<Position> list = (ArrayList) model.get("positions");
        Position position = new Position(testPositionName);
        Assert.assertTrue(!list.contains(position));
        positionController.positions(position);
        viewName = positionController.positions(model);
        list = (ArrayList) model.get("positions");
        Assert.assertTrue(list.contains(position));
        HttpServletResponse response = new MockHttpServletResponse();
        positionController.positionsDelete(testPositionName, response);
        viewName = positionController.positions(model);
        list = (ArrayList) model.get("positions");
        Assert.assertTrue(!list.contains(position));
    }

    @Test
    public void testFullScenario() throws Exception {
        //Create new test Position of employee
        String testPositionName = getTestString();
        Position position = new Position(testPositionName);
        positionController.positions(position);
        //Check for creating test employee Position
        Map<String, Object> model = new HashMap();
        String viewName = positionController.positions(model);
        List<Position>positionList = (ArrayList) model.get("positions");
        Assert.assertTrue(positionList.contains(position));

        //Create new Test Employee
        String testEmployeeName = getTestString();
        Employee employee = new Employee();
        employee.setName(testEmployeeName);
        employee.setSurname(testEmployeeName);
        employee.setBirthDay(Date.valueOf(TEST_DATE));
        employee.setPhone("123");
        employee.setSalary(123);
        employee.setPhotoFn("123");
        employee.setPosition(position);
        employeeController.employeeUpdate(employee);
        ModelAndView modelAndView = employeeController.employees();
        List<Employee> employeeList = (ArrayList) modelAndView.getModel().get("employees");
        Assert.assertTrue(employeeList.contains(employee));

        //Create new Test Category_Dish
        String testCategoryDish = getTestString();
        CategoryDish categoryDish = new CategoryDish(testCategoryDish);
        categoryController.positions(categoryDish); //Add new test category
        viewName = categoryController.positions(model);
        List<CategoryDish> categoryDishList = (ArrayList) model.get("dishCategories");
        Assert.assertTrue(categoryDishList.contains(categoryDish)); //check for category added

        //Create new Test Dish
        HttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        String testDishName = getTestString();
        Dish dish = new Dish();
        dish.setDishName(testDishName);
        dish.setPrice(123.0);
        dish.setWeight(123.0);
        dish.setCategoryDish(categoryDish);
        dishController.dishUpdate(dish);
        modelAndView = dishController.dishes(request, response);
        List<Dish> dishList = (ArrayList) modelAndView.getModel().get("dishes");
        Assert.assertTrue(dishList.contains(dish));

        //Create new Test ingredient
        String testIngredientName = getTestString();
        Ingredient ingredient = new Ingredient(testIngredientName);
        ingredientController.addIngredient(testIngredientName);
        modelAndView = ingredientController.positions();
        List<Ingredient> ingredientList = (ArrayList) modelAndView.getModel().get("ingredients");
        Assert.assertTrue(ingredientList.contains(ingredient));

        //Add Ingredient to Dish
        dishController.addIngredientToDish(dish.getId(), testIngredientName, 10, response);
        modelAndView = dishController.editDish(dish.getId(), request, response);
        List<Recipe> recipeList = (ArrayList) modelAndView.getModel().get("recipeList");
        Assert.assertTrue(recipeList.get(0).getIngredient().equals(ingredient));

        //Add Ingredient to Stock
        stockController.setIngredientQty(ingredient.getIngredientName(), 100.0 ,response);
        modelAndView = stockController.positions(ingredient.getIngredientName());
        List<Stock> stockList = (ArrayList) modelAndView.getModel().get("stockList");
        Assert.assertTrue(stockList.get(0).getIngredient().equals(ingredient));

        //Add Order with dish
        orderController.ordersPost(employee.getId(), Date.valueOf(TEST_DATE), 1, true);
        modelAndView = orderController.orderMenu(employee.getName(), TEST_DATE, 1);
        List<OrderNum> ordersList = (ArrayList) modelAndView.getModel().get("ordersList");
        OrderNum order = ordersList.get(0);
        orderController.ordersPost(order.getId(), dish.getId(), 1);
        modelAndView = orderController.orderMenu(employee.getName(), TEST_DATE, 1);
        ordersList = (ArrayList) modelAndView.getModel().get("ordersList");
        order = ordersList.get(0);
        Assert.assertTrue(order.getDateOrder().toString().equals(TEST_DATE));
        Assert.assertTrue(order.getEmployee().getId() == employee.getId());
        Assert.assertTrue(order.getTableNum() == 1);
        Assert.assertTrue(order.isOpen());
        Assert.assertTrue(order.getOrderDetails().get(0).getDish().equals(dish));

        //Prepare Dish
        dishController.prepareDish(dish.getId(), employee.getId(), order.getId(), Date.valueOf(TEST_DATE), response);
        modelAndView = orderController.ordersEditDetail(order.getId());
        List<PreparedDish> preparedDishs = (ArrayList) modelAndView.getModel().get("preparedDishList");
        PreparedDish preparedDish = null;
        for (PreparedDish p: preparedDishs) {
            if (p.getDish().equals(dish) &&
                    p.getEmployee().equals(employee) &&
                    p.getOrder().equals(order) &&
                    p.getPrepareDate().toString().equals(TEST_DATE)) {
                preparedDish = p;
                break;
            }
        }
        Assert.assertTrue(preparedDish.getDish().equals(dish));
        Assert.assertTrue(preparedDish.getEmployee().equals(employee));
        Assert.assertTrue(preparedDish.getOrder().equals(order));
        Assert.assertTrue(preparedDish.getDish().equals(dish));
        Assert.assertTrue(preparedDish.getPrepareDate().toString().equals(TEST_DATE));

        //Check Stock
        modelAndView = stockController.positions(ingredient.getIngredientName());
        stockList = (ArrayList) modelAndView.getModel().get("stockList");
        Assert.assertTrue(stockList.get(0).getQty() == 90);

        //Delete preparedDish
        preparedDishDao.del(preparedDish);

        //Delete Dish from order
        orderController.ordersDeleteDetail(order.getOrderDetails().get(0).getId());
        modelAndView = orderController.orderMenu(employee.getName(), TEST_DATE, 1);
        ordersList = (ArrayList) modelAndView.getModel().get("ordersList");
        order = ordersList.get(0);
        Assert.assertTrue(order.getOrderDetails().size()==0);
        int ordersListSize = ordersList.size();

        //Delete Order
        orderController.ordersDel(order.getId(), response);
        modelAndView = orderController.orderMenu(employee.getName(), TEST_DATE, 1);
        ordersList = (ArrayList) modelAndView.getModel().get("ordersList");
        Assert.assertTrue(ordersList.size() == ordersListSize - 1);

        //Delete ingredient from Recipe Dish
        modelAndView = dishController.editDish(dish.getId(), request, response);
        recipeList = (ArrayList) modelAndView.getModel().get("recipeList");
        dishController.delRecipe(recipeList.get(0).getId());
        Assert.assertTrue(recipeList.get(0).getIngredient().equals(ingredient));

        //Set Ingredient=0 and delete Ingredient from Stock
        stockController.setIngredientQty(testIngredientName, 0.0 ,response);
        stockController.delIngredient(testIngredientName,response);
        modelAndView = stockController.positions(ingredient.getIngredientName());
        stockList = (ArrayList) modelAndView.getModel().get("stockList");
        Assert.assertTrue(stockList.size() == 0);

        //Delete Ingredient
        ingredientController.positionsDelete(testIngredientName, response);
        modelAndView = ingredientController.positions();
        ordersList = (ArrayList) modelAndView.getModel().get("ingredients");
        Assert.assertTrue(!ordersList.contains(ingredient));

        //Delete Dish
        dishController.delDish(dish.getId(), response);
        modelAndView = dishController.dishes(request, response);
        dishList = (ArrayList) modelAndView.getModel().get("dishes");
        Assert.assertTrue(!dishList.contains(dish));

        //Delete CategoryDish

        //Delete Employee

        //Delete Position
    }

}

