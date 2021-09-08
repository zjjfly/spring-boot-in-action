package com.github.zjjfly.readinglist.repository;

import com.github.zjjfly.readinglist.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by zjjfly on 2017/7/2.
 */
@Repository
public interface ReaderRepository extends JpaRepository<Reader,String>{
    Optional<Reader> findByUsername(String name);
}
