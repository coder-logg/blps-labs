package edu.itmo.blps.dao.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
	List<Transaction> findByUser_id(Integer userId);
}
