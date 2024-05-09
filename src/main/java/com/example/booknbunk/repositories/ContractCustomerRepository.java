package com.example.booknbunk.repositories;

import com.example.booknbunk.models.ContractCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractCustomerRepository extends JpaRepository<ContractCustomer, Long> {

    Page<ContractCustomer> findByCompanyNameContainingIgnoreCaseOrCountryContainingIgnoreCaseOrContactNameContainingIgnoreCase(String name, String email, Pageable pageable);
}

