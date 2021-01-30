package com.vikasKrSherawat.payroll.repository;

import com.vikasKrSherawat.payroll.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
