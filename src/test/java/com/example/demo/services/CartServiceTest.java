package com.example.demo.services;
import com.example.demo.DTO.Request.CartRequest;
import com.example.demo.DTO.Response.CartResponse;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Tables.Cart;
import com.example.demo.Tables.Customer;
import lombok.extern.java.Log;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock private CartRepository cartRepository;
    private CartResponse cartResponse;
    @InjectMocks private CartService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CartService(cartRepository);
    }
    @Nested
    class whenGetCart {
        @Test
        void getCart() {
            //given
            CartResponse cartResponse = new CartResponse();
            Cart cart = new Cart(10L, LocalDate.now(), 120);
            given(cartRepository.existsById(cart.getId())).willReturn(true);
            when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
            cartResponse.setTotalPrice(cart.getTotalPrice());
            cartResponse.setCreatedDate(cart.getCreatedDate());
            //when
            var testResponse = underTest.getCart(cart.getId());
            //then
            assertEquals(testResponse.getCreatedDate(), cartResponse.getCreatedDate());
            assertEquals(testResponse.getTotalPrice(), cartResponse.getTotalPrice());
        }
        @Test
        void willThrowsWhenCartDoesNotExist(){
            Long cartId=10L;
            given(cartRepository.existsById(any())).willReturn(false);
            assertThatThrownBy(()->underTest.getCart(cartId)).isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Cart with id "+cartId+" doesn't exist");
        }
    }
    @Test
    void postCart() {
        //given
        CartRequest request=new CartRequest();
        request.setCreatedDate(LocalDate.now().minusDays(12));
        request.setTotalPrice(12);
        Cart cart=new Cart();
        if(request.getTotalPrice()>0)
            cart.setTotalPrice(request.getTotalPrice());
        if(request.getCreatedDate().isBefore(LocalDate.now()) || request.getCreatedDate().isEqual(LocalDate.now()))
            cart.setCreatedDate(request.getCreatedDate());
        //when
        underTest.postCart(request);
        //then
        ArgumentCaptor<Cart> customerArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(customerArgumentCaptor.capture());
        Cart capturedCart = customerArgumentCaptor.getValue();
        assertEquals(capturedCart.getCreatedDate(),request.getCreatedDate());
        assertEquals(capturedCart.getCreatedDate(),request.getCreatedDate());
    }
    @Nested
    class whenDeleteCart {
        @Test
        void deleteCart() {
            //given
            Long cartId=10L;
            given(cartRepository.existsById(cartId)).willReturn(true);
            //when
            underTest.deleteCart(cartId);
            //then
            verify(cartRepository, times(1)).deleteById(cartId);
        }
        @Test
        void willThrowWhenCartDoesNotExistToDelete(){
            Long cartId=10L;
            given(cartRepository.existsById(cartId)).willReturn(false);
            assertThatThrownBy(()->underTest.deleteCart(cartId)).isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Cart with id "+cartId+" doesn't exist");
            verify(cartRepository,never()).deleteById(any());
        }
    }
    @Nested
    class whenUpdateCart {
        @Test
        void updateCart() {
            //given
            Cart cart = new Cart(LocalDate.now().minusDays(2), 123);
            CartRequest request = new CartRequest();
            request.setTotalPrice(200);
            request.setCreatedDate(LocalDate.now());
            if (request.getTotalPrice() > 0)
                cart.setTotalPrice(request.getTotalPrice());
            if (request.getCreatedDate().isBefore(LocalDate.now()) || request.getCreatedDate().isEqual(LocalDate.now()))
                cart.setCreatedDate(request.getCreatedDate());
            when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
            //when
            underTest.updateCart(cart.getId(), request);
            //then
            assertEquals(cart.getCreatedDate(), request.getCreatedDate());
            assertEquals(cart.getTotalPrice(), request.getTotalPrice());
        }
        @Test
        void willThrowWhenCartDoesNotExistToUpdate() {
            Long cartId=10L;
            assertThatThrownBy(()->underTest.updateCart(cartId,new CartRequest())).isInstanceOf(IllegalStateException.class)
                   .hasMessageContaining("Cart with id "+cartId+" doesn't exist");
            verify(cartRepository,never()).save(any());
        }
    }
    @Test
    void createCartForCustomer() {
        //given
        Customer customer=new Customer();
        Cart cart=new Cart();
        cart.setCustomer(customer);
        cart.setCreatedDate(LocalDate.now());
        cart.setTotalPrice(0);
        //when
        underTest.createCartForCustomer(customer);
        //then
        ArgumentCaptor<Cart> cartArgumentCaptor=ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(cartArgumentCaptor.capture());
        Cart capturedCart=cartArgumentCaptor.getValue();
        assertEquals(capturedCart.getTotalPrice(),cart.getTotalPrice());
        assertEquals(capturedCart.getCreatedDate(),cart.getCreatedDate());
    }
}