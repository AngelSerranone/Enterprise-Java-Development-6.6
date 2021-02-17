package com.ironhack.ShippingOrders.service.interfaces;

import com.ironhack.ShippingOrders.controller.Dto.ShippingOrderDto;
import com.ironhack.ShippingOrders.model.ShippingOrder;

public interface IShippingOrderService {

    public ShippingOrder getShippingOrderById(Integer id);

    public ShippingOrder registerProduct(ShippingOrderDto shippingOrderDto);
}
