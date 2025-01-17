package eu.deltasource.event_system.repository;

import java.util.List;

public interface Repository<T> {
    void save(T entity);

    List<T> getAll();
}
