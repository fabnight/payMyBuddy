package com.payMyBuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.payMyBuddy.model.AppTransaction;


@Repository
public interface AppTransactionRepository extends CrudRepository<AppTransaction, Integer>  {

}
