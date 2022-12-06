package com.example.demo.services;

import com.example.demo.DTO.Request.LineItemRequest;
import com.example.demo.DTO.Request.ProductLineItemRequest;
import com.example.demo.DTO.Response.LineItemResponse;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.LineItemRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Tables.Cart;
import com.example.demo.Tables.LineItem;
import com.example.demo.Tables.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineItemService {
    private final LineItemRepository lineItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
@Autowired
    public LineItemService(LineItemRepository lineItemRepository, ProductRepository productRepository, CartRepository cartRepository) {
        this.lineItemRepository = lineItemRepository;
    this.productRepository = productRepository;
    this.cartRepository = cartRepository;
}
    public LineItemResponse getLineItem(Long lineItemId) {
        boolean exist = lineItemRepository.existsById(lineItemId);
        if (!exist)
            throw new IllegalStateException(
                    "Line Item with id " + lineItemId + " doesn't exist"
            );
        LineItemResponse response = new LineItemResponse();
        LineItem lineItem = lineItemRepository.findById(lineItemId).get();
        response.setQuantity(lineItem.getQuantity());
        return response;
    }
    public void postLineItem(ProductLineItemRequest request) {
        LineItem lineItem=new LineItem();
        lineItem.setProduct(productRepository.findById(request.getProductId()).get());
        lineItem.setCart(cartRepository.findById(request.getCartId()).get());
        lineItem.setQuantity(request.getQuantity());
        lineItemRepository.save(lineItem);
    }
    public void deleteLineItem(Long lineItemId) {
        LineItem lineItem = lineItemRepository.findById(lineItemId)
                .orElseThrow(() -> new IllegalStateException(
                        "Line item with id " + lineItemId + " doesn't exist"));
        lineItemRepository.deleteById(lineItemId);
    }

    public void updateProduct(Long lineItemId, LineItemRequest request) {
        LineItem lineItem = lineItemRepository.findById(lineItemId)
                .orElseThrow(() -> new IllegalStateException(
                        "Line item with id " + lineItemId + " doesn't exist"));
        if (request.getQuantity() > 0)
            lineItem.setQuantity(request.getQuantity());
        lineItemRepository.save(lineItem);
    }
    public void putProductInLineItem(ProductLineItemRequest productLineItemRequest) {
        if (!productRepository.existsById(productLineItemRequest.getProductId()))
            throw new IllegalStateException("Product with id " + productLineItemRequest.getProductId() + " doesn't exist");

        LineItem lineItem = new LineItem();
        Product product = productRepository.findById(productLineItemRequest.getProductId()).get();
        lineItem.setProduct(product);
        lineItem.setQuantity(productLineItemRequest.getQuantity());

        putLineItemInCart(productLineItemRequest, lineItem);
        lineItemRepository.save(lineItem);
    }

    private void putLineItemInCart(ProductLineItemRequest productLineItemRequest, LineItem lineItem) {
        Cart cart=  cartRepository.findById(productLineItemRequest.getCartId()).get();
        lineItem.setCart(cart);
    }
}
