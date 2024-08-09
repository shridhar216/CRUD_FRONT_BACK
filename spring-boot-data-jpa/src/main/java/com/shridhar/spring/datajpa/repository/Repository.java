package com.shridhar.spring.datajpa.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shridhar.spring.datajpa.entity.MyEntity;

public interface Repository extends JpaRepository<MyEntity ,Long> {
 List<MyEntity> findByPublished(boolean published);
 List<MyEntity> findByTitleContaining(String title);
}
