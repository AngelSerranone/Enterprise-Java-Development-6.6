package com.ironhack.ShippingOrders.service.impl;

import com.ironhack.ShippingOrders.client.ProductClient;
import com.ironhack.ShippingOrders.controller.Dto.ProductDto;
import com.ironhack.ShippingOrders.controller.Dto.ShippingOrderDto;
import com.ironhack.ShippingOrders.model.Product;
import com.ironhack.ShippingOrders.model.ShippingOrder;
import com.ironhack.ShippingOrders.repository.ShippingOrderRepository;
import com.ironhack.ShippingOrders.service.interfaces.IShippingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

public class ShippingOrderService implements IShippingOrderService {

    @Autowired
    private ProductClient productClient;
    @Autowired
    private ShippingOrderRepository shippingOrderRepository;

    private CircuitBreakerFactory circuitBreakerFactory = new Resilience4JCircuitBreakerFactory();

    public ShippingOrder getShippingOrderById(@PathVariable Integer id) {

        Optional<ShippingOrder> shippingOrder = shippingOrderRepository.findById(id);
        if (shippingOrder.isPresent()) {
            return shippingOrder.get();
        } else {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "The shipping order you are looking for doesn't exists");
        }
    }

    public ShippingOrder registerProduct(ShippingOrderDto shippingOrderDto) {

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("products-service");

        Product product = circuitBreaker.run(()-> productClient.getProductById(shippingOrderDto.getProductId()),
                throwable -> getProductByIdFallBack(shippingOrderDto.getProductId()));

        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "We couldn't find your amazing product...Please, come back and try again.");
        }

        if (product.getInventoryCount() < shippingOrderDto.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "We don't have enough stock");
        }
        product.setProductId(shippingOrderDto.getProductId());
        product.setInventoryCount(product.getInventoryCount()-shippingOrderDto.getQuantity());


        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setProductPrice(product.getProductPrice());
        productDto.setInventoryCount(product.getInventoryCount());

        circuitBreaker.run(()-> productClient.updateProduct(productDto.getProductId(), productDto),
                throwable -> updateProductFallBack(productDto.getProductId(), productDto));

        ShippingOrder shippingOrder = new ShippingOrder();
        shippingOrder.setProductId(shippingOrderDto.getProductId());
        shippingOrder.setQuantity(shippingOrderDto.getQuantity());

        return shippingOrderRepository.save(shippingOrder);

    }

    private Product updateProductFallBack(Integer productId, ProductDto productDto) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "We couldn't update the inventory...Please, come back and try again.");
    }

    private Product getProductByIdFallBack(Integer productId) {
        return null;
    }
}
