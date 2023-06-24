package edu.itmo.blps.dao.customer;

import edu.itmo.blps.dao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    boolean existsByUsername(String username);
    Optional<Customer> findByUsername(String username);

    Optional<Customer> findByUsernameAndPassword(String username, String password);
}
