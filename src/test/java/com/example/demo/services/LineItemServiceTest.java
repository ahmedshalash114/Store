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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class LineItemServiceTest {
    @Mock LineItemRepository lineItemRepository;
    @Mock ProductRepository productRepository;
    @Mock CartRepository cartRepository;
    @Mock LineItemResponse lineItemResponse;
    @Mock LineItemRequest lineItemRequest;
    @Mock ProductLineItemRequest productLineItemRequest;
    @Mock LineItem lineItem;
    @InjectMocks LineItemService underTest;
    @BeforeEach
    void setUp() {underTest = new LineItemService(lineItemRepository, productRepository, cartRepository);}
    @Nested
    class whenGetLineItem {
        @Test
        void getLineItem() {
            LineItem lineItem = new LineItem(1000L, 1000);
            LineItemResponse response = new LineItemResponse();
            given(lineItemRepository.existsById(lineItem.getId())).willReturn(true);
            when(lineItemRepository.findById(lineItem.getId())).thenReturn(Optional.of(lineItem));
            response.setQuantity(lineItem.getQuantity());
            LineItemResponse testResponse = underTest.getLineItem(lineItem.getId());
            assertEquals(testResponse.getQuantity(), response.getQuantity());
        }

        @Test
        void willThrowWhenLineItemDoesNotExist() {
            Long lineItemId = 1000L;
            given(lineItemRepository.existsById(lineItemId)).willReturn(false);
            assertThatThrownBy(() -> underTest.getLineItem(lineItemId)).isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Line Item with id " + lineItemId + " doesn't exist");
            verify(lineItemRepository, never()).findById(any());
        }
    }
    @Test
    void postLineItem() {
        LineItem lineItem = new LineItem();
        Cart cart = new Cart();
        Product product = new Product(1000L, "product", 123, "description");
        ProductLineItemRequest productLineItemRequest = new ProductLineItemRequest();
        productLineItemRequest.setProductId(product.getId());
        productLineItemRequest.setQuantity(100);
        productLineItemRequest.setCartId(1000L);
        lineItem.setProduct(product);
        lineItem.setCart(cart);
        lineItem.setQuantity(productLineItemRequest.getQuantity());
        when(productRepository.findById(productLineItemRequest.getProductId())).thenReturn(Optional.of(product));
        when(cartRepository.findById(productLineItemRequest.getCartId())).thenReturn(Optional.of(cart));
        //when
        underTest.postLineItem(productLineItemRequest);
        //then
        ArgumentCaptor<LineItem> capturedArgumentLineItem = ArgumentCaptor.forClass(LineItem.class);
        verify(lineItemRepository).save(capturedArgumentLineItem.capture());
        LineItem capturedLineItem = capturedArgumentLineItem.getValue();
        assertEquals(capturedLineItem.getProduct(), lineItem.getProduct());
        assertEquals(capturedLineItem.getCart(), lineItem.getCart());
        assertEquals(capturedLineItem.getQuantity(), lineItem.getQuantity());
    }
    @Nested
    class whenDeleteLineItem {
        @Test
        void deleteLineItem() {
            Long lineItemId = 1000L;
            given(lineItemRepository.existsById(lineItemId)).willReturn(true);
            underTest.deleteLineItem(lineItemId);
            verify(lineItemRepository, times(1)).deleteById(lineItemId);
        }

        @Test
        void willThrowWhenLineItemDoesNotExistWhenDelete() {
            Long lineItemId = 1000L;
            given(lineItemRepository.existsById(lineItemId)).willReturn(false);
            assertThatThrownBy(() -> underTest.deleteLineItem(lineItemId)).isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Line Item with id " + lineItemId + " doesn't exist");
            verify(lineItemRepository, never()).deleteById(any());
        }
    }
    @Test
    void updateProduct() {
        LineItem lineItem = new LineItem(100L, 1234);
        LineItemRequest request = new LineItemRequest();
        request.setQuantity(1111);
        when(lineItemRepository.findById(lineItem.getId())).thenReturn(Optional.of(lineItem));
        if (request.getQuantity() > 0)
            lineItem.setQuantity(request.getQuantity());
        underTest.updateProduct(lineItem.getId(), request);
        ArgumentCaptor<LineItem> capturedArgumentLineItem = ArgumentCaptor.forClass(LineItem.class);
        verify(lineItemRepository).save(capturedArgumentLineItem.capture());
        LineItem capturedLineItem = capturedArgumentLineItem.getValue();
        assertEquals(capturedLineItem.getQuantity(), request.getQuantity());
    }
    @Nested
    class whenPutProductInLineItem {
        @Test
        void putProductInLineItem() {
            Product product = new Product(1000L, "product", 123, "description");
            Cart cart = new Cart(LocalDate.now(), 14242);
            LineItem lineItem = new LineItem();
            ProductLineItemRequest request = new ProductLineItemRequest();
            request.setCartId(1000L);
            request.setProductId(product.getId());
            request.setQuantity(4040);
            lineItem.setProduct(product);
            lineItem.setQuantity(request.getQuantity());
            given(productRepository.existsById(request.getProductId())).willReturn(true);
            when(productRepository.findById(request.getProductId())).thenReturn(Optional.of(product));
            when(cartRepository.findById(request.getCartId())).thenReturn(Optional.of(cart));
            lineItem.setCart(cart);
            underTest.putProductInLineItem(request);
            ArgumentCaptor<LineItem> capturedArgumentLineItem = ArgumentCaptor.forClass(LineItem.class);
            verify(lineItemRepository).save(capturedArgumentLineItem.capture());
            LineItem capturedLineItem = capturedArgumentLineItem.getValue();
            assertEquals(capturedLineItem.getQuantity(), lineItem.getQuantity());
            assertEquals(capturedLineItem.getProduct(), lineItem.getProduct());

//        ArgumentCaptor<Cart> capturedArgumentCart = ArgumentCaptor.forClass(Cart.class);
//        verify(lineItem).setCart(capturedArgumentCart.capture());
//        Cart capturedCart=capturedArgumentCart.getValue();
//        assertEquals(capturedCart.getTotalPrice(),cart.getTotalPrice());
//        assertEquals(capturedCart.getCreatedDate(),cart.getCreatedDate());
        }
        @Test
        void willThrowWhenProductDoesNotExist(){
            ProductLineItemRequest request=new ProductLineItemRequest();
            request.setProductId(100L);
            given(productRepository.existsById(request.getProductId())).willReturn(false);
            assertThatThrownBy(()->underTest.putProductInLineItem(request)).isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Product with id " + request.getProductId() + " doesn't exist");
            verify(lineItemRepository,never()).save(any());
        }
    }
}