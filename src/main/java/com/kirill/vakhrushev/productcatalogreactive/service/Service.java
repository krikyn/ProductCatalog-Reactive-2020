package com.kirill.vakhrushev.productcatalogreactive.service;


import com.kirill.vakhrushev.productcatalogreactive.repository.UserCrudRepository;
import com.kirill.vakhrushev.productcatalogreactive.model.Converter;
import com.kirill.vakhrushev.productcatalogreactive.model.Product;
import com.kirill.vakhrushev.productcatalogreactive.model.User;
import com.kirill.vakhrushev.productcatalogreactive.repository.ProductCrudRepository;
import com.kirill.vakhrushev.productcatalogreactive.repository.UserCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class Service {

    private final UserCrudRepository userCrudRepository;
    private final ProductCrudRepository productCrudRepository;

    public Service(
            UserCrudRepository userCrudRepository,
            ProductCrudRepository productCrudRepository
    ) {
        this.userCrudRepository = userCrudRepository;
        this.productCrudRepository = productCrudRepository;
    }

    public Flux<Product> getProductsForUser(long userId) {
        return userCrudRepository.findById(userId)
                .flatMapMany(user -> productCrudRepository.findAll()
                        .map(product -> Converter.convertProductPrice(product, user.getCurrency())));
    }

    public Mono<User> addUser(User user) {
        return userCrudRepository.insert(user);
    }

    public Mono<User> getUserById(long id) {
        return userCrudRepository.findById(id);
    }

    public void deleteUser(long id) {
        userCrudRepository.deleteById(id);
    }

    public Mono<Product> addProduct(Product product) {
        return productCrudRepository.insert(product);
    }

    public Mono<Product> getProductById(long id) {
        return productCrudRepository.findById(id);
    }

    public void deleteProduct(long id) {
        productCrudRepository.deleteById(id);
    }
}
