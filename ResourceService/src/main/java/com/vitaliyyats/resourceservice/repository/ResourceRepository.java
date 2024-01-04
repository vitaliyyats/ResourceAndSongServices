package com.vitaliyyats.resourceservice.repository;

import com.vitaliyyats.resourceservice.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
