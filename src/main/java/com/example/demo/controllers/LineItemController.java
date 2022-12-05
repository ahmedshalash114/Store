package com.example.demo.controllers;
import com.example.demo.DTO.Request.LineItemRequest;
import com.example.demo.DTO.Request.ProductLineItemRequest;
import com.example.demo.DTO.Response.LineItemResponse;
import com.example.demo.services.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/lineItem")
public class LineItemController {
    private final LineItemService lineItemService;
    @Autowired
    public LineItemController(LineItemService lineItemService) {
        this.lineItemService = lineItemService;
    }
    @GetMapping("/{lineItemId}")
    public ResponseEntity<LineItemResponse> getLineItem(@PathVariable Long lineItemId){
        return new ResponseEntity<>(lineItemService.getLineItem(lineItemId), HttpStatus.OK);
    }
    @PostMapping
    public void postLineItem(@RequestBody ProductLineItemRequest productLineItemRequest){
        lineItemService.postLineItem(productLineItemRequest);
    }
    @DeleteMapping("/{lineItemId}")
    public void deleteLineItem(@PathVariable Long lineItemId){
        lineItemService.deleteLineItem(lineItemId);
    }
    @PutMapping("/{lineItemId}")
    public void updateProduct(@PathVariable Long lineItemId,@RequestBody LineItemRequest lineItemRequest){
        lineItemService.updateProduct(lineItemId,lineItemRequest);
    }
}
