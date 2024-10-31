package org.bamappli.telfonibackendspring.Repository;


import org.bamappli.telfonibackendspring.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

}