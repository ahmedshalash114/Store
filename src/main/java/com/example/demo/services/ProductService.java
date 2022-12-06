package com.example.demo.services;
import com.example.demo.DTO.Request.ProductRequest;
import com.example.demo.DTO.Request.ProductLineItemRequest;
import com.example.demo.DTO.Response.ProductResponse;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Tables.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final LineItemService lineItemService;
    @Autowired
    public ProductService(ProductRepository productRepository, LineItemService lineItemService) {
        this.productRepository = productRepository;
        this.lineItemService = lineItemService;
    }
    public ProductResponse getProduct(Long productId) {
        boolean exist=productRepository.existsById(productId);
        if(!exist)
            throw new IllegalStateException("Product with id "+productId+" doesn't exist");
        ProductResponse response=new ProductResponse();
        Product product=productRepository.findById(productId).get();
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        return response;
    }
    public void postProduct(ProductRequest productRequest) {
        Optional<Product> productOptional=productRepository
                .findProductByName(productRequest.getName());
        if(productOptional.isPresent())
            throw new IllegalStateException("This product is already here");

        Product product= new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);
    }
    public void deleteProduct(Long productId) {
        Product product= productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException(
                        "Product with id " + productId + " doesn't exist"
                ));
        productRepository.delete(product);
    }
    public void updateProduct(Long productId, ProductRequest request) {
        Product product= productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException(
                        "Product with id " + productId + " doesn't exist"
                ));
        Optional<Product> productOptional=productRepository
                .findProductByName(request.getName());
        if(productOptional.isPresent())
            throw new IllegalStateException("This product is already here");
        if(request.getName()!=null &&
                request.getName().length()>0){
            product.setName(request.getName());
        }
        if(request.getPrice()>0)
            product.setPrice(request.getPrice());
        productRepository.save(product);
    }
    public void postProductInLineItem(ProductLineItemRequest lineItemRequest) {
        lineItemService.putProductInLineItem(lineItemRequest);
    }
}
