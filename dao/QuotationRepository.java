package com.rsl.event.dao;

import org.springframework.data.repository.CrudRepository;
import com.rsl.event.entity.Quotation;
import java.util.Optional;

// Repository for Quotation operations
public interface QuotationRepository extends CrudRepository<Quotation, Integer> {
    // Method to find a Quotation by its id
    Optional<Quotation> findById(Integer id);
}
