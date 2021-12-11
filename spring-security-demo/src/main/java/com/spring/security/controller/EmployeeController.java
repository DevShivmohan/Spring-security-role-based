package com.spring.security.controller;

import com.spring.security.entity.Employee;
import com.spring.security.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * show portal message
     * @return
     */
    @RequestMapping(value ="/portal",method = RequestMethod.GET)
    public String disp(){
        return "Welcome in Employee Portal";
    }

    /**
     * add an employee
     * @param employee
     * @return
     */
    @PostMapping(value = "/add")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }

    /**
     * update an employee
     * @param employee
     * @param mobile
     * @return
     */
    @PutMapping(value = "/update/{mobile}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee,@PathVariable("mobile") long mobile){
        return employeeService.updateEmployee(employee,mobile);
    }

    /**
     * getting all employees
     * @return
     */
    @GetMapping(value = "/employees")
    public ResponseEntity<List<Employee>> getEmployees(){
        return employeeService.getAllEmployees();
    }

    /**
     * getting employee by mobile
     * @param mobile
     * @return
     */
    @GetMapping(value = "/employees/{mobile}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("mobile") long mobile){
        return employeeService.getEmployeeByMobile(mobile);
    }

    /**
     * delete employee
     * @param mobile
     * @return
     */
    @DeleteMapping(value = "/delete/{mobile}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("mobile") long mobile){
        return employeeService.deleteEmployee(mobile);
    }
}
