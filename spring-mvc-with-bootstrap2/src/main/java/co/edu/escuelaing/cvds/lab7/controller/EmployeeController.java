package co.edu.escuelaing.cvds.lab7.controller;

import co.edu.escuelaing.cvds.lab7.model.Configuration;
import co.edu.escuelaing.cvds.lab7.model.Employee;
import co.edu.escuelaing.cvds.lab7.model.ToDoItem;
import co.edu.escuelaing.cvds.lab7.service.ConfigurationService;
import co.edu.escuelaing.cvds.lab7.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService configurationService) {
        this.employeeService = configurationService;
    }

    @CrossOrigin
    @GetMapping("/")
    @ResponseBody
    public List<Employee> getAllEmployee() {
        return employeeService.getAllEmployee();
    }

    @CrossOrigin
    @RequestMapping("/{id}")
    @ResponseBody
    public Employee getEmployeeById(@PathVariable String id) {
        return employeeService.getEmployee(id);
    }

    @CrossOrigin
    @PostMapping("/")
    @ResponseBody
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);

    }

    @CrossOrigin
    @PutMapping("/")
    @ResponseBody
    public Employee updateEmployee(@RequestBody Employee employee) {

        Employee employee1 = employeeService.getEmployee(employee.getEmployeeId());

        if(employee1 != null) {
            employee1.setFirstName(employee.getFirstName());
            employee1.setLastName(employee.getLastName());
            employee1.setRole(employee.getRole());
            employee1.setSalary(employee.getSalary());

            employeeService.addEmployee(employee1);

            return employee1;
        }
        else {
            return null;
        }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteEmployeeById(@PathVariable String id) {
        if (employeeService.getEmployee(id) != null) {
            employeeService.deleteEmployee(id);
        }
    }


}
