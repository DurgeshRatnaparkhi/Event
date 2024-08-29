package com.rsl.event.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rsl.event.entity.AllInvoice;

public interface AllInvoiceRepository extends JpaRepository<AllInvoice, Long> {
    AllInvoice findByName(String name);
}