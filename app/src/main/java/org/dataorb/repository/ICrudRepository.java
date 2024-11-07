package org.dataorb.repository;

import java.util.List;
import java.util.Optional;

public interface ICrudRepository <T, ID> {

    void save(T entity);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    void deleteById(ID id);

    void update(T entity);

    List<T> findAll();
}
