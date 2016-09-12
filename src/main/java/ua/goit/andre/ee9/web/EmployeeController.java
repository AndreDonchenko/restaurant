package ua.goit.andre.ee9.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.goit.andre.ee9.model.Employee;
import ua.goit.andre.ee9.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static ua.goit.andre.ee9.Util.getFilenames;

@Controller
public class EmployeeController {

    private EmployeeService employeeService;

    @RequestMapping(value = "admin/employees", method = RequestMethod.GET)
    public ModelAndView employees() {
        return new ModelAndView("admin/Employee/employees", "employees", employeeService.getAllEmployees());
    }

    @RequestMapping(value = "admin/employees", method = RequestMethod.POST)
    public String employeeUpdate(Employee employee) {
        employeeService.save(employee);
        return "redirect:/admin/employees";
    }

    @RequestMapping(value = "admin/employee/add", method = RequestMethod.GET)
    public ModelAndView addEmployee(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("admin/Employee/editEmployee", "employee", new Employee());
        modelAndView.addObject("positionList", employeeService.getAllPositions());
        String photoPath = request.getSession().getServletContext().getRealPath("/resources/employee/");
        modelAndView.addObject("photoFiles", getFilenames(photoPath));
        return modelAndView;
    }

    @RequestMapping(value = "admin/employee/{employeeId}", method = RequestMethod.GET)
    public ModelAndView editEmployee(@PathVariable(value = "employeeId") Integer employeeId,
                                     HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("admin/Employee/editEmployee", "employee", employeeService.getEmployeesById(employeeId));
        modelAndView.addObject("positionList", employeeService.getAllPositions());
        String photoPath = request.getSession().getServletContext().getRealPath("/resources/employee/");
        modelAndView.addObject("photoFiles", getFilenames(photoPath));
        return modelAndView;
    }

    @RequestMapping(value = "/admin/employee/delete/{employeeId}", method = RequestMethod.GET)
    public void delEmployee(@PathVariable(value = "employeeId") Integer employeeId,
                            HttpServletRequest request, HttpServletResponse response) {
        try {
            employeeService.delEmployee(employeeService.getEmployeesById(employeeId));
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
