package ua.goit.andre.ee9.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.andre.ee9.model.*;
import ua.goit.andre.ee9.service.DishService;
import ua.goit.andre.ee9.service.EmployeeService;
import ua.goit.andre.ee9.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by Andre on 21.08.2016.
 */
@Controller
public class OrderController {

    EmployeeService employeeService;
    OrderService orderService;
    DishService dishService;

    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "admin/orders", method = RequestMethod.GET)
    public ModelAndView getOrders(String filterEmployee, String filterDate, Integer filterTable) {
        ModelAndView modelAndView = new ModelAndView("admin/orders/orders");
        List<OrderNum> ordersList= orderService.getAllOrders();
        for (Iterator<OrderNum> orderNumIterator = ordersList.iterator(); orderNumIterator.hasNext();) {
            OrderNum orderNum = orderNumIterator.next();
            if (filterEmployee != null && !orderNum.getEmployee().getName().contains(filterEmployee)) {
                orderNumIterator.remove();
                continue;
            };
            if (filterTable != null && orderNum.getTableNum() != filterTable) {
                orderNumIterator.remove();
                continue;
            };
            if (filterDate != null && filterDate != "") {
                Date date = Date.valueOf(filterDate);
                if (!orderNum.getDateOrder().equals(date)) {
                    orderNumIterator.remove();
                    continue;
                }
            }
        }
        modelAndView.addObject("ordersList", ordersList);
        modelAndView.addObject("newOrder", new OrderNum());
        Map<Integer, String> employeeList = new HashMap<>();
        for (Employee e: employeeService.getAllEmployees()) {
            employeeList.put(e.getId(), e.getName() + " " + e.getSurname());
        }
        modelAndView.addObject("employeeList", employeeList);
        return modelAndView;
    }

    @RequestMapping(value = "admin/orders/delete/{orderId}", method = RequestMethod.GET)
    public void delOrder(@PathVariable (value = "orderId") Integer orderId,
                          HttpServletResponse response ) {
        try {
            orderService.delOrder(orderService.getOrderById(orderId));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }


    @RequestMapping(value = "admin/orders/editDetail/{orderId}", method = RequestMethod.GET)
    public ModelAndView ordersEditDetail(@PathVariable(value = "orderId") Integer orderId) {
        OrderNum orderNum = orderService.getOrderById(orderId);
        ModelAndView modelAndView = new ModelAndView("admin/orders/editOrderDetail");
        modelAndView.addObject("orderNum", orderNum);
        Map<Integer, String> employeeList = new HashMap<>();
        for (Employee e: employeeService.getAllEmployees()) {
            if (e.getPosition().getPositionName().equals("Coocker")) {
                employeeList.put(e.getId(), e.getName() + " " + e.getSurname());
            }
            //employeeList.put(e.getId(), e.getName() + " " + e.getSurname());
        }
        modelAndView.addObject("coockerList", employeeList);
        Map<Integer, String> dishList = new HashMap<>();
        for (Dish d: dishService.getAllDishes()) {
            dishList.put(d.getId(), d.getDishName());
        }
        modelAndView.addObject("dishList", dishList);
        modelAndView.addObject("preparedDishList", dishService.getPreparedDishByOrder(orderId));
        return modelAndView;
    }

    @RequestMapping(value = "admin/orders", method = RequestMethod.POST)
    public void ordersPost(Integer employeeId, Date dateOrder, Integer tableNum, boolean isOpen) {
        OrderNum orderNum = new OrderNum();
        orderNum.setEmployee(employeeService.getEmployeesById(employeeId));
        orderNum.setDateOrder(dateOrder);
        orderNum.setTableNum(tableNum);
        orderNum.setOpen(isOpen);
        orderService.saveOrder(orderNum);
    }

    @RequestMapping(value = "admin/orders/editDetail/{orderId}", method = RequestMethod.POST)
    public String ordersDetailPost(OrderNum orderNum, @PathVariable (value = "orderId") Integer orderId) {
        orderService.saveOrder(orderNum);
        return "redirect:/admin/orders/editDetail/" + orderId;

    }

    @RequestMapping(value = "admin/orders/addDetail/", method = RequestMethod.POST)
    public String ordersPost(Integer orderId, Integer dishId, Integer qty) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDish(dishService.getDishById(dishId));
        orderDetail.setQty(qty);
        orderDetail.setOrderNum(orderService.getOrderById(orderId));
        orderService.addOrderDetail(orderDetail);
        return "redirect:/admin/orders/editDetail/" + orderId;
    }

    @RequestMapping(value = "admin/orders/deleteDetail/{detailId}", method = RequestMethod.POST)
    public void ordersDeleteDetail(@PathVariable(value = "detailId") Integer detailId) {
        orderService.delOrderDetail(detailId);
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

}
