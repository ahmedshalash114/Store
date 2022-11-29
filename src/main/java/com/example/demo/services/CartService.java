package com.example.demo.services;
import com.example.demo.DTO.Request.CartRequest;
import com.example.demo.DTO.Response.CartResponse;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Tables.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class CartService {
    private final CartRepository cartRepository;
@Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    public CartResponse getCart(Long cartId) {
        boolean exist=cartRepository.existsById(cartId);
        if(!exist)
            throw new IllegalStateException("Cart with id "+cartId+" doesn't exist");
        CartResponse response=new CartResponse();
        Cart cart=cartRepository.findById(cartId).get();
        response.setTotalPrice(cart.getTotalPrice());
        response.setCreatedDate(cart.getCreatedDate());
        return response;
    }
    public void postCart(CartRequest request) {
     Cart cart=new Cart();
     if(request.getTotalPrice()>0)
         cart.setTotalPrice(request.getTotalPrice());
     if(request.getCreatedDate().isBefore(LocalDate.now()) || request.getCreatedDate().isEqual(LocalDate.now()))
         cart.setCreatedDate(request.getCreatedDate());
     cartRepository.save(cart);
    }
    public void deleteCart(Long cartId) {
    boolean exist=cartRepository.existsById(cartId);
        if(!exist)
          throw  new IllegalStateException("Cart with id "+cartId+" doesn't exist");
    cartRepository.deleteById(cartId);
    }
    public void updateCart(Long cartId, CartRequest request) {
    Cart cart=cartRepository.findById(cartId).orElseThrow(()-> new IllegalStateException(
            "Cart with id "+cartId+" doesn't exist"));
    if(request.getTotalPrice()>0)
        cart.setTotalPrice(request.getTotalPrice());
    if(request.getCreatedDate().isBefore(LocalDate.now()) || request.getCreatedDate().isEqual(LocalDate.now()))
        cart.setCreatedDate(request.getCreatedDate());
    cartRepository.save(cart);
    }
}
