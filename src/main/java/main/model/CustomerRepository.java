package main.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Query(value = "SELECT * FROM Customer WHERE Customer.surname = :surname AND Customer.name = :name " +
        "AND Customer.patronymic = :patronymic", nativeQuery = true)
    public List<Customer> searchByFullName(@Param("surname") String surname, @Param("name") String name, @Param("patronymic") String patronymic);
}
