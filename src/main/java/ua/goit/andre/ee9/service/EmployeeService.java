package ua.goit.andre.ee9.service;

import org.springframework.transaction.annotation.Transactional;
import ua.goit.andre.ee9.dao.HEmployeeDao;
import ua.goit.andre.ee9.dao.HPositionDao;
import ua.goit.andre.ee9.model.Employee;
import ua.goit.andre.ee9.model.Position;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    private HEmployeeDao employeeDao;
    private HPositionDao positionDao;

    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeDao.getAll();
    }

    @Transactional
    public Employee getEmployeesById(Integer id) {
        return employeeDao.getById(id);
    }

    public void save(Employee employee) {
        employeeDao.add(employee);
    }

    public void delEmployee(Employee employee) {
        employeeDao.del(employee);
    }

    public Position getPositionByName(String name) {
        List<Position> list = positionDao.getByName(name);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Position getPositionById(Integer id) {
        return positionDao.getById(id);
    }

    public List<Position> getAllPositions() {
        return positionDao.getAll();
    }

    public void addPosition(Position position) {positionDao.add(position);}

    public void delPosition(Position position) {positionDao.del(position);}

    public List<Employee> getEmployeesByPosition(Position position) {
        return employeeDao.getAllByPosition(position);
    }

    public void setEmployeeDao(HEmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void setPositionDao(HPositionDao positionDao) {
        this.positionDao = positionDao;
    }
}


