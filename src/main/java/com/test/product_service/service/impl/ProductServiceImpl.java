package com.test.product_service.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.product_service.model.Product;
import com.test.product_service.service.ProductService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private static final String URI = "https://fakestoreapi.com";

    @Override
    public List<Product> getAll() {

        try {
            Response response = RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .baseUri(URI)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("/products")
                    .then()
                    .statusCode(200)
                    .extract().response();

            return new ObjectMapper()
                    .readValue(response.getBody().asString(),
                            new TypeReference<List<Product>>() {}
            );
        }catch (Exception e){
            throw new RuntimeException("there are no results");
        }

    }

    @Override
    public Product single(Long id) {

        try {
            Response response = RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .baseUri(URI)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("/products/" + id)
                    .then()
                    .statusCode(200)
                    .extract().response();

            return new ObjectMapper()
                    .readValue(response.getBody().asString(),
                            new TypeReference<Product>() {}
                    );
        }catch (Exception e){
            throw new RuntimeException("product not found");
        }
    }

    @Override
    public Product add(Product product) {
        try {
            Response response = RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .baseUri(URI)
                    .header("Content-Type", "application/json")
                    .contentType(ContentType.JSON)
                    .body(product)
                    .when()
                    .post("/products")
                    .then()
                    .statusCode(200)
                    .extract().response();

            return new ObjectMapper()
                    .readValue(response.getBody().asString(),
                            new TypeReference<Product>() {}
                    );
        }catch (Exception e){
            throw new RuntimeException("product not added");
        }
    }

    @Override
    public Product update(Product product) {
        try {
            Response response = RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .baseUri(URI)
                    .header("Content-Type", "application/json")
                    .contentType(ContentType.JSON)
                    .body(product)
                    .when()
                    .put("/products" + product.getId())
                    .then()
                    .statusCode(200)
                    .extract().response();

            return new ObjectMapper()
                    .readValue(response.getBody().asString(),
                            new TypeReference<Product>() {}
                    );
        }catch (Exception e){
            throw new RuntimeException("product not updated");
        }
    }

    @Override
    public void remove(Long id) {

        try {
                    RestAssured.given()
                    .relaxedHTTPSValidation()
                    .baseUri(URI)
                    .header("Content-Type", "application/json")
                    .contentType(ContentType.JSON)
                    .when()
                    .put("/products" + id)
                    .then()
                    .statusCode(200)
                    .extract().response();
                    log.info("product removed");

        }catch (Exception e){
            throw new RuntimeException("product not remove");
        }
    }
}
