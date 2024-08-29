package com.rsl.event.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rsl.event.entity.Vendor;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, Integer> {

    // Method to find a vendor by its ID
    Vendor findById(int id);
}
