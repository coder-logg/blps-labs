package edu.itmo.blps.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByUsername(String username);
	boolean existsByUsernameAndPassword(String username, String password);
	Optional<User> findByUsername(String username);
}
