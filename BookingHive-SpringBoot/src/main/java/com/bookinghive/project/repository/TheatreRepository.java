package com.bookinghive.project.repository;

import com.bookinghive.project.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    List<Theatre> findByCity_Id(Long cityId);
//    List<Theatre> findByCity_Id(Long cityId);
}

