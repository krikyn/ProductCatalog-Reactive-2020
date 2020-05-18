package com.kirill.vakhrushev.productcatalogreactive;

import com.kirill.vakhrushev.productcatalogreactive.controller.Controller;
import com.kirill.vakhrushev.productcatalogreactive.model.Currency;
import com.kirill.vakhrushev.productcatalogreactive.model.Product;
import com.kirill.vakhrushev.productcatalogreactive.model.User;
import com.kirill.vakhrushev.productcatalogreactive.repository.ProductCrudRepository;
import com.kirill.vakhrushev.productcatalogreactive.repository.UserCrudRepository;
import com.kirill.vakhrushev.productcatalogreactive.service.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(Controller.class)
@Import(Service.class)
class MainTest {

    private static final long USER_ID = 1L;
    private static final User USER = new User(USER_ID, "user", Currency.RUB);
    public static final Product PRODUCT = new Product(1, "product_1", Currency.RUB, BigDecimal.valueOf(10));

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserCrudRepository userCrudMock;

    @MockBean
    private ProductCrudRepository productCrudMock;

    @BeforeEach
    void setup() {
        when(userCrudMock.findById(eq(USER_ID)))
                .thenReturn(Mono.just(USER));
        when(productCrudMock.findById(eq(1L)))
                .thenReturn(Mono.just(PRODUCT));
        when(productCrudMock.findAll())
                .thenReturn(Flux.just(
                        PRODUCT,
                        new Product(2, "product_2", Currency.EUR, BigDecimal.valueOf(20)),
                        new Product(3, "product_3", Currency.USD, BigDecimal.valueOf(30))
                ));
    }

    @Test
    void testGetUser() {
        webTestClient
                .get().uri("/get/user/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class).isEqualTo(USER);
    }

    @Test
    void testGetProduct() {
        webTestClient
                .get().uri("/get/product/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class).isEqualTo(PRODUCT);
    }

    @Test
    void testGetProducts() {
        webTestClient
                .get().uri("/get/products/{userId}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .contains(
                        PRODUCT,
                        new Product(2, "product_2", Currency.RUB, BigDecimal.valueOf(1700)),
                        new Product(3, "product_3", Currency.RUB, BigDecimal.valueOf(2370))
                );
    }
}
