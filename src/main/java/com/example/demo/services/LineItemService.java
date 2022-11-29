package com.example.demo.services;

import com.example.demo.DTO.Request.LineItemRequest;
import com.example.demo.DTO.Response.LineItemResponse;
import com.example.demo.Repository.LineItemRepository;
import com.example.demo.Tables.LineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LineItemService {
    private final LineItemRepository lineItemRepository;
@Autowired
    public LineItemService(LineItemRepository lineItemRepository) {
        this.lineItemRepository = lineItemRepository;
    }
    public LineItemResponse getLineItem(Long lineItemId) {
      boolean exist=lineItemRepository.existsById(lineItemId);
      if(!exist)
          throw new IllegalStateException(
                  "Line Item with id "+lineItemId+" doesn't exist"
          );
      LineItemResponse response=new LineItemResponse();
      LineItem lineItem=lineItemRepository.findById(lineItemId).get();
      response.setQuantity(lineItem.getQuantity());
      return  response;
    }
    public void postLineItem(LineItemRequest request) {
        LineItem lineItem=new LineItem();
        lineItem.setQuantity(request.getQuantity());
        lineItemRepository.save(lineItem);
    }
    public void deleteLineItem(Long lineItemId) {
    LineItem lineItem=lineItemRepository.findById(lineItemId)
            .orElseThrow(()-> new IllegalStateException(
                    "Line item with id "+lineItemId+" doesn't exist"));
    lineItemRepository.deleteById(lineItemId);
    }

    public void updateProduct(Long lineItemId, LineItemRequest request) {
    LineItem lineItem=lineItemRepository.findById(lineItemId)
            .orElseThrow(()-> new IllegalStateException(
                    "Line item with id "+lineItemId+" doesn't exist"));
    if(request.getQuantity()>0)
        lineItem.setQuantity(request.getQuantity());
    lineItemRepository.save(lineItem);
    }
}
