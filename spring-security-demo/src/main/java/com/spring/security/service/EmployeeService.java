package com.spring.security.service;

import com.spring.security.dao.EmployeeRepository;
import com.spring.security.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * add employee record
     * @param employee
     * @return
     */
    public ResponseEntity<Object> addEmployee(Employee employee){
        try{
            if(employee!=null){
                List<Employee> employees= (List<Employee>) employeeRepository.findAll();
                if(employees!=null || employees.size()>0)
                    for(Employee employee1:employees)
                        if(employee1.getMobile()==employee.getMobile())
                            return new ResponseEntity<>("Employee "+employee.getMobile()+" already exist in our database", HttpStatus.IM_USED);
                 employee.setCaptureDate(new Date());
                 employee=employeeRepository.save(employee);
                if(employee!=null)
                    return new ResponseEntity<>(employee,HttpStatus.OK);
                else
                    return new ResponseEntity<>("Due to technical problem your request couldn't be processed",HttpStatus.I_AM_A_TEAPOT);
            }else
                return new ResponseEntity<>("Provide proper request body",HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * getting all employees
     * @return
     */
    public ResponseEntity<List<Employee>> getAllEmployees(){
        try{
            List<Employee> employees= (List<Employee>) employeeRepository.findAll();
            if(employees!=null && employees.size()>0)
                return new ResponseEntity<>(employees,HttpStatus.OK);
            else
                return new ResponseEntity("Not any employee record found",HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * getting employee by mobile
     * @param mobile
     * @return
     */
    public ResponseEntity<Employee> getEmployeeByMobile(long mobile){
        try{
            List<Employee> employees= (List<Employee>) employeeRepository.findAll();
            if(employees!=null && employees.size()>0)
                for(Employee employee:employees)
                    if(employee.getMobile()==mobile)
                        return new ResponseEntity<>(employee,HttpStatus.OK);
            return new ResponseEntity("Employee does not exist",HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * update employee record
     * @param employee
     * @param mobile
     * @return
     */
    public ResponseEntity<Employee> updateEmployee(Employee employee,long mobile){
        try{
            List<Employee> employees= (List<Employee>) employeeRepository.findAll();
            if(employees!=null && employees.size()>0)
                for(Employee employee1:employees)
                    if(employee1.getMobile()==mobile){
                        employee.setId(employee1.getId());
                        employee.setCaptureDate(employee1.getCaptureDate());
                        employee=employeeRepository.save(employee);
                        if(employee!=null)
                            return new ResponseEntity<>(employee,HttpStatus.OK);
                        else
                            return new ResponseEntity("Due to technical problem request cannot be processed",HttpStatus.EXPECTATION_FAILED);
                    }
            return new ResponseEntity("Employee record does not exist",HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * delete employee
     * @param mobile
     * @return
     */
    public ResponseEntity<Employee> deleteEmployee(long mobile){
        try{
            List<Employee> employees= (List<Employee>) employeeRepository.findAll();
            if(employees!=null && employees.size()>0)
                for(Employee employee1:employees)
                    if(employee1.getMobile()==mobile){
                        employeeRepository.delete(employee1);
                        return new ResponseEntity(employee1,HttpStatus.OK);
                    }
            return new ResponseEntity("Employee does not exist",HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
