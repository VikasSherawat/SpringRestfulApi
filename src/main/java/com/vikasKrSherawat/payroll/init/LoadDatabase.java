package com.vikasKrSherawat.payroll.init;

import com.vikasKrSherawat.payroll.model.employee.Employee;
import com.vikasKrSherawat.payroll.model.order.Order;
import com.vikasKrSherawat.payroll.model.order.Status;
import com.vikasKrSherawat.payroll.repository.EmployeeRepository;
import com.vikasKrSherawat.payroll.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository, OrderRepository orderRepository){
        return args -> {
            log.info("Preloading " + repository.save(new Employee("Bilbo", "Baggins", "burglar")));
            log.info("Preloading " + repository.save(new Employee("Frodo", "Baggins", "thief")));
            log.info("Preloading " + orderRepository.save(new Order("MacBook Pro", Status.COMPLETED)));
            log.info("Preloading " + orderRepository.save(new Order("Iphone", Status.IN_PROGRESS)));
        };
    }
}
