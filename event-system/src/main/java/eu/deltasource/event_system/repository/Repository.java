package eu.deltasource.event_system.repository;

public interface Repository<T> {
    void save(T entity);
}
