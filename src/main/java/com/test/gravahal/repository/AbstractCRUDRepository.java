package com.test.gravahal.repository;

import com.google.common.base.Optional;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:25:11 PM 

public interface AbstractCRUDRepository<T> {
    
    T save(T entity);
    
    void update(T entity);
    
    void delete(T entity);

    Optional<T> get(int id);

}
