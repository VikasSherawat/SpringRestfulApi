package com.vikasKrSherawat.payroll.controller;

import com.vikasKrSherawat.payroll.exception.EmployeeNotFoundException;
import com.vikasKrSherawat.payroll.model.Employee;
import com.vikasKrSherawat.payroll.model.EmployeeModelAssembler;
import com.vikasKrSherawat.payroll.repository.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final EmployeeModelAssembler employeeAssembler;

    public EmployeeController(EmployeeRepository employeeRepository,EmployeeModelAssembler employeeAssembler) {
        this.employeeRepository = employeeRepository;
        this.employeeAssembler = employeeAssembler;
    }

    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> getAllEmployees(){
        List<EntityModel<Employee>> employees =
                employeeRepository.findAll()
                .stream()
                .map(employeeAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel());
    }

    @GetMapping("/employee/{id}")
    public EntityModel<Employee> findEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException(id));
        return employeeAssembler.toModel(employee);
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @PutMapping("/employees/{id}")
    Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return employeeRepository.save(employee);
                })
                .orElseThrow(()->new EmployeeNotFoundException(id));
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }

}
