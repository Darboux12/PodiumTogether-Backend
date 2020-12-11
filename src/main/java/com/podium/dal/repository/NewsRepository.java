package com.podium.dal.repository;

import com.podium.dal.entity.News;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface NewsRepository extends CrudRepository<News,Integer> {

    Optional<News> findByDate(Date date);

    Optional<News> findByTitle(String newsTitle);

    boolean existsByTitle(String newsTitle);

    Iterable<News> findAllByOrderByDateDesc();

}
