package com.example.demo.controllers;
import com.example.demo.DTO.Request.ProductLineItemRequest;
import com.example.demo.DTO.Request.ProductRequest;
import com.example.demo.DTO.Response.ProductResponse;
import com.example.demo.Tables.Product;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {
   private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {this.productService = productService;}

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId){
     return new ResponseEntity<>(productService.getProduct(productId), HttpStatus.OK);
    }
    @PostMapping
    public void postProduct(@RequestBody ProductRequest productRequest){
     productService.postProduct(productRequest);}

    @PostMapping("/{lineItemId}")
    public void postProductInLineItem(@RequestBody ProductLineItemRequest LineItemRequest){
     productService.postProductInLineItem(LineItemRequest);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId){
      productService.deleteProduct(productId);
    }

    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable Long productId,@RequestBody ProductRequest request){
        productService.updateProduct(productId,request);
    }
}
