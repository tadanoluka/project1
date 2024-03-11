package com.tadanoluka.project1.database.repository;

import com.tadanoluka.project1.database.entity.Consignee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsigneeRepository extends JpaRepository<Consignee, Integer> {

    Optional<Consignee> findConsigneeById(int id);
}
