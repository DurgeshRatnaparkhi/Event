package com.rsl.event.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rsl.event.entity.AllQuotation;

import java.util.List;

@Repository
public interface AllQuotationRepository extends CrudRepository<AllQuotation, Integer> {
    List<AllQuotation> findByCustomerName(String customerName);
}
