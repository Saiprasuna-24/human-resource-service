package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.model.Grades;
@Repository
public interface GradesRepo extends JpaRepository<Grades, Integer>{

}
