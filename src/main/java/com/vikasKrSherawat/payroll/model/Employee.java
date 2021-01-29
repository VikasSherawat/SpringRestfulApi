package com.vikasKrSherawat.payroll.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Employee {

    private @Id @GeneratedValue Long id;
    private String name;
    private String role;

}
