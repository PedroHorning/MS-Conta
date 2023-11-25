package com.example.msaccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.msaccount.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
