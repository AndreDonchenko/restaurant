package ua.goit.andre.ee9.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.goit.andre.ee9.model.Position;
import ua.goit.andre.ee9.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Andre on 07.08.2016.
 */
@Controller
public class PositionController {

    private EmployeeService employeeService;

    @RequestMapping(value = "admin/positions", method = RequestMethod.GET)
    public String getPositions(Map<String, Object> model) {
        model.put("positions", employeeService.getAllPositions());
        return "admin/Employee/positions";
    }

    @RequestMapping(value = "admin/positions", method = RequestMethod.POST)
    public String addPosition(Position position) {
        employeeService.addPosition(position);
        return "redirect:/admin/positions";
    }

    @RequestMapping(value = "admin/positions/delete/{positionName}", method = RequestMethod.GET)
    public void delPosition(@PathVariable(value = "positionName") String positionName,
                                HttpServletResponse response) {
        try {
            Position position = new Position(positionName);
            employeeService.delPosition(position);
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
