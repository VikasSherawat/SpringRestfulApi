package com.vikasKrSherawat.payroll.controller;

import com.vikasKrSherawat.payroll.exception.EmployeeNotFoundException;
import com.vikasKrSherawat.payroll.model.employee.Employee;
import com.vikasKrSherawat.payroll.model.employee.EmployeeModelAssembler;
import com.vikasKrSherawat.payroll.repository.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee){
         EntityModel<Employee> entityModel =
                 employeeAssembler.toModel(employeeRepository.save(employee));
         return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                 .body(entityModel);
    }

    @PutMapping("/employees/{id}")
    ResponseEntity<?> updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        Employee updatedEmployee =  employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return employeeRepository.save(employee);
                })
                .orElseThrow(()->new EmployeeNotFoundException(id));
        EntityModel<Employee> entityModel = employeeAssembler.toModel(updatedEmployee);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
