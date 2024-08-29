package com.rsl.event.dao;

import org.springframework.data.repository.CrudRepository;
import com.rsl.event.entity.Invoice;
import java.util.Optional;

// Repository for Invoice operations
public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {
    // Method to find an Invoice by its id
    Optional<Invoice> findById(Integer id);
}
