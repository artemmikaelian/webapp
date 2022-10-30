package main.Service;

import main.model.Customer;
import main.model.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * todo Document type CustomerService
 */
@Service
@Transactional
public class CustomerService {
    @Autowired
    CustomerRepository customerRepo;

    public void save(Customer customer) {
        customerRepo.save(customer);
    }

    public List<Customer> getAll() {
        return (List<Customer>) customerRepo.findAll();
    }

    public Optional<Customer> findById(int id) {
        return customerRepo.findById(id);
    }

    public void delete(Customer customer) {
        customerRepo.delete(customer);
    }

    public List<Customer> findByFullName(String surname, String name, String patronymic) {
        return customerRepo.searchByFullName(surname, name, patronymic);
    }
}
