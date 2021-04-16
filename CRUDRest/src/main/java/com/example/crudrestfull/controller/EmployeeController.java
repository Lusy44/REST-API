package com.example.crudrestfull.controller;

import com.example.crudrestfull.exception.ResourceNotFoundException;
import com.example.crudrestfull.model.Employee;
import com.example.crudrestfull.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

 @RestController
  //@RequestMapping("/api/v1")
 @RequestMapping("/api/v1")
 public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;


    //create get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@Validated @RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @GetMapping("employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") long employeeId) throws ResourceNotFoundException {
       Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id: " +employeeId));

        return ResponseEntity.ok().body(employee);
    }

    //Update
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") long employeeId,
                                                   @RequestBody Employee employeeDetails) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id" + employeeId));
      employee.setFirstName(employeeDetails.getFirstName());
      employee.setLastName(employeeDetails.getLastName());
      employeeDetails.setEmailId(employeeDetails.getEmailId());
      employeeRepository.save(employee);

      return  ResponseEntity.ok().body(employee);
    }

    //Delete
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") long employeeId) throws  ResourceNotFoundException{
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id" + employeeId));

        employeeRepository.deleteById(employeeId);
       return ResponseEntity.ok().build();


    }


}
