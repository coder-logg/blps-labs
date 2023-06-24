package edu.itmo.blps.dao.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {

	Optional<Company> findByNameAndPassword(String password, String name);
	Optional<Company> findByName(String name);

	boolean existsByName(String name);
}
