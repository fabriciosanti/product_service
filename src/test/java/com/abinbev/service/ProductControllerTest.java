package com.abinbev.service;

import com.abinbev.document.Product;
import com.abinbev.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpMethod.DELETE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    @MockBean
    private ProductRepository productRepository;

    @TestConfiguration
    static class Config {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthentication("test", "test");
        }
    }

    @Before
    public void setup() {
        Product product = new Product("01", "Skol Puro Malte", "Cerveja Pilsen", new BigDecimal("2.09"), "Ambev");
        BDDMockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
    }

    @Test
    public void listProductsWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401() {
        System.out.println(port);
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("/api/products/", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void listProductsWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() {
        List<Product> product = asList(new Product("01", "Skol Puro Malte", "Cerveja Pilsen", new BigDecimal("2.09"), "Ambev"),
                new Product("02", "Brahma Puro Malte", "Cerveja Pilsen", new BigDecimal("2.80"), "Ambev"));
        BDDMockito.when(productRepository.findAll()).thenReturn(product);
        ResponseEntity<String> response = restTemplate.getForEntity("/api/products/", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getProductsByIdWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() {
        ResponseEntity<Product> response = restTemplate.getForEntity("/api/products/{id}", Product.class, "01");
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getProductsByIdWhenUsernameAndPasswordAreCorrectAndCharacterDoesNotExistShouldReturnStatusCode404() {
        ResponseEntity<Product> response = restTemplate.getForEntity("/api/products/{id}", Product.class, "-1");
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteWhenCharacterNotExistsShouldReturnStatusCode404() {
        BDDMockito.doNothing().when(productRepository).deleteById("01");
        ResponseEntity<String> exchange = restTemplate.exchange("/api/products/{id}", DELETE, null, String.class, "-1");
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteWhenCharacterExistsShouldReturnStatusCode200() {
        BDDMockito.doNothing().when(productRepository).deleteById("01");
        ResponseEntity<String> exchange = restTemplate.exchange("/api/products/{id}", DELETE, null, String.class, "01");
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void createShouldPersistDataAndReturnStatusCode201() throws Exception{
        Product product = new Product("04", "Guarana", "Guarana da amazonia", new BigDecimal("10.09"), "Ambev");
        BDDMockito.when(productRepository.save(product)).thenReturn(product);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/products/", product, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(201);
        Assertions.assertThat(response.getBody()).isNotNull();
    }
}
