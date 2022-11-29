package com.example.demo.controllers;

import com.example.demo.DTO.Request.CartRequest;
import com.example.demo.DTO.Response.CartResponse;
import com.example.demo.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/cart")
public class CartController {
    private final CartService cartService;
@Autowired
    public CartController(CartService cartService) {this.cartService = cartService;}

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long cartId){
     return new ResponseEntity<>(cartService.getCart(cartId), HttpStatus.OK);
    }
    @PostMapping
    public void postCart(@RequestBody CartRequest cartRequest){
      cartService.postCart(cartRequest);
    }
    @DeleteMapping("/{cartId}")
    public void deleteCart(@PathVariable Long cartId){
       cartService.deleteCart(cartId);
    }
    @PutMapping("/{cartId}")
    public void updateCart(@PathVariable Long cartId,@RequestBody CartRequest request){
      cartService.updateCart(cartId,request);
    }
}
