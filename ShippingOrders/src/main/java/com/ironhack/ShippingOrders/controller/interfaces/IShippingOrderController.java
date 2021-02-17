package com.ironhack.ShippingOrders.controller.interfaces;

import com.ironhack.ShippingOrders.controller.Dto.ShippingOrderDto;
import com.ironhack.ShippingOrders.model.ShippingOrder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface IShippingOrderController {

    public ShippingOrder getShippingOrderById(Integer id);

    public ShippingOrder registerProduct(ShippingOrderDto shippingOrderDto);
}
