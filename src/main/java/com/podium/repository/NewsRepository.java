package com.podium.repository;

import com.podium.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends JpaRepository<News,Integer> {
}
