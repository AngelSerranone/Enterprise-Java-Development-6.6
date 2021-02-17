package com.ironhack.ShippingOrders.controller.impl;
import com.ironhack.ShippingOrders.controller.Dto.ShippingOrderDto;
import com.ironhack.ShippingOrders.model.ShippingOrder;
import com.ironhack.ShippingOrders.service.interfaces.IShippingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ShippingOrderController {

    @Autowired
    private IShippingOrderService shippingOrderService;


    @GetMapping("/shipping-order/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ShippingOrder getShippingOrderById(@PathVariable Integer id) {
        return shippingOrderService.getShippingOrderById(id);
    }

    @PostMapping("/shipping-order")
    @ResponseStatus(HttpStatus.CREATED)
    public ShippingOrder registerProduct(@RequestBody @Valid ShippingOrderDto shippingOrderDto) {

        return shippingOrderService.registerProduct(shippingOrderDto);

    }
}
