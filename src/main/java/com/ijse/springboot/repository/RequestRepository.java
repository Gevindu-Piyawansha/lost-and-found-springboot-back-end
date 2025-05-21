package com.ijse.springboot.repository;

import com.ijse.springboot.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
