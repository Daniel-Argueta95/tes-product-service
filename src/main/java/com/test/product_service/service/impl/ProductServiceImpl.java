package com.test.product_service.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.product_service.exception.ResourceNotFoundException;
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
    private static final String PATH = "products";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    @Override
    public List<Product> getAll() {

        try {
            Response response = RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .baseUri(URI)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .when()
                    .get("/"+PATH)
                    .then()
                    .statusCode(200)
                    .extract().response();

            return new ObjectMapper()
                    .readValue(response.getBody().asString(),
                            new TypeReference<List<Product>>() {}
            );
        }catch (Exception e){
            throw new ResourceNotFoundException("there are no results");
        }

    }

    @Override
    public Product single(Long id) {

        try {
            Response response = RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .baseUri(URI)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .when()
                    .get("/"+PATH+"/" + id)
                    .then()
                    .statusCode(200)
                    .extract().response();

            return new ObjectMapper()
                    .readValue(response.getBody().asString(),
                            new TypeReference<Product>() {}
                    );
        }catch (Exception e){
            throw new ResourceNotFoundException("product not found");
        }
    }

    @Override
    public Product add(Product product) {
        try {
            Response response = RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .baseUri(URI)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .contentType(ContentType.JSON)
                    .body(product)
                    .when()
                    .post("/"+PATH)
                    .then()
                    .statusCode(200)
                    .extract().response();

            return new ObjectMapper()
                    .readValue(response.getBody().asString(),
                            new TypeReference<Product>() {}
                    );
        }catch (Exception e){
            throw new ResourceNotFoundException("product not added");
        }
    }

    @Override
    public Product update(Product product) {
        try {
            Response response = RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .baseUri(URI)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .contentType(ContentType.JSON)
                    .body(product)
                    .when()
                    .put("/" + PATH + product.getId())
                    .then()
                    .statusCode(200)
                    .extract().response();

            return new ObjectMapper()
                    .readValue(response.getBody().asString(),
                            new TypeReference<Product>() {}
                    );
        }catch (Exception e){
            throw new ResourceNotFoundException("product not updated");
        }
    }

    @Override
    public void remove(Long id) {

        try {
                    RestAssured.given()
                    .relaxedHTTPSValidation()
                    .baseUri(URI)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .contentType(ContentType.JSON)
                    .when()
                    .put("/" + PATH + "/" + id)
                    .then()
                    .statusCode(200)
                    .extract().response();
                    log.info("product removed");

        }catch (Exception e){
            throw new ResourceNotFoundException("product not remove");
        }
    }
}
