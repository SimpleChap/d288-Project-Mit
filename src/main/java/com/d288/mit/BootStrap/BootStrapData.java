package com.d288.mit.BootStrap;

import com.d288.mit.dao.CustomerRepository;
import com.d288.mit.dao.DivisionRepository;
import com.d288.mit.entities.Customer;
import com.d288.mit.entities.Division;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    public BootStrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Division division = divisionRepository.findById(2L).orElse(null);
        if (division != null) {
            addCustomerIfNotExists("Harry", "Potter", "101 Hogwarts Street", "12345", "123-456-7777", division);
            addCustomerIfNotExists("James", "Smith", "102 Hogwarts Street", "12346", "123-456-8888", division);
            addCustomerIfNotExists("Clark", "Kent", "103 Hogwarts Street", "12347", "123-456-9999", division);
            addCustomerIfNotExists("Bruce", "Wayne", "104 Hogwarts Street", "12348", "123-456-0000", division);
            addCustomerIfNotExists("Tim", "Pat", "105 Hogwarts Street", "12349", "123-456-1111", division);
        }
    }

    private void addCustomerIfNotExists(String firstName, String lastName, String address, String postalCode, String phone, Division division) {
        if (customerRepository.findByFirstNameAndLastName(firstName, lastName).isEmpty()) {
            Customer customer = new Customer(firstName, lastName, address, postalCode, phone, division);
            customerRepository.save(customer);
        }
    }
}







