package com.example.springsecurity2.repository;

import com.example.springsecurity2.model.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends CrudRepository<Loans, Long> {

    List<Loans> findByCustomerIdOrderByCreateDtDesc(int customerId);

}
