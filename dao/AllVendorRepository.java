package com.rsl.event.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rsl.event.entity.AllVendor;

@Repository
public interface AllVendorRepository extends CrudRepository<AllVendor, Long> {
}
