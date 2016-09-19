package ua.goit.andre.ee9.web;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sun.org.apache.xpath.internal.operations.Mod;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
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
        String viewName = categoryController.getCategories(model);
        Assert.assertTrue("/admin/dish/categories".equals(viewName));
        Assert.assertTrue(model.get("dishCategories").getClass().equals(ArrayList.class));
        List<CategoryDish> list = (ArrayList) model.get("dishCategories");
        CategoryDish categoryDish = new CategoryDish(testCategoryDish);
        Assert.assertTrue(!list.contains(categoryDish));
        categoryController.addCategory(categoryDish); //Add new test category
        viewName = categoryController.getCategories(model);
        list = (ArrayList) model.get("dishCategories");
        Assert.assertTrue(list.contains(categoryDish)); //check for category added
        categoryController.delCategory(testCategoryDish, response); //remove test category
        viewName = categoryController.getCategories(model);
        list = (ArrayList) model.get("dishCategories");
        Assert.assertTrue(!list.contains(categoryDish));
    }

    @Test
    public void testIngredient() throws Exception {
        String testIngredientName = getTestString();
        ModelAndView modelAndView = ingredientController.getIngredients();
        String viewName = modelAndView.getViewName();
        Assert.assertTrue("admin/dish/ingredient".equals(viewName));
        Assert.assertTrue(modelAndView.getModel().get("ingredients").getClass().equals(ArrayList.class));
        List<Ingredient> list = (ArrayList) modelAndView.getModel().get("ingredients");
        Ingredient ingredient = new Ingredient(testIngredientName);
        Assert.assertTrue(!list.contains(testIngredientName));

        ingredientController.addIngredient(testIngredientName);
        modelAndView = ingredientController.getIngredients();
        list = (ArrayList) modelAndView.getModel().get("ingredients");
        Assert.assertTrue(list.contains(ingredient));
        HttpServletResponse response = new MockHttpServletResponse();
        ingredientController.delIngredient(testIngredientName, response);
        modelAndView = ingredientController.getIngredients();
        list = (ArrayList) modelAndView.getModel().get("ingredients");
        Assert.assertTrue(!list.contains(ingredient));
    }

    @Test
    public void testPosition() throws Exception {
        String testPositionName = getTestString();
        Map<String, Object> model = new HashMap();
        String viewName = positionController.getPositions(model);
        Assert.assertTrue("admin/Employee/positions".equals(viewName));
        Assert.assertTrue(model.get("positions").getClass().equals(ArrayList.class));
        List<Position> list = (ArrayList) model.get("positions");
        Position position = new Position(testPositionName);
        Assert.assertTrue(!list.contains(position));
        positionController.addPosition(position);
        viewName = positionController.getPositions(model);
        list = (ArrayList) model.get("positions");
        Assert.assertTrue(list.contains(position));
        HttpServletResponse response = new MockHttpServletResponse();
        positionController.delPosition(testPositionName, response);
        viewName = positionController.getPositions(model);
        list = (ArrayList) model.get("positions");
        Assert.assertTrue(!list.contains(position));
    }

    //Create new test Position of employee
    private Position createTestPosition() {
        String testPositionName = getTestString();
        Position position = new Position(testPositionName);
        positionController.addPosition(position);
        Map<String, Object> model = new HashMap();
        positionController.getPositions(model);
        List<Position>positionList = (ArrayList) model.get("positions");
        Assert.assertTrue(positionList.contains(position));
        return position;
    }

    //Create new Test Employee
    private Employee createTestEmployee(Position position) {
        String testEmployeeName = getTestString();
        Employee employee = new Employee();
        employee.setName(testEmployeeName);
        employee.setSurname(testEmployeeName);
        employee.setBirthDay(Date.valueOf(TEST_DATE));
        employee.setPhone("123");
        employee.setSalary(123);
        employee.setPhotoFn("123");
        employee.setPosition(position);
        employeeController.updateEmployee(employee);
        ModelAndView modelAndView = employeeController.getEmployees();
        List<Employee> employeeList = (ArrayList) modelAndView.getModel().get("employees");
        Assert.assertTrue(employeeList.contains(employee));
        return employee;
    }

    //Create new Test Category_Dish
    private CategoryDish createTestCategoryDish() {
        String testCategoryDish = getTestString();
        CategoryDish categoryDish = new CategoryDish(testCategoryDish);
        categoryController.addCategory(categoryDish);
        Map<String, Object> model = new HashMap();
        categoryController.getCategories(model);
        List<CategoryDish> categoryDishList = (ArrayList) model.get("dishCategories");
        Assert.assertTrue(categoryDishList.contains(categoryDish));
        return categoryDish;
    }

    //Create new Test Dish
    private Dish createTestDish (CategoryDish categoryDish) {
        HttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        String testDishName = getTestString();
        Dish dish = new Dish();
        dish.setDishName(testDishName);
        dish.setPrice(123.0);
        dish.setWeight(123.0);
        dish.setCategoryDish(categoryDish);
        dishController.updateDish(dish);
        ModelAndView modelAndView = dishController.getDishes(request, response);
        List<Dish> dishList = (ArrayList) modelAndView.getModel().get("dishes");
        Assert.assertTrue(dishList.contains(dish));
        return dish;
    }

    //Create new Test ingredient
    private Ingredient createTestIngredient() {
        String testIngredientName = getTestString();
        Ingredient ingredient = new Ingredient(testIngredientName);
        ingredientController.addIngredient(testIngredientName);
        ModelAndView modelAndView = ingredientController.getIngredients();
        List<Ingredient> ingredientList = (ArrayList) modelAndView.getModel().get("ingredients");
        Assert.assertTrue(ingredientList.contains(ingredient));
        return ingredient;
    }

    //Add Ingredient to Dish
    private Recipe addIngredientToDish(Dish dish, Ingredient ingredient, double qty) {
        HttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        dishController.addIngredientToDish(dish.getId(), ingredient.getIngredientName(), qty, response);
        ModelAndView modelAndView = dishController.editDish(dish.getId(), request, response);
        List<Recipe> recipeList = (ArrayList) modelAndView.getModel().get("recipeList");
        Recipe recipe = recipeList.get(0);
        Assert.assertTrue(recipe.getIngredient().equals(ingredient));
        return recipe;
    }

    //Add Ingredient to Stock
    private void addIngredientToStock (Ingredient ingredient, double qty) {
        HttpServletResponse response = new MockHttpServletResponse();
        stockController.setIngredientQty(ingredient.getIngredientName(), qty ,response);
        ModelAndView modelAndView = stockController.getStock(ingredient.getIngredientName());
        List<Stock> stockList = (ArrayList) modelAndView.getModel().get("stockList");
        Assert.assertTrue(stockList.get(0).getIngredient().equals(ingredient));
    }

    //Create Order with dish
    private OrderNum createOrderWithDish(Employee employee, Dish dish) {
        orderController.ordersPost(employee.getId(), Date.valueOf(TEST_DATE), 1, true);
        ModelAndView modelAndView = orderController.getOrders(employee.getName(), TEST_DATE, 1);
        List<OrderNum> ordersList = (ArrayList) modelAndView.getModel().get("ordersList");
        OrderNum order = ordersList.get(0);
        orderController.ordersPost(order.getId(), dish.getId(), 1);
        modelAndView = orderController.getOrders(employee.getName(), TEST_DATE, 1);
        ordersList = (ArrayList) modelAndView.getModel().get("ordersList");
        order = ordersList.get(0);
        Assert.assertTrue(order.getDateOrder().toString().equals(TEST_DATE));
        Assert.assertTrue(order.getEmployee().getId() == employee.getId());
        Assert.assertTrue(order.getTableNum() == 1);
        Assert.assertTrue(order.isOpen());
        Assert.assertTrue(order.getOrderDetails().get(0).getDish().equals(dish));
        return order;
    }


    //Prepare Dish
    private TestResult prepareDish(Dish dish, Employee employee, OrderNum order) {
        MockHttpServletResponse response = new MockHttpServletResponse();
        dishController.prepareDish(dish.getId(), employee.getId(), order.getId(), Date.valueOf(TEST_DATE), response);
        ModelAndView modelAndView = orderController.ordersEditDetail(order.getId());
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
        return new TestResult((preparedDish != null), response, preparedDish);
    }

    private double getStockBalance(Ingredient ingredient) {
        ModelAndView modelAndView = stockController.getStock(ingredient.getIngredientName());
        List<Stock> stockList = (ArrayList) modelAndView.getModel().get("stockList");
        return stockList.get(0).getQty();
    }

    //Delete Dish from order
    private TestResult delDishFromOrder(OrderNum order, Employee employee) {
        orderController.ordersDeleteDetail(order.getOrderDetails().get(0).getId());
        ModelAndView modelAndView = orderController.getOrders(employee.getName(), TEST_DATE, 1);
        List<OrderNum> ordersList = (ArrayList) modelAndView.getModel().get("ordersList");
        order = ordersList.get(0);
        return new TestResult((order.getOrderDetails().size()==0), null, null);
    }

    //Delete Order
    private TestResult delOrder(OrderNum order, Employee employee) {
        ModelAndView modelAndView = orderController.getOrders(employee.getName(), TEST_DATE, 1);
        List<OrderNum> ordersList = (ArrayList) modelAndView.getModel().get("ordersList");
        int ordersListSize = ordersList.size();
        MockHttpServletResponse response = new MockHttpServletResponse();
        orderController.delOrder(order.getId(), response);
        modelAndView = orderController.getOrders(employee.getName(), TEST_DATE, 1);
        ordersList = (ArrayList) modelAndView.getModel().get("ordersList");
        return new TestResult((ordersList.size() == ordersListSize - 1), response, null);
    }

    //Delete ingredient from Recipe Dish
    private TestResult delIngredientFromDish(Dish dish) {
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ModelAndView modelAndView = dishController.editDish(dish.getId(), request, response);
        List<Recipe> recipeList = (ArrayList) modelAndView.getModel().get("recipeList");
        dishController.delRecipe(recipeList.get(0).getId());
        modelAndView = dishController.editDish(dish.getId(), request, response);
        recipeList = (ArrayList) modelAndView.getModel().get("recipeList");
        return new TestResult((recipeList.size() == 0), response, null);
    }

    //Set Ingredient=0 and delete Ingredient from Stock
    private TestResult delIngredientFromStock(Ingredient ingredient) {
        MockHttpServletResponse response = new MockHttpServletResponse();
        stockController.setIngredientQty(ingredient.getIngredientName(), 0.0 ,response);
        stockController.delIngredient(ingredient.getIngredientName(), response);
        ModelAndView modelAndView = stockController.getStock(ingredient.getIngredientName());
        List<Stock> stockList = (ArrayList) modelAndView.getModel().get("stockList");
        return new TestResult((stockList.size() == 0), response, null);
    }

    //Delete Ingredient
    private TestResult delIngredient(Ingredient ingredient) {
        MockHttpServletResponse response = new MockHttpServletResponse();
        ingredientController.delIngredient(ingredient.getIngredientName(), response);
        ModelAndView modelAndView = ingredientController.getIngredients();
        List<Ingredient> ingredientList = (ArrayList) modelAndView.getModel().get("ingredients");
        return new TestResult((!ingredientList.contains(ingredient)), response, null);
    }

    //Delete Dish
    private TestResult delDish(Dish dish) {
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        dishController.delDish(dish.getId(), response);
        ModelAndView modelAndView = dishController.getDishes(request, response);
        List <Dish> dishList = (ArrayList) modelAndView.getModel().get("dishes");
        return new TestResult((!dishList.contains(dish)), response, null);
    }

    //Delete CategoryDish
    private TestResult delCategoryDish(CategoryDish categoryDish) {
        MockHttpServletResponse response = new MockHttpServletResponse();
        categoryController.delCategory(categoryDish.getCategoryName(), response); //remove test category
        Map<String, Object> model = new HashMap();
        categoryController.getCategories(model);
        List <CategoryDish> categoryDishList = (ArrayList) model.get("dishCategories");
        return new TestResult((!categoryDishList.contains(categoryDish)), response, null);
    }

    //Delete Employee
    private TestResult delEmployee(Employee employee) {
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        employeeController.delEmployee(employee.getId(), request, response);
        ModelAndView modelAndView = employeeController.getEmployees();
        List <Employee> employeeList = (ArrayList) modelAndView.getModel().get("employees");
        return new TestResult((!employeeList.contains(employee)), response, null);
    }

    //Delete Position
    private TestResult delPosition (Position position) {
        MockHttpServletResponse response = new MockHttpServletResponse();
        positionController.delPosition(position.getPositionName(), response);
        Map<String, Object> model = new HashMap();
        positionController.getPositions(model);
        List <Position> positionList = (ArrayList) model.get("positions");
        return  new TestResult((!positionList.contains(position)), response, null);
    }

    @Test
    public void testFullScenario() throws Exception {
        Position position = createTestPosition();
        Employee employee = createTestEmployee(position);
        CategoryDish categoryDish = createTestCategoryDish();
        Dish dish = createTestDish(categoryDish);
        Ingredient ingredient = createTestIngredient();
        Recipe recipe = addIngredientToDish(dish, ingredient, 10.0);
        addIngredientToStock (ingredient, 100.0);
        OrderNum order = createOrderWithDish(employee, dish);

        TestResult result = prepareDish(dish, employee, order);
        Assert.assertTrue(result.isOk());
        PreparedDish preparedDish = (PreparedDish) result.getResult();
        //Check Stock
        Assert.assertTrue(getStockBalance(ingredient) == 90);
        //Delete preparedDish
        preparedDishDao.del(preparedDish);

        result = delDishFromOrder(order, employee);
        Assert.assertTrue(result.isOk());

        result = delOrder(order, employee);
        Assert.assertTrue(result.isOk());

        result = delIngredientFromDish(dish);
        Assert.assertTrue(result.isOk());

        result = delIngredientFromStock(ingredient);
        Assert.assertTrue(result.isOk());

        result = delIngredient(ingredient);
        Assert.assertTrue(result.isOk());

        result = delDish(dish);
        Assert.assertTrue(result.isOk());

        result = delCategoryDish(categoryDish);
        Assert.assertTrue(result.isOk());

        result = delEmployee(employee);
        Assert.assertTrue(result.isOk());

        result = delPosition(position);
        Assert.assertTrue(result.isOk());
    }
}