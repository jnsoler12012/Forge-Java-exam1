package com.nicolas.exam.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface BaseRepository<T> extends CrudRepository<T, Long> {
    <S extends T> S save(S entity);
    <S extends T> List<S> saveAll(Iterable<S> entities);
    List<T> findAll();
 
    Optional<T> findById(Long id);
    Iterable<T> findAllById(Iterable<Long> ids);
 
    void deleteById(Long id);
    void delete(T entity);
    void deleteAllById(Iterable<? extends Long> ids);
    void deleteAll(Iterable<? extends T> entities);
}
