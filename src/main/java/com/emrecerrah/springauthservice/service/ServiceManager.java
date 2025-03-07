package com.emrecerrah.springauthservice.service;

import com.emrecerrah.springauthservice.model.BaseEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ServiceManager<T extends BaseEntity, ID> implements IService<T, ID> {

    private final MongoRepository<T, ID> repository;

    @Override
    public void save(T t) {
        repository.save(t);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> t) {
        return repository.saveAll(t);
    }

    @Override
    public T update(T t) {
        return repository.save(t);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }
}
