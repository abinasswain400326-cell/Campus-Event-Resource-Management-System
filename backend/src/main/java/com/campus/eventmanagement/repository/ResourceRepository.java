package com.campus.eventmanagement.repository;

import com.campus.eventmanagement.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
