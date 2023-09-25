package it.academy.model.dao;

import java.util.List;

public interface DefaultDao<T> {
    T getById(Integer entityId);

    List<T> getALl();

    int getCount();

    boolean save(T entity);

    boolean update(T entity);

    boolean delete(T entity);

    boolean deleteById(Integer entityId);
}
