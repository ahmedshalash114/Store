package com.example.demo.services;
import com.example.demo.DTO.Request.ProductLineItemRequest;
import com.example.demo.DTO.Request.ProductRequest;
import com.example.demo.DTO.Response.ProductResponse;
import com.example.demo.Repository.ProductRepository;
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
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService underTest;
    @Mock private ProductRepository productRepository;
    @Mock private LineItemService lineItemService;
    @Mock private ProductResponse productResponse;
    @Mock private ProductLineItemRequest productLineItemRequest;
    @BeforeEach
    void setUp() {
        underTest=new ProductService(productRepository,lineItemService);
    }
    @Nested
    class whenGetProduct {
        @Test
        void getProduct() {
            //given
            Product product = new Product(10L, "product", 12, "description");
            ProductResponse response = new ProductResponse();
            given(productRepository.existsById(product.getId())).willReturn(true);
            when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
            response.setName(product.getName());
            response.setPrice(product.getPrice());
            //when
            ProductResponse testedResponse = underTest.getProduct(product.getId());
            //then
            assertEquals(testedResponse.getName(), response.getName());
            assertEquals(testedResponse.getPrice(), response.getPrice());
        }
        @Test
        void willThrowWhenProductDoesNotFoundWhenGet(){
            Long productId=10L;
            given(productRepository.existsById(productId)).willReturn(false);
            assertThatThrownBy(()->underTest.getProduct(productId)).isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Product with id "+productId+" doesn't exist");
            verify(productRepository,never()).findById(any());
        }
    }
    @Nested
    class whenPost {
        @Test
        void postProduct() {
            //given
            ProductRequest request = new ProductRequest();
            request.setName("tttt");
            request.setPrice(12);
            Product product = new Product();
            product.setName(request.getName());
            product.setPrice(request.getPrice());
            //when
            underTest.postProduct(request);
            //then
            ArgumentCaptor<Product> capturedArgumentProduct = ArgumentCaptor.forClass(Product.class);
            verify(productRepository).save(capturedArgumentProduct.capture());
            Product capturedProduct = capturedArgumentProduct.getValue();
            assertEquals(capturedProduct.getName(), request.getName());
            assertEquals(capturedProduct.getPrice(), request.getPrice());
        }
        @Test
        void willThrowWhenProductExist(){
            given(productRepository.findProductByName(any())).willReturn(Optional.of(new Product()));
            assertThatThrownBy(()->underTest.postProduct(new ProductRequest())).isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("This product is already here");
            verify(productRepository,never()).save(any());
        }
    }
    @Nested
    class whenDeleteProduct {
        @Test
        void deleteProduct() {
            //given
            Long productId=100L;
            given(productRepository.existsById(any())).willReturn(true);
            //when
            underTest.deleteProduct(productId);
            //then
            verify(productRepository, times(1)).deleteById(productId);
        }
        @Test
        void willThrowWhenProductDoesNotExistWhenDelete(){
            Long productId=100L;
            given(productRepository.existsById(any())).willReturn(false);
            assertThatThrownBy(()->underTest.deleteProduct(productId)).isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Product with id "+productId+" doesn't exist");
            verify(productRepository,never()).deleteById(productId);
        }
    }
    @Nested
    class whenUpdateProduct {
        @Test
        void updateProduct() {
            Product product = new Product(100L, "prduct", 11, "description");
            ProductRequest request = new ProductRequest();
            request.setName("product");
            request.setPrice(1234);
            if(request.getName()!=null && request.getName().length()>0)
                product.setName(request.getName());
            if(request.getPrice()>0)
                product.setPrice(request.getPrice());
            given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
            underTest.updateProduct(product.getId(), request);
            assertEquals(product.getName(), request.getName());
            assertEquals(product.getPrice(), request.getPrice());
        }
        @Test
        void willThrowWhenProductDoesNotExistWhenUpdate(){
            Long productId=100L;
            assertThatThrownBy(()->underTest.updateProduct(productId,new ProductRequest())).isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Product with id " + productId + " doesn't exist");
            verify(productRepository,never()).save(any());
        }
    }
    @Test
    void postProductInLineItem() {
        ProductLineItemRequest productLineItemRequest=new ProductLineItemRequest();
        productLineItemRequest.setProductId(100L);
        productLineItemRequest.setCartId(200L);
        productLineItemRequest.setQuantity(123);
        //when
        underTest.postProductInLineItem(productLineItemRequest);
        //then
        ArgumentCaptor<ProductLineItemRequest> requestArgumentCaptor=ArgumentCaptor.forClass(ProductLineItemRequest.class);
        verify(lineItemService).putProductInLineItem(requestArgumentCaptor.capture());
        ProductLineItemRequest capturedRequest=requestArgumentCaptor.getValue();
        assertEquals(capturedRequest.getProductId(),productLineItemRequest.getProductId());
        assertEquals(capturedRequest.getCartId(),productLineItemRequest.getCartId());
        assertEquals(capturedRequest.getQuantity(),productLineItemRequest.getQuantity());
    }
}