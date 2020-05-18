package com.kirill.vakhrushev.productcatalogreactive.repository;

import com.kirill.vakhrushev.productcatalogreactive.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCrudRepository extends ReactiveMongoRepository<Product, Long> {
}
