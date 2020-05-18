package com.kirill.vakhrushev.productcatalogreactive.repository;

import com.kirill.vakhrushev.productcatalogreactive.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCrudRepository extends ReactiveMongoRepository<User, Long> {
}
