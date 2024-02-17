package com.cts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.model.GradesHistory;
import com.cts.model.Roles;

@Repository
public interface GradesHistoryRepo extends JpaRepository<GradesHistory , Integer>{

}
