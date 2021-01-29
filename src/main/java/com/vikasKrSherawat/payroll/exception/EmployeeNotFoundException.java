package com.vikasKrSherawat.payroll.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(long id) {
        super(String.format("Employee %s not found", id));
    }
}
