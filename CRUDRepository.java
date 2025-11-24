package dao;

import java.util.List;

public interface CRUDRepository<T> {

    T findById(int id);

    List<T> findAll();

    boolean save(T entity);

    boolean update(T entity);

    boolean delete(int id);
}
